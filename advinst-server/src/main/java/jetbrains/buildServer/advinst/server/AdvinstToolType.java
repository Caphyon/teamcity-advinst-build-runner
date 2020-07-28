package jetbrains.buildServer.advinst.server;

import jetbrains.buildServer.tools.ToolTypeAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import jetbrains.buildServer.advinst.common.AdvinstConstants;


public class AdvinstToolType extends ToolTypeAdapter {

  @NotNull
  @Override
  public String getType() {
    return AdvinstConstants.ADVINST_TOOL_NAME;
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return AdvinstConstants.ADVINST_TOOL_DISPLAY_NAME;
  }

  @NotNull
  @Override
  public String getShortDisplayName() {
    return getDisplayName();
  }

  @Nullable
  @Override
  public String getDescription() {
    return "";
  }

  @Override
  public boolean isSupportUpload() {
    return false;
  }

  @Override
  public boolean isSupportDownload() {
    return true;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

  @Override
  public boolean isServerOnly() {
    return false;
  }

  @Override
  public boolean isCountUsages() {
    return false;
  }

  @Override
  public String getToolSiteUrl() {
    return AdvinstConstants.ADVINST_TOOL_VERSIONS_HISTORY_URL;
  }
}

