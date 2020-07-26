package jetbrains.buildServer.advinst.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.intellij.openapi.util.text.StringUtil;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.agent.ToolCannotBeFoundException;
import jetbrains.buildServer.util.FileUtil;

public final class AdvinstTool {

  private final String rootFolder;
  private final String licenseId;
  private final boolean enablePwershell;
  private final String agentToolsDir;
  private static final String UNPACK_FOLDER = ".unpacked";
  private static final String ADVINST_SUBPATH = "bin\\x86\\AdvancedInstaller.com";

  public AdvinstTool(final Map<String, String> runnerParamenters, final String agentToolsDir) {
    this.rootFolder = runnerParamenters.get(AdvinstConstants.SETTINGS_ADVINST_ROOT);
    this.licenseId = runnerParamenters.get(AdvinstConstants.SETTINGS_ADVINST_LICENSE);
    this.enablePwershell = runnerParamenters.containsKey(AdvinstConstants.SETTINGS_ADVINST_ENABLE_POWERSHELL);
    this.agentToolsDir = agentToolsDir;
  }

  public void unpack() {

    // If the path is not under the agent "tools" dir we have nothing to unpack.
    // It means that a custom root was specified
    if (!Paths.get(this.rootFolder).startsWith(this.agentToolsDir))
      return;

    try {
      // Unpack
      if (!Files.exists(Paths.get(getPath())))
      {
        final File msiFile = getMsiFile();
        final String extractCmd = String.format(AdvinstConstants.ADVINST_TOOL_EXTRACT_CMD, msiFile.toString(),
            getExtractLocation().toString());
        int ret = Runtime.getRuntime().exec(extractCmd).waitFor();
        if (0 != ret)
          throw new Exception();
      }

      // Register
      if (!StringUtil.isEmpty(this.licenseId)) {
        final String registerCmd = String.format(AdvinstConstants.ADVINST_TOOL_REGISTER_CMD, getPath(), this.licenseId);
        int ret = Runtime.getRuntime().exec(registerCmd).waitFor();
        if (0 != ret)
          throw new Exception();
      }

      //Enable powershell 
      if (this.enablePwershell)
      {
        final String registerCom = String.format(AdvinstConstants.ADVINST_TOOL_REGISTER_COM, getPath());
        int ret = Runtime.getRuntime().exec(registerCom).waitFor();
        if (0 != ret)
          throw new Exception();
      }

    } catch (Exception e) {
      throw new ToolCannotBeFoundException("Failed to extract Advanced Installer tool");
    }
  }

  public final String getPath() {
    if (Paths.get(this.rootFolder).startsWith(this.agentToolsDir))
      return Paths.get(this.rootFolder, UNPACK_FOLDER, ADVINST_SUBPATH).toString(); //
    else
      return Paths.get(this.rootFolder, ADVINST_SUBPATH).toString(); // Custom root dir
  }

  private File getMsiFile() throws FileNotFoundException {
    File msiFile = FileUtil.findFile(new FileUtil.RegexFileFilter("advancedinstaller-.*\\.msi"),
        new File(this.rootFolder));
    if (!msiFile.exists()) {
      throw new FileNotFoundException("Could not locate Advanced Installer tool setup in " + this.rootFolder);
    }
    return msiFile;
  }

  private Path getExtractLocation() {
    return Paths.get(this.rootFolder, UNPACK_FOLDER);
  }
}