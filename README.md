# Abstract
This is the implementation from the Android Capstone project. For more details look here: https://class.coursera.org/androidcapstone-001/wiki/Symptoms

# Setup

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

Download and install the development environment: https://developer.android.com/sdk/installing/studio.html#download

Download and install the Android emulator Genymotion: https://cloud.genymotion.com/page/launchpad/download/

Import the server project
- open Android Studio
- File>Import Project
- Select build.gradle in the folder Server

Import the client project
- see above
