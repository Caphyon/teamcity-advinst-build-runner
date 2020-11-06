<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean" />
<jsp:useBean id="constants" class="jetbrains.buildServer.advinst.server.AdvinstConstantsBean" />

<div class="parameter">
  Advanced Installer installation root: <strong>
    <props:displayValue name="${constants.advinstRoot}" emptyValue="not specified" /></strong>
</div>

<div class="parameter">
  Advanced Installer license id: <strong>
    <props:displayValue name="${constants.advinstLicense}" emptyValue="not specified" /></strong>
</div>

<div class="parameter">
  Enable PowerShell support: <strong>
    <props:displayValue name="${constants.advinstEnablePowershell}" emptyValue="not specified" /></strong>
</div>

<div class="parameter">
  Advanced Installer project path: <strong>
    <props:displayValue name="${constants.aipPath}" emptyValue="not specified" /></strong>
</div>

<div class="parameter">
  Advanced Installer project build: <strong>
    <props:displayValue name="${constants.aipBuild}" emptyValue="not specified" /></strong>
</div>

<div class="parameter">
  Output package name: <strong>
    <props:displayValue name="${constants.aipSetupFile}" emptyValue="not specified" /></strong>
</div>

<div class="parameter">
  Output package folder: <strong>
    <props:displayValue name="${constants.aipSetupFolder}" emptyValue="not specified" /></strong>
</div>

<div class="parameter">
  Do not digitally sign package: <strong>
    <props:displayCheckboxValue name="${constants.aipDoNotSign}" /></strong>
</div>

<div class="parameter">
  Commands: <strong>
    <props:displayValue name="${constants.aipExtraCommands}" /></strong>
</div>