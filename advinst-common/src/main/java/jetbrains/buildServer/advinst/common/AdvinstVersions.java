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
    try {
      final LocalDate minReleaseDate = LocalDate.now().minusMonths(AdvinstConstants.RELEASE_INTERVAL_MONTHS);
      final URL updatesIniUrl = new URL("https://www.advancedinstaller.com/downloads/updates.ini");
      Wini updatesIni = new Wini(updatesIniUrl);
      Section r = updatesIni.values().stream().filter(s -> {
        LocalDate rd = LocalDate.parse(s.get("ReleaseDate"), DateTimeFormatter.ofPattern("dd/mm/yyyy"));
        return minReleaseDate.isAfter(rd);
      }).findAny().orElse(null);
      if (r == null) {
        return "";
      }
      return r.get("ProductVersion");
    } catch (Exception e) {
      throw new AdvinstException(e.getMessage(), e);
    }
  }

  public static List<String> getAllowedVersions() throws AdvinstException {
    try {
      final LocalDate minReleaseDate = LocalDate.now().minusMonths(AdvinstConstants.RELEASE_INTERVAL_MONTHS);
      final URL updatesIniUrl = new URL("https://www.advancedinstaller.com/downloads/updates.ini");
      Wini updatesIni = new Wini(updatesIniUrl);
      return updatesIni.values().stream().filter(s -> {
        LocalDate rd = LocalDate.parse(s.get("ReleaseDate"), DateTimeFormatter.ofPattern("dd/M/yyyy"));
        return minReleaseDate.isBefore(rd) || minReleaseDate.isEqual(rd);
      }).map(s -> s.get("ProductVersion")).collect(Collectors.toList());
    } catch (Exception e) {
      throw new AdvinstException(e.getMessage(), e);
    }
  }
}
