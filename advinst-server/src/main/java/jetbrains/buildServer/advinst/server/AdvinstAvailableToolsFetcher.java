package jetbrains.buildServer.advinst.server;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.advinst.common.AdvinstException;
import jetbrains.buildServer.advinst.common.AdvinstVersions;
import jetbrains.buildServer.tools.available.AvailableToolsFetcher;
import jetbrains.buildServer.tools.available.FetchAvailableToolsResult;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;


public class AdvinstAvailableToolsFetcher implements AvailableToolsFetcher {

  @NotNull
  @Override
  public FetchAvailableToolsResult fetchAvailable() {
    try {
      final Collection<AdvinstDownloadableToolVersion> advinstVersions = AdvinstVersions.getAllowedVersions().stream()
          .map(v -> new AdvinstDownloadableToolVersion(v)).collect(Collectors.toList());
      return FetchAvailableToolsResult.createSuccessful(advinstVersions);
    } catch (AdvinstException e) {
      return FetchAvailableToolsResult.createError(
          "Failed to fetch available Advanced Installer versions from " + AdvinstConstants.ADVINST_TOOL_VERSIONS_URL,
          e);
    }
  }
}
