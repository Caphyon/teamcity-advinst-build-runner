package jetbrains.buildServer.advinst.agent;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;

public class AdvinstAgentListener extends AgentLifeCycleAdapter {

  BuildRunnerContext runnerContext = null;

  public AdvinstAgentListener(@NotNull final EventDispatcher<AgentLifeCycleListener> eventDispatcher) {
    eventDispatcher.addListener(this);
  }

  public void beforeRunnerStart(@NotNull final BuildRunnerContext runner) {
    super.beforeRunnerStart(runner);
    runnerContext = runner;
  }

  @Override
  public void beforeBuildFinish(@NotNull final AgentRunningBuild build,
      @NotNull final BuildFinishedStatus buildStatus) {
    super.beforeBuildFinish(build, buildStatus);
    try {
      build.getBuildLogger().message("Advanced Installer tool cleanup");
      AdvinstTool tool = new AdvinstTool(runnerContext);
      tool.cleanup();
    } catch (Exception e) {
      build.getBuildLogger().warning(e.getMessage());
    }
  }
}