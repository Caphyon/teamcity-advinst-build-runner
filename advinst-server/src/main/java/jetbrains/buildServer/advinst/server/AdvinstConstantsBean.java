package jetbrains.buildServer.advinst.server;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import org.jetbrains.annotations.NotNull;

public final class AdvinstConstantsBean {

  @NotNull
  public String getAdvinstToolName() {
    return AdvinstConstants.ADVINST_TOOL_NAME;
  }

  @NotNull
  public String getAdvinstLicense() {
    return AdvinstConstants.SETTINGS_ADVINST_LICENSE;
  }

  @NotNull
  public String getAdvinstEnablePowershell(){
    return AdvinstConstants.SETTINGS_ADVINST_ENABLE_POWERSHELL;
  }

  @NotNull
  public String getAdvinstRunType(){
    return AdvinstConstants.SETTINGS_ADVINST_RUN_TYPE;
  }

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

  @NotNull
  public String getAdvinstToolLocation() {
    return "server";
  }
}
