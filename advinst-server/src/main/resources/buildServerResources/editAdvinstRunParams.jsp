<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean" />
<jsp:useBean id="constants" class="jetbrains.buildServer.advinst.server.AdvinstConstantsBean" />

<c:set var="advintRunTypeDeploy" value="${propertiesBean.properties[constants.advinstRunType] eq 'deploy' or 
            empty propertiesBean.properties[constants.aipPath]}" />

<tr>
  <th>
    <label for="${constants.advinstRunType}">Run Type:
      <l:star /></label>
  </th>
  <td>
    <script type="text/javascript">
      advinstRunTypeChanged = function () {
        var runType = $('<%=constants.getAdvinstRunType()%>').value;
        if (runType == 'deploy') {
          BS.Util.hide('advinstAipGroup');
          BS.Util.hide('advinstAipPathSection');
          BS.Util.hide('advinstAipBuildSection');
          BS.Util.hide('advinstAipSetupFileSection');
          BS.Util.hide('advinstAipSetupFolderSection');
          BS.Util.hide('advinstAipCommandsSection');
          BS.Util.hide('advinstAipSignSection');
        } else {
          BS.Util.show('advinstAipGroup');
          BS.Util.show('advinstAipPathSection');
          BS.Util.show('advinstAipBuildSection');
          BS.Util.show('advinstAipSetupFileSection');
          BS.Util.show('advinstAipSetupFolderSection');
          BS.Util.show('advinstAipCommandsSection');
          BS.Util.show('advinstAipSignSection');
        }
        BS.VisibilityHandlers.updateVisibility('mainContent');
      };
    </script>
    <props:selectProperty name="${constants.advinstRunType}" onchange="advinstRunTypeChanged()" className="longField">
      <props:option value="deploy" selected="${advintRunTypeDeploy}">Deploy Advanced Installer</props:option>
      <props:option value="build" selected="${not advintRunTypeDeploy}">Deploy Advanced Installer and build project
      </props:option>
    </props:selectProperty>
  </td>
</tr>

<l:settingsGroup title="Advanced Installer Tool">
  <tr>
    <th><label for="${constants.advinstRoot}">Choose version:
        <l:star /></label></th>
    </th>
    <td>
      <jsp:include
        page="/tools/selector.html?toolType=${constants.advinstToolName}&versionParameterName=${constants.advinstRoot}&toolLocation=${constants.advinstToolLocation}&class=longField" />
      <span class="smallNote">When selecting custom path, you need to specify an existing Advanced Installer install
        directory. E.g. C:\Program Files (x86)\Caphyon\Advanced Installer x.x</span>
    </td>
  </tr>

  <tr>
    <th><label for="${constants.advinstLicense}">License ID:</label></th>
    </th>
    <td>
      <props:passwordProperty name="${constants.advinstLicense}" className="longField" />
      <span class="error" id="error_${constants.advinstLicense}"></span>
      <span class="smallNote">Your Advanced Installer license ID. Leave this field empty for Simple project
        types.</span>
    </td>
  </tr>

  <tr>
    <th><label for="${constants.advinstEnablePowershell}">Enable PowerShell support</label></th>
    <td>
      <props:checkboxProperty name="${constants.advinstEnablePowershell}" />
      <span class="error" id="error_${constants.advinstEnablePowershell}"></span>
    </td>
  </tr>

</l:settingsGroup>

<%-- This is a clone of a settingsGroup because it does not accept style or id id params --%>
<tr id="advinstAipGroup" class="groupingTitle" style="${advintRunTypeDeploy ? 'display:none;' : ''}">
  <td colspan="2">Advanced Installer Project</td>
</tr>

<tr id="advinstAipPathSection" style="${advintRunTypeDeploy ? 'display:none;' : ''}">
  <th><label for="${constants.aipPath}">AIP path:
      <l:star /></label></th>
  <td>
    <props:textProperty name="${constants.aipPath}" className="longField" />
    <span class="error" id="error_${constants.aipPath}"></span>
    <span class="smallNote">Advanced Installer project file. It can be an absolute path or relative to the checkout
      root</span>
  </td>
</tr>

<tr id="advinstAipBuildSection" style="${advintRunTypeDeploy ? 'display:none;' : ''}">
  <th><label for="${constants.aipBuild}">AIP build:</label></th>
  <td>
    <props:textProperty name="${constants.aipBuild}" className="longField" />
    <span class="error" id="error_${constants.aipBuild}"></span>
  </td>
</tr>

<tr id="advinstAipSetupFileSection" style="${advintRunTypeDeploy ? 'display:none;' : ''}">
  <th><label for="${constants.aipSetupFile}">Output file:</label></th>
  <td>
    <props:textProperty name="${constants.aipSetupFile}" className="longField" />
    <span class="error" id="error_${constants.aipSetupFile}"></span>
  </td>
</tr>

<tr id="advinstAipSetupFolderSection" style="${advintRunTypeDeploy ? 'display:none;' : ''}">
  <th><label for="${constants.aipSetupFolder}">Output folder:</label></th>
  <td>
    <props:textProperty name="${constants.aipSetupFolder}" className="longField" />
    <span class="error" id="error_${constants.aipSetupFolder}"></span>
    <span class="smallNote">Project output folder. It can be an absolute path or relative to the checkout root</span>
  </td>
</tr>

<tr id="advinstAipCommandsSection" style="${advintRunTypeDeploy ? 'display:none;' : ''}">
  <th><label for="${constants.aipExtraCommands}">Commands:</label></th>
  <td>
    <props:multilineProperty name="${constants.aipExtraCommands}" className="longField" linkTitle="" cols="55" rows="5"
      expanded="true" />
    <span class="error" id="error_${constants.aipExtraCommands}"></span>
    <span class="smallNote">Advanced Installer edit commands that will be executed against the specified aip
      file.<br />
      Example: SetVersion x.x.x</span>
  </td>
</tr>

<tr id="advinstAipSignSection" style="${advintRunTypeDeploy ? 'display:none;' : ''}">
  <th><label for="${constants.aipDoNotSign}">Do not digitally sign package</label></th>
  <td>
    <props:checkboxProperty name="${constants.aipDoNotSign}" />
    <span class="error" id="error_${constants.aipDoNotSign}"></span>
  </td>
</tr>