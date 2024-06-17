# ChangeLog

Changelog of Advanced Installer plugin for TeamCity.

### Advanced Installer TeamCity Plugin  2.0

New:
* Add [Azure Trusted Sign](https://www.advancedinstaller.com/trusted-signing-integration.html) support.

Changes:
* The plugin now requires TeamCity version 2020.1 and newer.
* Add deprecation error, if using an Advanced Installer version older than two years.

### Advanced Installer TeamCity Plugin  1.5

Features:
* Add better integration with newer Advanced Installer versions.
* Add deprecation warning, if using an Advanced Installer version older than two years.

### Advanced Installer TeamCity Plugin  1.4

Features:
* Add support for building [.AIPROJ](https://www.advancedinstaller.com/user-guide/ai-ext-vs-project.html) files

### Advanced Installer TeamCity Plugin  1.3

Features:
* The plugin now supports two execution scenarios:
  * Deploy Advanced Installer tool
  * Deploy Advanced Installer tool and build project
* Added **Enable PowerShell support** option.

__*PowerShell support* requires the build agent to run with elevated privileges.__

### Advanced Installer TeamCity Plugin  1.2

__*The minimum required TeamCity version is now 2017.1.*__

Features:
* Automated deploy of Advanced Installer tool on agents.

### Advanced Installer TeamCity Plugin  1.1.2

Bugs:
* Build task fails if a build name is specified.

### Advanced Installer TeamCity Plugin  1.1.1

Bugs:
* Fixed bug which prevented plugin data validation when Advanced Installer was not installed on server.

### Advanced Installer TeamCity Plugin  1.1

Improvements:
* Added support for running extra project editing commands.

### Advanced Installer TeamCity Plugin  1.0

First official release.
