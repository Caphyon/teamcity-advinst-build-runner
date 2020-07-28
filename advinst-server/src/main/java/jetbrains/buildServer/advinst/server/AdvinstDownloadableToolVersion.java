package jetbrains.buildServer.advinst.server;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.tools.ToolType;
import jetbrains.buildServer.tools.available.DownloadableToolVersion;
import org.jetbrains.annotations.NotNull;



public class AdvinstDownloadableToolVersion implements DownloadableToolVersion {
  private String version;

  AdvinstDownloadableToolVersion(@NotNull String version) {
    this.version = version;
  }

  @NotNull
  @Override
  public String getDestinationFileName() {
    return String.format(AdvinstConstants.ADVINST_TOOL_VERSION_FILE_NAME, version);
  }

  @NotNull
  @Override
  public String getDownloadUrl() {
    return String.format(AdvinstConstants.ADVINST_TOOL_VERSION_URL, version);
  }

  @NotNull
  @Override
  public ToolType getType() {
    return AdvinstToolProvider.ADVINST_TOOL_TYPE;
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return getId();
  }

  @NotNull
  @Override
  public String getId() {
    return String.format(AdvinstConstants.ADVINST_TOOL_VERSION_ID, version);
  }

  @NotNull
  @Override
  public String getVersion() {
    return version;
  }
}
