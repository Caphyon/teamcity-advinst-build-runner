package jetbrains.buildServer.advinst.server;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.tools.available.AvailableToolsFetcher;
import jetbrains.buildServer.tools.available.FetchAvailableToolsResult;

import org.ini4j.Profile;
import org.ini4j.Wini;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class AdvinstAvailableToolsFetcher implements AvailableToolsFetcher {

  @NotNull
  @Override
  public FetchAvailableToolsResult fetchAvailable() {
    try {
      final URL updatesIniUrl = new URL("https://www.advancedinstaller.com/downloads/updates.ini");
      Wini updatesIni = new Wini(updatesIniUrl);
      final Collection<AdvinstDownloadableToolVersion> advinstVersions = new ArrayList<>();
      for (Profile.Section section : updatesIni.values()) {
        advinstVersions.add(new AdvinstDownloadableToolVersion(section.get("ProductVersion")));
      }
      return FetchAvailableToolsResult.createSuccessful(advinstVersions);
    } catch (IOException e) {
      return FetchAvailableToolsResult.createError(
          "Failed to fetch available Advanced Installer versions from " + AdvinstConstants.ADVINST_TOOL_VERSIONS_URL, e);
    }
  }
}
