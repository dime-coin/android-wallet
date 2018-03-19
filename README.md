Technical details
=================

### FILES

Your wallet contains your private keys and various transaction related metadata. It is stored in app-private
storage:

    Mainnet: /data/data/de.schildbach.wallet/files/wallet-protobuf
    Testnet: /data/data/de.schildbach.wallet_test/files/wallet-protobuf-testnet

The wallet file format is not compatible to wallet.dat (Satoshi client). Rather, it uses a custom protobuf format
which should be compatible between clients using dimecoinj.

Certain actions cause automatic rolling backups of your wallet to app-private storage:

    Mainnet: /data/data/de.schildbach.wallet/files/key-backup-protobuf
    Testnet: /data/data/de.schildbach.wallet_test/files/key-backup-protobuf-testnet

Your wallet can be manually backed up to and restored from a share of the storage access framework (likely Google Drive):

    Mainnet: dimecoin-wallet-backup-<yyyy-MM-dd>
    Testnet: dimecoin-wallet-backup-testnet-<yyyy-MM-dd>

If you want to recover coins from manual backups and for whatever reason you cannot use the app
itself to restore from the backup, see the separate [README.recover.md](README.recover.md) guide.

The current fee rate for each of the fee categories (economic, normal, priority) is cached in
app-private storage:

    Mainnet: /data/data/de.schildbach.wallet/files/fees.txt
    Testnet: /data/data/de.schildbach.wallet_test/files/fees-testnet.txt


### DEBUGGING

Wallet file for Testnet can be pulled from an (even un-rooted) device using:

    adb pull /data/data/de.schildbach.wallet_test/files/wallet-protobuf-testnet

Log messages can be viewed by:

    adb logcat

The app can send extensive debug information. Use **Options > Settings > Report Issue** and follow the dialog.
In the generated e-mail, replace the support address with yours.


### BUILDING THE DEVELOPMENT VERSION

It's important to know that the development version uses Testnet, is debuggable and the wallet file
is world readable/writeable. The goal is to be able to debug easily.

You can probably skip some steps, especially if you built Android apps before.

You'll need git, a Java SDK 6 (or later) and Gradle 2.10 (or later) for this. I'll assume Ubuntu Xenial Linux
for the package installs, which comes with slightly more recent versions.

    # first time only
    sudo apt install git gradle openjdk-8-jdk libstdc++6:i386 zlib1g:i386

Download the [Android SDK Tools](https://developer.android.com/studio/index.html#command-tools)
and unpack to your workspace directory. Point your `ANDROID_HOME` variable to the unpacked Android SDK directory
and switch to it.

Download and install the required Android dependencies:

    tools/android update sdk --no-ui --force --all --filter tool,platform-tool,build-tools-27.0.3,android-15,android-27

Download the [Android NDK](https://developer.android.com/ndk/downloads/), then unpack it to your workspace directory. Point your `ANDROID_NDK_HOME` variable to the unpacked Android NDK directory.

Finally, you can build Dimecoin Wallet and sign it with your development key. Again in your workspace,
use:

    # first time only
    git clone -b master https://github.com/dime-coin/android-wallet.git android-wallet

    # each time
    cd android-wallet
    git pull
    gradle clean :native-scrypt:copy test build

To install the app on your Android device, use:

    # first time only
    sudo apt install android-tools-adb

    # each time
    adb install wallet/build/outputs/apk/android-wallet-debug.apk

If installation fails, make sure "Developer options" and "USB debugging" are enabled on your Android device, and an ADB
connection is established.


### BUILDING THE PRODUCTIVE VERSION

At this point I'd like to remind that you continue on your own risk. According to the license,
there is basically no warranty and liability. It's your responsibility to audit the source code
for security issues and build, install and run the application in a secure way.

The production version uses Mainnet, is built non-debuggable, space-optimized with ProGuard and the
wallet file is protected against access from non-root users. In the code repository, it lives in a
separate 'prod' branch that gets rebased against master with each released version.

    # each time
    cd android-wallet
    git fetch origin
    git checkout origin/prod
    gradle clean :native-scrypt:copy test build


### SETTING UP FOR DEVELOPMENT

You should be able to import the project into Android Studio, as it uses Gradle for building.


### TRANSLATIONS

The source language is English. Translations for all languages except German [happened on Transifex](https://www.transifex.com/bitcoin-wallet/bitcoin-wallet/).

The English resources are pushed to Transifex. Changes are pulled and committed to the git
repository from time to time. It can be done by manually downloading the files, but using the `tx`
command line client is more convenient:

    # first time only
    sudo apt install transifex-client

If strings resources are added or changed, the source language files need to be pushed to
Transifex. This step will probably only be executed by the maintainer of the project, as special
permission is needed:

    # push source files to Transifex
    tx push -s

As soon as a translation is ready, it can be pulled:

    # pull translation from Transifex
    tx pull -f -l <language code>

Note that after pulling, any bugs introduced by either translators or Transifex itself need to be
corrected manually.


### NFC (Near field communication)

Dimecoin Wallet supports reading Dimecoin requests via NFC, either from a passive NFC tag or from
another NFC capable Android device that is requesting coins.

For this to work, just enable NFC in your phone and hold your phone to the tag or device (with
the "Request coins" dialog open). The "Send coins" dialog will open with fields populated.

Instructions for preparing an NFC tag with your address:

- We have successfully tested [this NFC tag writer](https://play.google.com/store/apps/details?id=com.nxp.nfc.tagwriter).
  Other writers should work as well, let us know if you succeed.

- Some tags have less than 50 bytes capacity, those won't work. 1 KB tags recommended.

- The tag needs to contain a Dimecoin URI. You can construct one with the "Request coins" dialog,
  then share with messaging or email. You can also construct the URI manually. Mainnet example:
  `dimecoin:1G2Y2jP5YFZ5RGk2PXaeWwbeA5y1ZtFhoL`

- The type of the message needs to be URI or URL (not Text).

- If you put your tag at a public place, don't forget to enable write protect. Otherwise, someone
  could overwrite the tag with his own Dimecoin address.


### DIMECOINJ

Dimecoin Wallet uses [dimecoinj](https://github.com/dime-coin/dimecoinj) for Dimecoin specific logic.


### EXCHANGE RATES

Dimecoin Wallet reads this feed from "DimecoinAverage" for getting exchange rates:

    https://apiv2.bitcoinaverage.com/indices/global/ticker/short?crypto=DIME

We chose this feed because it is not dependent on a single exchange. However, you should keep in
mind it's always a 24h average. This feature can be disabled with the compile-time flag

    Constants.ENABLE_EXCHANGE_RATES


### SWEEPING WALLETS

When sweeping wallets, Dimecoin Wallet uses a set of Electrum servers to query for unspent transaction
outputs (UTXOs). This feature can be disabled with the compile-time flag:

    Constants.ENABLE_SWEEP_WALLET
