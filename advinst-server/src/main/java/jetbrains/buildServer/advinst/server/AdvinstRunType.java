package jetbrains.buildServer.advinst.server;

import java.util.HashMap;
import java.util.Map;
import jetbrains.buildServer.advinst.common.AdvinstConstants;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

public class AdvinstRunType extends RunType
{

  private final PluginDescriptor myPluginDescriptor;

  public AdvinstRunType(final RunTypeRegistry runTypeRegistry, final PluginDescriptor pluginDescriptor)
  {
    myPluginDescriptor = pluginDescriptor;
    runTypeRegistry.registerRunType(this);
  }

  @Override
  public PropertiesProcessor getRunnerPropertiesProcessor()
  {
    return new AdvinstRunTypePropertiesProcessor();
  }

  @NotNull
  @Override
  public String getDescription()
  {
    return AdvinstConstants.RUNNER_DESCRIPTION;
  }

  @Override
  public String getEditRunnerParamsJspFilePath()
  {
    return myPluginDescriptor.getPluginResourcesPath("editAdvinstRunParams.jsp");
  }

  @Override
  public String getViewRunnerParamsJspFilePath()
  {
    return myPluginDescriptor.getPluginResourcesPath("viewAdvinstRunParams.jsp");
  }

  @Override
  public Map<String, String> getDefaultRunnerProperties()
  {
    Map<String, String> parameters = new HashMap<String, String>();
    return parameters;
  }

  @Override
  @NotNull
  public String getType()
  {
    return AdvinstConstants.RUNNER_TYPE;
  }

  @NotNull
  @Override
  public String getDisplayName()
  {
    return AdvinstConstants.RUNNER_DISPLAY_NAME;
  }

  @NotNull
  @Override
  public String describeParameters(@NotNull final Map<String, String> parameters)
  {
    StringBuilder result = new StringBuilder();
    return result.toString();
  }
}
