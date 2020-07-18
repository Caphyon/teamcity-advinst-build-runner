package jetbrains.buildServer.advinst.common;

public interface AdvinstConstants {

  String RUNNER_TYPE = "AdvancedInstaller";
  String ADVINST_BINARY = "AdvancedInstaller.com";
  String ADVINST_BIN_FOLDER = "bin\\x86\\";
  String ADVINST_AIC_HEADER = ";aic";

  String SETTINGS_ADVINST_ROOT = "advancedinstaller.root";
  String SETTINGS_ADVINST_AIP_PATH = "advancedinstaller.aip.path";
  String SETTINGS_ADVINST_AIP_BUILD = "advancedinstaller.aip.build";
  String SETTINGS_ADVINST_AIP_SETUP_FILE = "advancedinstaller.aip.setup.file";
  String SETTINGS_ADVINST_AIP_SETUP_FOLDER = "advancedinstaller.aip.setup.folder";
  String SETTINGS_ADVINST_AIP_DONOTSIGN = "advancedinstaller.aip.donotsign";
  String SETTINGS_ADVINST_AIP_EXTRA_COMMANDS = "advancedinstaller.aip.extra.commands";

  String RUNNER_DISPLAY_NAME = "Advanced Installer";
  String RUNNER_DESCRIPTION = "Build setup packages.";



  String ADVINST_TOOL_VERSIONS_URL = "https://www.advancedinstaller.com/downloads/updates.ini";
  String ADVINST_TOOL_VERSION_URL = "https://www.advancedinstaller.com/downloads/%1$s/advinst.msi";
  String ADVINST_TOOL_VERSION_FILE_NAME = "advancedinstaller-%1$s.msi";
  String ADVINST_TOOL_VERSION_ID = "advancedinstaller-%1$s";
  String ADVINST_TOOL_DISPLAY_NAME = "Advanced Installer";
  String ADVINST_TOOL_NAME = "advancedinstaller";
  String ADVINST_TOOL_VERSION = "advancedinstaller.version";
  String ADVINST_TOOL_DEFAULT_VERSION = "advancedinstaller..default.version";
  String ADVINST_TOOL_VERSIONS_HISTORY_URL = "https://www.advancedinstaller.com/version-history.html";
  String ADVINST_TOOL_EXTRACT_CMD = "msiexec /a \"%1$s\" TARGETDIR=\"%2$s\" /qn";
}
