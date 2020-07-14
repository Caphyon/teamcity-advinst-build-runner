package jetbrains.buildServer.advinst.server;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import org.jetbrains.annotations.NotNull;

public class AdvinstConstantsBean {

  @NotNull
  public String getAdvinstRoot() {
    return AdvinstConstants.SETTINGS_ADVINST_ROOT;
  }

  @NotNull
  public String getAipPath() {
    return AdvinstConstants.SETTINGS_ADVINST_AIP_PATH;
  }

  @NotNull
  public String getAipBuild() {
    return AdvinstConstants.SETTINGS_ADVINST_AIP_BUILD;
  }

  @NotNull
  public String getAipSetupFile() {
    return AdvinstConstants.SETTINGS_ADVINST_AIP_SETUP_FILE;
  }

  @NotNull
  public String getAipSetupFolder() {
    return AdvinstConstants.SETTINGS_ADVINST_AIP_SETUP_FOLDER;
  }

  @NotNull
  public String getAipDoNotSign() {
    return AdvinstConstants.SETTINGS_ADVINST_AIP_DONOTSIGN;
  }

  @NotNull
  public String getAipExtraCommands() {
    return AdvinstConstants.SETTINGS_ADVINST_AIP_EXTRA_COMMANDS;
  }

}
