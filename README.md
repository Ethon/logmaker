# logmaker
Creates formatted reports out of exports/backups of gym workout log apps

### Supported workout apps
* [Android] Redy Gym Log
  * Desktop version: CSV exports and Database backups are supported.
  * Note: When reading CSV exports the workout's name can't be retrieved as that information is not available in the export file.

### Supported desktop platforms
* Theoretically every operating system and CPU for which a Java 8+ implementation exists.

Tested:
* 64bit (x64) Windows with Oracle JDK 8

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

