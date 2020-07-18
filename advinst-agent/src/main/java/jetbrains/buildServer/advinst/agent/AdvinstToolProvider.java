package jetbrains.buildServer.advinst.agent;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.BundledTool;
import jetbrains.buildServer.agent.BundledToolsRegistry;
import jetbrains.buildServer.agent.ToolCannotBeFoundException;
import jetbrains.buildServer.agent.ToolProvider;
import jetbrains.buildServer.agent.ToolProvidersRegistry;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;

public class AdvinstToolProvider implements ToolProvider {

  private final BundledToolsRegistry bundledRegistry;

  public AdvinstToolProvider(@NotNull ToolProvidersRegistry toolProvidersRegistry,
      @NotNull final BundledToolsRegistry bundledRegistry) {
    toolProvidersRegistry.registerToolProvider(this);
    this.bundledRegistry = bundledRegistry;
  }

  @Override
  public boolean supports(@NotNull final String toolName) {
    return AdvinstConstants.ADVINST_TOOL_NAME.equals(toolName);
  }

  @NotNull
  @Override
  public String getPath(@NotNull final String toolName) {
    BundledTool tool = bundledRegistry.findTool(toolName);
    return Optional.ofNullable(tool).map(BundledTool::getRootPath).map(File::getPath)
        .orElseThrow(() -> new ToolCannotBeFoundException("Could not locate Allure installation."));
  }

  @NotNull
  @Override
  public String getPath(@NotNull final String toolName, @NotNull final AgentRunningBuild build,
      @NotNull final BuildRunnerContext runner) {
    return runner.getRunnerParameters().get(AdvinstConstants.ADVINST_TOOL_VERSION);
  }
}
