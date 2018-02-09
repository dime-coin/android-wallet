2014 Copyright Hashengineering Developers

2018 Copyright Dimecoin Developers

## Summary
Welcome to _Dimecoin Wallet_, a standalone Dimecoin payment app for your Android device!

This project contains several sub-projects:

 * __wallet__:
     The Android app itself. This is probably what you're searching for.
 * __market__:
     App description and promo material for the Google Play app store.
 * __integration-android__:
     A tiny library for integrating digitial payments into your own Android app
     (e.g. donations, in-app purchases).
 * __sample-integration-android__:
     A minimal example app to demonstrate integration of digital payments into
     your Android app.

# Building the wallet

In order to build the wallet you need to install JDK, Android Studio and Maven

## Build using Maven

You can build all sub-projects at once using Maven:

`mvn clean install`

## Build using Netbeans | MacOS

If you downloaded JDK with Netbeans 8.2 you can build the wallet inside the IDE.

First set the environment variable $ANDROID_HOME with the correct Android SDK home path: where the Android SDK home is installed depends on how you installed it.

If you downloaded the SDK through their website and then dragged/dropped the Application to your Applications folder, it's most likely here:
```
/Applications/ADT/sdk
```
If you installed the SDK using Homebrew (brew cask install android-sdk), then it's located here:
```
/usr/local/Caskroom/android-sdk/{YOUR_SDK_VERSION_NUMBER}
```
If the SDK was installed automatically as part of Android Studio then it's located here:
```
~/Library/Android/sdk
```

Once you know the location, open a terminal window and execute the following
```
export ANDROID_HOME={YOUR_PATH}
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```
Add those two exports to your `~/.bash_profile` so that they are executed each time you open a terminal window.

Make sure you run Netbeans from a terminal window, otherwise the $ANDROID_HOME variable will not be picked by the IDE.
