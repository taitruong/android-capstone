# Abstract
This is the implementation from the Android Capstone project. For more details look here: https://class.coursera.org/androidcapstone-001/wiki/Symptoms

# Setup

## Java

Make sure you have the JDK installed and not only the JRE.
- Check in the folder:
-- C:\Program Files\Java (for 64bit version) and
-- C:\Program Files (x86)\Java (for 32bit version)
- Is there a folder named jdkXXXXX and/or jreXXXXX?
- Install the JDK here: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
-- accept license
-- select "Windows x86" version for 32bit
-- select "Windows x64" version for 64bit


## Install Git and TortoiseGit

First install Git: http://msysgit.github.io/
You might have to add the git install folder to your PATH. Test this:
- open command prompt
- enter: git

Now install TortoiseGit: https://code.google.com/p/tortoisegit/

## GitHub

- Register on GitHub: https://github.com/
- Ask Tai for having access to https://github.com/taitruong/android-capstone
- Create folder D:\development\android_capstone
- Enter in command prompt and jump to the created folder:
-- cd D:\development\android_capstone
-- cd D:
- Clone repository from GitHub to local Git on separate git folder "git"
-- git clone --separate-git-dir git https://github.com/taitruong/android-capstone

## Install Android Studio

### Download and install the Android emulator Genymotion: https://cloud.genymotion.com/page/launchpad/download/

- Start Genymotion
- click 'Add'
- Create Android Device
-- select 'Google Nexus 5-4.4.4-API19-1080x1920' > next > next > finish
- start device by double-clicking


### Download and install the development environment: https://developer.android.com/sdk/installing/studio.html#download

Import the server project
- open Android Studio
- File>Import Project
- Select build.gradle file in the folder Server
- open in the Project view (tab on the left side):
-- open file Application.java in folder java/org.aliensource.symptommanagement.cloud.video
-- If there is an error showing "Project SDK is not defined" then click on "Setup SDK" and set to the JDK (and not to Android SDK!)

Starting and stopping the Server
- right-click on the file>Run 'Application.main()'
- this starts an extra Java process (check on Task Manager)
- click on bottom, tab "application", press stop button
- this stops the Java process (check on Task Manager)


Server tests
- open file VideoSvcClientApiTest in folder tests/org.aliensource.symptommanagement.cloud.integration.test
-- right-click file>Run 'VideoSvcClientApiTest'
-- Test should pass (green)


Import the client project
- see above to import build.gradle file in the Client folder

Starting the app on Genymotion
- assuming you have the server started
- assuming you have the Android device in Genymotion started, you can install and run the app:
-- In the menu Run>Run 'app'
--- for the first time this takes some time
--- if an ADB error occurs open the task manager and close all ADB processes
-- select the Genymotion device > OK
- in the app on Genymotion:
-- click the OK button for login
-- next UI should appear

Client tests
- select package org.aliensource.symptommanagement.android.test
- right-click on the package > Run > Run 'org.aliensource...'
- Test should pass (green)
