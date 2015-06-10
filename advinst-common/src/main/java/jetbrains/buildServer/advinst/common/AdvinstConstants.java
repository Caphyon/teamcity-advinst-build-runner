package jetbrains.buildServer.advinst.common;

public interface AdvinstConstants
{

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

}
