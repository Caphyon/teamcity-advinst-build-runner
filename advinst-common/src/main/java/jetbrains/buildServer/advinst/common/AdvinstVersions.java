package jetbrains.buildServer.advinst.common;

import java.net.URL;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AdvinstVersions {

  public static String getMinimumAllowedVersion() throws AdvinstException {
    List<Section> versions = getAllowedReleaseInfo();
    if (versions.isEmpty())
      return "";
    return versions.get(versions.size() - 1).get("ProductVersion");
  }

  public static List<String> getAllowedVersions() throws AdvinstException {
    return getAllowedReleaseInfo().stream().map(s -> s.get("ProductVersion")).collect(Collectors.toList());
  }

  private static List<Section> getAllowedReleaseInfo() throws AdvinstException {
    try {
      final LocalDate minReleaseDate = LocalDate.now().minusMonths(AdvinstConstants.RELEASE_INTERVAL_MONTHS);
      final URL updatesIniUrl = new URL("https://www.advancedinstaller.com/downloads/updates.ini");
      Wini updatesIni = new Wini(updatesIniUrl);
      return updatesIni.values().stream().filter(s -> {
        LocalDate rd = LocalDate.parse(s.get("ReleaseDate"), DateTimeFormatter.ofPattern("dd/M/yyyy"));
        return minReleaseDate.isBefore(rd) || minReleaseDate.isEqual(rd);
      }).collect(Collectors.toList());
    } catch (Exception e) {
      throw new AdvinstException(e.getMessage(), e);
    }

  }
}
