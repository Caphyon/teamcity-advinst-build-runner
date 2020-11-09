package jetbrains.buildServer.advinst.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.advinst.common.AdvinstAipReader;
import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.advinst.common.AdvinstException;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public class AdvinstBuildServiceAdapter extends BuildServiceAdapter {

  private List<File> mTempFiles = new ArrayList<File>();
  private AdvinstTool mAdvinstTool = null;

  public AdvinstBuildServiceAdapter() {
  }

  @Override
  public void afterInitialized() throws RunBuildException {
    super.afterInitialized();
    mAdvinstTool = new AdvinstTool(getRunnerContext());
    getBuild().addSharedConfigParameter(AdvinstConstants.ADVINST_TOOL_PATH, mAdvinstTool.getPath());
    getBuild().addSharedConfigParameter(AdvinstConstants.ADVINST_TOOL_CLEANUP,
        String.valueOf(mAdvinstTool.needsCleanup()));

  }

  @Override
  public void beforeProcessStarted() throws RunBuildException {
    try {
      mAdvinstTool.unpack();
    } catch (AdvinstException e) {
      throw new RunBuildException(e.getMessage());
    }
  }

  @Override
  public void afterProcessFinished() throws RunBuildException {
    super.afterProcessFinished();
    for (File file : mTempFiles) {
      FileUtil.delete(file);
    }
    mTempFiles.clear();
  }

  @NotNull
  @Override
  public BuildFinishedStatus getRunResult(final int exitCode) {
    if (exitCode != 0) {
      return BuildFinishedStatus.FINISHED_FAILED;
    }

    return BuildFinishedStatus.FINISHED_SUCCESS;
  }

  @NotNull
  @Override
  public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
    return new ProgramCommandLine() {
      @NotNull
      @Override
      public String getExecutablePath() throws RunBuildException {
        return getToolPath();
      }

      @NotNull
      @Override
      public String getWorkingDirectory() throws RunBuildException {
        return getCheckoutDirectory().getPath();
      }

      @NotNull
      @Override
      public List<String> getArguments() throws RunBuildException {
        return getToolArguments();
      }

      @NotNull
      @Override
      public Map<String, String> getEnvironment() throws RunBuildException {
        return getBuildParameters().getEnvironmentVariables();
      }
    };
  }

  @NotNull
  public final List<String> getToolArguments() throws RunBuildException {
    List<String> arguments = new ArrayList<String>();
    if (isDeployMode()) {
      arguments.add("/c");
      arguments.add("echo Advanced Installer tool deployed");
      return arguments;
    }
    return getAdvinstArguments();
  }

  @NotNull
  public final String getToolPath() {
    if (isDeployMode()) {
      return "cmd.exe";
    }
    return mAdvinstTool.getPath();
  }

  @NotNull
  private boolean isDeployMode() {
    return getRunnerParameters().containsKey(AdvinstConstants.SETTINGS_ADVINST_RUN_TYPE) && getRunnerParameters()
        .get(AdvinstConstants.SETTINGS_ADVINST_RUN_TYPE).equals(AdvinstConstants.ADVINST_RUN_TYPE_DEPLOY);
  }

  @NotNull
  private List<String> getAdvinstArguments() throws RunBuildException {

    List<String> arguments = new ArrayList<String>();
    List<String> commands = new ArrayList<String>();
    arguments.add("/execute");

    String absoluteAipPath;
    String buildName;
    String packageName;
    String packageFolder;
    String extraCommands;
    boolean resetSig = false;
    // ------------------------------------------------------------------------
    // Compute and validate AIP project path. It can be either an absolute path
    // or relative to the checkout folder.
    {
      absoluteAipPath = getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_AIP_PATH);
      getLogger().message(AdvinstConstants.SETTINGS_ADVINST_AIP_PATH + "=" + absoluteAipPath);
      if (StringUtil.isEmpty(absoluteAipPath)) {
        throw new RunBuildException("Advanced Installer project path (.AIP) was not specified in build settings");
      }
      File aipPath = new File(absoluteAipPath);
      if (!aipPath.isAbsolute()) {
        absoluteAipPath = new File(getCheckoutDirectory(), aipPath.getPath()).getAbsolutePath();
      }

      if (Files.notExists(Paths.get(absoluteAipPath))) {
        throw new RunBuildException(
            String.format("Advanced Installer project file not found. Path: %s", absoluteAipPath));
      }

      arguments.add(absoluteAipPath);
    }

    // ------------------------------------------------------------------------
    // compute and validate build name.
    {
      buildName = getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_AIP_BUILD);
      getLogger().message(AdvinstConstants.SETTINGS_ADVINST_AIP_BUILD + "=" + buildName);
      if (StringUtil.isNotEmpty(buildName)) {
        AdvinstAipReader aipReader = new AdvinstAipReader(absoluteAipPath);
        try {
          if (!buildName.isEmpty() && !aipReader.getBuilds().contains(buildName)) {
            throw new RunBuildException("The specified build is not present in the project file");
          }
        } catch (AdvinstException ex) {
          throw new RunBuildException(ex.getMessage());
        }
      }
    }

    // ------------------------------------------------------------------------
    // compute and validate the output package name
    {
      packageName = getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_AIP_SETUP_FILE);
      getLogger().message(AdvinstConstants.SETTINGS_ADVINST_AIP_SETUP_FILE + "=" + packageName);
      if (StringUtil.isNotEmpty(packageName) && StringUtil.isEmpty(buildName)) {
        throw new RunBuildException("Using a package output name requires a build to be specified");
      }
    }

    // ------------------------------------------------------------------------
    // Compute and validate output folder path. It can be either an absolute path
    // or relative to the build workspace folder.
    {
      packageFolder = getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_AIP_SETUP_FOLDER);
      getLogger().message(AdvinstConstants.SETTINGS_ADVINST_AIP_SETUP_FOLDER + "=" + packageFolder);
      if (StringUtil.isNotEmpty(packageFolder) && StringUtil.isEmpty(buildName)) {
        throw new RunBuildException("Using a package output folder requires a build to be specified");
      }

      if (StringUtil.isNotEmpty(packageFolder)) {
        File dir = new File(packageFolder);
        if (!dir.isAbsolute()) {
          packageFolder = new File(getCheckoutDirectory(), dir.getPath()).getAbsolutePath();
        }
      }
    }

    {
      resetSig = getRunnerParameters().containsKey(AdvinstConstants.SETTINGS_ADVINST_AIP_DONOTSIGN)
          && getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_AIP_DONOTSIGN).equals(Boolean.TRUE.toString());
    }

    {
      extraCommands = getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_AIP_EXTRA_COMMANDS);
      getLogger().message(AdvinstConstants.SETTINGS_ADVINST_AIP_EXTRA_COMMANDS + "=" + extraCommands);
    }

    if (StringUtil.isNotEmpty(packageName)) {
      commands.add(String.format("SetPackageName \"%s\" -buildname \"%s\"", packageName, buildName));
    }

    if (StringUtil.isNotEmpty(packageFolder)) {
      commands.add(String.format("SetOutputLocation -buildname \"%s\" -path \"%s\"", buildName, packageFolder));
    }

    if (resetSig) {
      commands.add("ResetSig");
    }

    if (StringUtil.isNotEmpty(extraCommands)) {
      Iterable<String> tokens = StringUtil.tokenize(extraCommands, "\r\n");
      for (String token : tokens) {
        commands.add(token);
      }
    }

    commands.add(String.format(StringUtil.isEmpty(buildName) ? "Build" : "Build -buildslist \"%s\"", buildName));

    File commandsFile;
    try {
      commandsFile = createAicFile(commands);
      arguments.add(commandsFile.getPath());
      mTempFiles.add(commandsFile);
    } catch (IOException ex) {
      throw new RunBuildException(ex.getMessage());
    }

    return arguments;
  }

  @NotNull
  private static File createAicFile(final List<String> aCommands) throws IOException {
    File aicFile = File.createTempFile("aic", null);
    FileOutputStream outStream = new FileOutputStream(aicFile);
    OutputStreamWriter writer = null;
    try {
      writer = new OutputStreamWriter(outStream, "UTF-16");
      writer.write(AdvinstConstants.ADVINST_AIC_HEADER + "\r\n");
      for (String command : aCommands) {
        writer.write(command + "\r\n");
      }
    } finally {
      try {
        if (null != writer) {
          writer.close();
        }
      } catch (IOException ex) {
      }
    }
    return aicFile;
  }
}
