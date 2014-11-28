# Abstract
This is the implementation from the Android Capstone project. For more details look here: https://class.coursera.org/androidcapstone-001/wiki/Symptoms

# Setup

## Java

Make sure you have the JDK installed and not only the JRE.

|Check the folders                                |
|:------------------------------------------------|
| C:\Program Files\Java (for 64bit version)       |
| C:\Program Files (x86)\Java (for 32bit version) |

Is there a folder named jdkXXXXX and/or jreXXXXX?

Install the JDK here: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
- accept license
- select "Windows x86" version for 32bit
- select "Windows x64" version for 64bit

## Install Git and TortoiseGit

First install Git: http://msysgit.github.io/
You might have to add the git install folder to your PATH. Test this:
- open command prompt
- enter: git

Now install TortoiseGit: https://code.google.com/p/tortoisegit/

## GitHub

Register on GitHub: https://github.com/ and ask Tai for having access to: https://github.com/taitruong/android-capstone

Create the folder D:\development\android_capstone and start the command prompt in this folder. Clone the remote repository from GitHub to a local Git repository into the "git" folder:
```
 git clone https://github.com/taitruong/android-capstone git
```
## Set JAVA_HOME variable

- Name: JAVA_HOME
- Value: C:\Program Files\Java\jdk1.8.0_25

## Install Android Studio

### Download and install the Android emulator Genymotion: https://cloud.genymotion.com/page/launchpad/download/

Start Genymotion and click 'Add' to create a new Android device:
- select 'Google Nexus 5-4.4.4-API19-1080x1920' > next > next > finish

Now you can start the emulator / device by double-clicking on the virtual device list.

### Download stand-alone Android SDK
Similar to Java JDK you need the SDK. 
Download: https://developer.android.com/sdk/installing/index.html?pkg=tools

### Download and install the development environment: 
http://tools.android.com/download/studio/canary

### Server Project
Start Android Studio and import the server project via the menu:
- File>Import Project
- Select the build.gradle file (and NOT the directory) in D:\development\android_capstone\git\Server

It takes some time since some libraries will be downloaded. Once it is finished you need to check whether everything is compile correctly.
- File > Settings
 - Version Control>GitHub: enter login name and password
 - Version Control: "Add root" for "D:\development\android_capstone\git"
 - Gradle: if selectable select "Use default gradle wrapper"; if not then select "Use customizable gradle wrapper"; press OK; wait until gradle build is finished (see status bar below); then re-open and now you can select "Use default gradle wrapper"
Open in the Project view (tab on the left side):
- open file Application.java in folder java/org.aliensource.symptommanagement.cloud.video
- If there is an error showing "Project SDK is not defined" then click on "Setup SDK" and set to the JDK (and not to Android SDK!)
- NOTE: in the project tab on the left you should switch from "Android" to "Project" view. This shows you all files including hidden files

"Update project" to get the latest file from the repository
 - VCS>Update Project
 - leave default selection for "Branch Default" and "Using Stash">OK>Enter master password to save your GitHub password in Android Studio
 - Log shows how many files were updated or if everything is up to date

If project has changed, start the build process with Gradle
Possible Gradle tasks:
- build: build all main and test classes and execute test classes. If build fails only because of test classes, you can use the task "assemble".
- assemble: build all main classes. this must be at least successful to start the server.
- clean: delete all files. If you have a weird behaviour when starting the server, then do a clean before build or assemble.
- bootRun: starts the server within Gradle.
 - When stopping the application the Java process is not correctly stopped. You have to stop all java processes in the task manager
 - Another possibility to start the server in the IDE: select the Application.java in the project view >right-click>Run 'Application.main()'

Starting and stopping the Server
- right-click on the file>Run 'Application.main()'
- this starts an extra Java process (check on Task Manager)
- click on bottom, tab "application", press stop button
- this stops the Java process (check on Task Manager)

Server tests
- open file VideoSvcClientApiTest in folder tests/org.aliensource.symptommanagement.cloud.integration.test
- right-click file>Run 'VideoSvcClientApiTest'
- Test should pass (green)

Attach project to Git and GitHub
- Open menu File>Settings>Version Control>GitHub
- Enter user username and password
- Go one up to Version Control and press "Add root" for the "Unregistered Git root: D:\development\android_capstone\git"


### Client Project
Import the client project
See above to import build.gradle file in the Client folder and select Android SDK folder.

Go to Tools>Android>SDK Manager and select the followin packages:
- Tools:
 - Android SDK Tools
 - Android SDK Platform-tools
 - Android SDK Build-tools with the Latest (current Revision: 21.1.1) and oldest (Rev.17) Android SDK Build Tools
- Select packages for Android versions
 - Android 5.0 (API 21): select all packages
 - Select only "SDK Platform" package for Android 4.4W.2 (API 20), Android 4.4.2 (API 19), Android 4.3.1 (API 18), Android 4.2.2 (API 17), Android 4.1.2 (API 16), and Android 4.0 (API 14)
- Extras: Android Support Repository, Android Support Library, Google USB Driver

Starting the app on Genymotion
Assuming you have the server in den Android Studio environment and the Android device in Genymotion started, you can install and run the app. Click in the menu Run>Run 'app'. For the first time this takes some time.

If an ADB (Android Debugging Bridge) error occurs open the task manager and close all ADB processes. Select the Genymotion device you want the app to install and press OK. The app starts in Genymotion. Click the OK button for login and the next UI should appear

Client tests
- select package org.aliensource.symptommanagement.android.test
- right-click on the package > Run > Run 'org.aliensource...'
- Test should pass (green)

Attach project to Git and GitHub
- Open menu File>Settings>Version Control>GitHub
- Enter user username and password
- Go one up to Version Control and press "Add root" for the "Unregistered Git root: D:\development\android_capstone\git"
