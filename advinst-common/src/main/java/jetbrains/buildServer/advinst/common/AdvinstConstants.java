package jetbrains.buildServer.advinst.common;

public interface AdvinstConstants {

  String RUNNER_TYPE = "AdvancedInstaller";
  String ADVINST_AIC_HEADER = ";aic";

  String SETTINGS_ADVINST_ROOT = "advancedinstaller.root";
  String SETTINGS_ADVINST_AIP_PATH = "advancedinstaller.aip.path";
  String SETTINGS_ADVINST_AIP_BUILD = "advancedinstaller.aip.build";
  String SETTINGS_ADVINST_AIP_SETUP_FILE = "advancedinstaller.aip.setup.file";
  String SETTINGS_ADVINST_AIP_SETUP_FOLDER = "advancedinstaller.aip.setup.folder";
  String SETTINGS_ADVINST_AIP_DONOTSIGN = "advancedinstaller.aip.donotsign";
  String SETTINGS_ADVINST_AIP_EXTRA_COMMANDS = "advancedinstaller.aip.extra.commands";
  String SETTINGS_ADVINST_LICENSE = "secure:advancedinstaller.license";
  String SETTINGS_ADVINST_ENABLE_POWERSHELL = "advancedinstaller.enable.powershell";
  String SETTINGS_ADVINST_RUN_TYPE = "advancedinstaller.run.type";
  String ADVINST_RUN_TYPE_DEPLOY = "deploy";
  String ADVINST_RUN_TYPE_BUILD = "build";

  String RUNNER_DISPLAY_NAME = "Advanced Installer";
  String RUNNER_DESCRIPTION = "Build setups using Advanced Installer tool.";

  String ADVINST_TOOL_VERSIONS_URL = "https://www.advancedinstaller.com/downloads/updates.ini";
  String ADVINST_TOOL_VERSION_URL = "https://www.advancedinstaller.com/downloads/%1$s/advinst.msi";
  String ADVINST_TOOL_VERSION_FILE_NAME = "advancedinstaller-%1$s.msi";
  String ADVINST_TOOL_VERSION_ID = "advancedinstaller-%1$s";
  String ADVINST_TOOL_DISPLAY_NAME = "Advanced Installer";
  String ADVINST_TOOL_NAME = "advancedinstaller";
  String ADVINST_TOOL_PATH = "advancedinstaller.tool";
  String ADVINST_TOOL_CLEANUP = "advancedinstaller.tool.cleanup";

  String ADVINST_TOOL_DEFAULT_VERSION = "advancedinstaller.default.version";
  String ADVINST_TOOL_VERSIONS_HISTORY_URL = "https://www.advancedinstaller.com/version-history.html";
  String ADVINST_TOOL_EXTRACT_CMD = "msiexec /a \"%1$s\" TARGETDIR=\"%2$s\" /qn";
  String ADVINST_TOOL_REGISTER_CMD = "\"%1$s\" /RegisterCI %2$s";
  String ADVINST_TOOL_REGISTER_COM = "\"%1$s\" /REGSERVER";
  String ADVINST_TOOL_UNREGISTER_COM = "\"%1$s\" /UNREGSERVER";

  String ADVINST_MSBUILD_TARGETS_VAR = "AdvancedInstallerMSBuildTargets";
  String ADVINST_TOOL_ROOT_VAR = "AdvancedInstallerRoot";
}
