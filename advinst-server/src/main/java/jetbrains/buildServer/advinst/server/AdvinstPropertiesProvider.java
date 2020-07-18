package jetbrains.buildServer.advinst.server;

import jetbrains.buildServer.serverSide.BuildStartContext;
import jetbrains.buildServer.serverSide.BuildStartContextProcessor;
import jetbrains.buildServer.serverSide.SRunnerContext;
import jetbrains.buildServer.advinst.common.AdvinstConstants;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class AdvinstPropertiesProvider implements BuildStartContextProcessor {

  @Override
  public void updateParameters(@NotNull final BuildStartContext context) {
    context.getRunnerContexts().stream()
    .filter(runner -> runner.getType().equals(AdvinstConstants.RUNNER_TYPE))
    .forEach(this::updateAdvinstParameters);
  }

  private void updateAdvinstParameters(@NotNull final SRunnerContext runnerContext) {
    if (StringUtils.isBlank(runnerContext.getParameters().get(AdvinstConstants.ADVINST_TOOL_VERSION))) {
      runnerContext.addRunnerParameter(AdvinstConstants.ADVINST_TOOL_VERSION,
          AdvinstConstants.ADVINST_TOOL_DEFAULT_VERSION);
    }
  }

}
