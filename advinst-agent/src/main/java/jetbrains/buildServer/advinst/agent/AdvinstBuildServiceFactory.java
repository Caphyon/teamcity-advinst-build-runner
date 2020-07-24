package jetbrains.buildServer.advinst.agent;

import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class AdvinstBuildServiceFactory implements CommandLineBuildServiceFactory, AgentBuildRunnerInfo {

  private static final Logger LOG = Logger.getLogger(AdvinstBuildServiceFactory.class);

  public AdvinstBuildServiceFactory() {
  }

  @NotNull
  public String getType() {
    return AdvinstConstants.RUNNER_TYPE;
  }

  public boolean canRun(@NotNull final BuildAgentConfiguration agentConfiguration) {
    if (!agentConfiguration.getSystemInfo().isWindows()) {
      LOG.debug(getType() + " runner is supported only under Windows platform");
      return false;
    }
    return true;
  }

  @NotNull
  public CommandLineBuildService createService() {
    return new AdvinstBuildServiceAdapter();
  }

  @NotNull
  public AgentBuildRunnerInfo getBuildRunnerInfo() {
    return this;
  }
}
