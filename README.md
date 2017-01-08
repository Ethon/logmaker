# logmaker
Creates formatted reports out of exports/backups of gym workout log apps

![Image of the logmaker UI](https://picload.org/image/rawpccap/logmaker.png)

### Supported workout apps
* [Android] Redy Gym Log
  * Desktop version: CSV exports and Database backups are supported.
  * Note: When reading CSV exports the workout's name can't be retrieved as that information is not available in the export file.

### Supported desktop platforms
* Theoretically every operating system and CPU for which a Java 8+ implementation exists.

Tested:
* 64bit (x64) Windows with Oracle JDK 8

### Prerequisites:
* Get and install Java 8 (or later) if not already installed. https://java.com/en/download/

### Build instructions:
1. Clone or download the source code

#### Desktop:
* Navigate to logmaker-desktop
* Run ```mvn package```


## Redy Gym Log Desktop-Guide
1. Workout ;)
2. Navigate to export or backup in the app's menu and select Cloud Backup or Cloud Export, export to your favorite cloud provider like Dropbox.
  * This might require the pro version of the app. Please buy it and contribute to the app's development.
  * Of course you can do a local backup/export and copy the file manually to your desktop machine. Then the free version of the App should suffice.
3. Start the logmaker-desktop application
4. Select the 'Redy Gym Log Database Backup Reader' in the dropdown list if a backup was done, the 'Redy Gym Log CSV Export Reader' if an export was done'
5. Select the redyGymLog.db or RedyGymLog.csv file accordingly.
6. Select your favorite template
7. Generate

## Template Guide
Templates can be found under templates/ and contain template text using the FreeMarker template engine.

Take a look at FreeMarker's template author documentation to start writing your own templates. http://freemarker.org/docs/dgui.html

Templates can simply be dropped into the templates/ directory, a restart of logmaker is required to detect newly added templates but templates can be changed while the application is running.

