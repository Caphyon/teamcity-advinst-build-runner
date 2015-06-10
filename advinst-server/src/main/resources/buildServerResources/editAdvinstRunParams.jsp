<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.advinst.server.AdvinstConstantsBean"/>

<l:settingsGroup title="Advanced Installer Installation">

  <tr id="advinstPathSection">
    <th><label for="${constants.advinstRoot}">Installation root: <l:star/></label></th>
    <td><props:textProperty name="${constants.advinstRoot}" className="longField"/>
      <span class="error" id="error_${constants.advinstRoot}"></span>
      <span class="smallNote">Path to Advanced Installer install directory. E.g. C:\Program Files (x86)\Caphyon\Advanced Installer x.x</span>
    </td>
  </tr>

</l:settingsGroup>

<l:settingsGroup title="Build Options">

  <tr id="advinstAipPathSection">
    <th><label for="${constants.aipPath}">AIP path: <l:star/></label></th>
    <td><props:textProperty name="${constants.aipPath}" className="longField"/>
      <span class="error" id="error_${constants.aipPath}"></span>
      <span class="smallNote">Advanced Installer project file. It can be an absolute path or relative to the checkout root</span>
    </td>
  </tr>

  <tr id="advinstAipBuildSection">
    <th><label for="${constants.aipBuild}">AIP build: </label></th>
    <td><props:textProperty name="${constants.aipBuild}" className="longField"/>
      <span class="error" id="error_${constants.aipBuild}"></span>
    </td>
  </tr>

</l:settingsGroup>

<l:settingsGroup title="Advanced Settings"  className="advancedSetting">

  <tr class="advancedSetting">
    <th><label for="${constants.aipSetupFile}">Output file: </label></th>
    <td><props:textProperty name="${constants.aipSetupFile}" className="longField"/>
      <span class="error" id="error_${constants.aipSetupFile}"></span>
    </td>
  </tr>


  <tr class="advancedSetting">
    <th><label for="${constants.aipSetupFolder}">Output folder: </label></th>
    <td><props:textProperty name="${constants.aipSetupFolder}" className="longField"/>
      <span class="error" id="error_${constants.aipSetupFolder}"></span>
      <span class="smallNote">Project output folder. It can be an absolute path or relative to the checkout root</span>
    </td>
  </tr>

  <tr class="advancedSetting">
    <th><label for="${constants.aipExtraCommands}">Commands: </label></th>
    <td>
      <props:multilineProperty name="${constants.aipExtraCommands}"
                               className="longField"
                               linkTitle="Type commands"
                               cols="55" rows="5"
                               expanded="true"/>
      <span class="error" id="error_${constants.aipExtraCommands}"></span>
      <span class="smallNote">Advanced Installer edit commands that will be executed against the specified aip file.<br/>
         Example: SetVersion 1.2.3</span>
    </td>
  </tr>

  <tr class="advancedSetting">
    <th><label for="${constants.aipDoNotSign}">Do not digitally sign package</label></th>
    <td>
      <props:checkboxProperty name="${constants.aipDoNotSign}"/>
      <span class="error" id="error_${constants.aipDoNotSign}"></span>
    </td>
  </tr>

</l:settingsGroup>
