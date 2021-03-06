package jetbrains.buildServer.advinst.server;

import jetbrains.buildServer.tools.*;
import jetbrains.buildServer.tools.available.AvailableToolsFetcher;
import jetbrains.buildServer.tools.available.DownloadableToolVersion;
import jetbrains.buildServer.tools.utils.URLDownloader;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.StringUtil;
import org.apache.commons.io.FilenameUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jetbrains.buildServer.util.FileUtil;

public class AdvinstToolProvider extends ServerToolProviderAdapter {
  private AvailableToolsFetcher fetcher;

  public AdvinstToolProvider(AvailableToolsFetcher fetcher) {
    this.fetcher = fetcher;
  }

  static final ToolType ADVINST_TOOL_TYPE = new AdvinstToolType();

  @NotNull
  @Override
  public ToolType getType() {
    return ADVINST_TOOL_TYPE;
  }

  @NotNull
  @Override
  public Collection<? extends ToolVersion> getAvailableToolVersions() {
    return fetcher.fetchAvailable().getFetchedTools();
  }

  @NotNull
  @Override
  public File fetchToolPackage(@NotNull ToolVersion toolVersion, @NotNull File targetDirectory) throws ToolException {
    String id = toolVersion.getId();
    DownloadableToolVersion tool = CollectionsUtil.findFirst(fetcher.fetchAvailable().getFetchedTools(),
        data -> data.getId().equals(id));
    if (tool == null) {
      throw new ToolException("Failed to fetch Advance Installer tool " + toolVersion + ".");
    }
    File advinstToolMsi = new File(targetDirectory, tool.getDestinationFileName());
    URLDownloader.download(tool.getDownloadUrl(), advinstToolMsi);
    return advinstToolMsi;
  }

  @Override
  public void unpackToolPackage(@NotNull final File toolPackage, @NotNull final File targetDirectory) throws ToolException {
    try {
      final File targetPackage = new File(targetDirectory, toolPackage.getName());
      FileUtil.copy(toolPackage, targetPackage);
    } catch (IOException e) {
      throw new ToolException("Failed to copy " + toolPackage.getName() + " to " + targetDirectory, e);
    }
  }

  @NotNull
  @Override
  public GetPackageVersionResult tryGetPackageVersion(@NotNull File toolPackage) {
    final String packageName = FilenameUtils.removeExtension(toolPackage.getName());
    Pattern pattern = Pattern.compile("advancedinstaller-(.+)");

    Matcher matcher = pattern.matcher(packageName);
    if (!matcher.matches()) {
      return GetPackageVersionResult.error("Not advancedinstaller");
    }
    final String toolId = matcher.group(1);
    if (StringUtil.isEmpty(toolId)) {
      return GetPackageVersionResult.error(String.format(
          "Failed to determine Advanced Installer version based on its package file name %s. Checked package %s",
          toolPackage.getName(), toolPackage.getAbsolutePath()));
    }
    return GetPackageVersionResult.version(new AdvinstDownloadableToolVersion(toolId));
  }
}
