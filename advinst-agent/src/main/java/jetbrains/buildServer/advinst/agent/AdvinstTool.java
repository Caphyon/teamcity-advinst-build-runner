package jetbrains.buildServer.advinst.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.intellij.openapi.util.text.StringUtil;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.advinst.common.AdvinstException;
import jetbrains.buildServer.agent.AgentRuntimeProperties;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.util.FileUtil;

public final class AdvinstTool {

  private final String rootFolder;
  private final String licenseId;
  private final String agentToolsDir;
  private final String agentName;
  private final boolean enablePws;
  private static final String UNPACK_FOLDER = ".unpacked";
  private static final String ADVINST_SUBPATH = "bin\\x86\\AdvancedInstaller.com";
  private BuildRunnerContext runnerContext;

  public AdvinstTool(BuildRunnerContext runner) {
    runnerContext = runner;
    rootFolder = runner.getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_ROOT);
    licenseId = runner.getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_LICENSE);
    enablePws = runner.getRunnerParameters().containsKey(AdvinstConstants.SETTINGS_ADVINST_ENABLE_POWERSHELL) && runner
        .getRunnerParameters().get(AdvinstConstants.SETTINGS_ADVINST_ENABLE_POWERSHELL).equals(Boolean.TRUE.toString());

    agentToolsDir = runner.getConfigParameters().get(AgentRuntimeProperties.TEAMCITY_AGENT_TOOLS);
    agentName = runner.getConfigParameters().get(AgentRuntimeProperties.TEAMCITY_AGENT_NAME);
  }

  public void unpack() throws AdvinstException {
    try {
      // Unpack
      final String advinstToolPath = getPath();
      if (Files.notExists(Paths.get(advinstToolPath)) && Paths.get(rootFolder).startsWith(agentToolsDir)) {
        final File msiFile = getMsiFile();
        final String extractCmd = String.format(AdvinstConstants.ADVINST_TOOL_EXTRACT_CMD, msiFile.toString(),
            getUnpackDir().toString());
        int ret = Runtime.getRuntime().exec(extractCmd).waitFor();
        if (0 != ret)
          throw new Exception("Failed to unpack Advanced Installer tool");
      }

      // Verify the tool actually exists after unpack
      if (Files.notExists(Paths.get(advinstToolPath)))
        throw new FileNotFoundException("Failed to locate Advanced Installer tool " + advinstToolPath);

      runnerContext.addConfigParameter(AdvinstConstants.ADVINST_TOOL_PATH, advinstToolPath);

      // Register
      if (!StringUtil.isEmpty(licenseId)) {
        final String registerCmd = String.format(AdvinstConstants.ADVINST_TOOL_REGISTER_CMD, advinstToolPath,
            licenseId);
        int ret = Runtime.getRuntime().exec(registerCmd).waitFor();
        if (0 != ret)
          throw new Exception("Failed to register Advanced Installer tool");
      }

      // Enable PowerShell support
      if (enablePws) {
        final String enablePswCmd = String.format(AdvinstConstants.ADVINST_TOOL_REGISTER_COM, advinstToolPath);
        int ret = Runtime.getRuntime().exec(enablePswCmd).waitFor();
        if (0 != ret)
          throw new Exception("Failed to enable PowerShell support for Advanced Installer tool");
      }

    } catch (Exception e) {
      throw new AdvinstException(
          "Failed to deploy Advanved Installer tool to agent " + agentName + ". Error: " + e.getMessage(), e);
    }
  }

  public final String getPath() {
    if (Paths.get(rootFolder).startsWith(agentToolsDir))
      return Paths.get(rootFolder, UNPACK_FOLDER, ADVINST_SUBPATH).toString(); //
    else
      return Paths.get(rootFolder, ADVINST_SUBPATH).toString(); // Custom root dir
  }

  public final void unregisterCOM() throws AdvinstException {
    try {
      final String advinstToolPath = runnerContext.getRunnerParameters().get(AdvinstConstants.ADVINST_TOOL_PATH);
      // Enable PowerShell support
      if (enablePws) {
        final String disablePswCmd = String.format(AdvinstConstants.ADVINST_TOOL_UNREGISTER_COM, advinstToolPath);
        int ret = Runtime.getRuntime().exec(disablePswCmd).waitFor();
        if (0 != ret)
          throw new Exception("Failed to disable PowerShell support for Advanced Installer tool");
      }
    } catch (Exception e) {
      throw new AdvinstException(
          "Failed to cleanup Advanved Installer tool from agent " + agentName + ". Error: " + e.getMessage(), e);
    }
  }

  private File getMsiFile() throws FileNotFoundException {
    File msiFile = FileUtil.findFile(new FileUtil.RegexFileFilter("advancedinstaller-.*\\.msi"), new File(rootFolder));
    if (!msiFile.exists()) {
      throw new FileNotFoundException("Could not locate Advanced Installer tool setup in " + rootFolder);
    }
    return msiFile;
  }

  private Path getUnpackDir() {
    return Paths.get(rootFolder, UNPACK_FOLDER);
  }
}