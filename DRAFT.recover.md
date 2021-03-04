Recovering Dimecoins From Android Wallet App (Draft not finished)
===================

## INTRODUCTION

This document describes how you can use a backup file on a standard PC to recover your Dimecoins. Normally, this shouldn't be needed. It is much preferred to 
just use **Options > Back up Keys > Restore Private Keys** from within the Dimecoin Wallet app if you can. This guide is only meant for rare cases:

- Your Android device is destroyed or missing and you do not want or cannot get a new Android device.
- Legislation in your country forbids you to continue using the app and you missed the chance to move your coins out while it was still legal.
- The app suddenly goes out of service for whatever reason. This event is extremely unlikely,however, is plausible.

Be aware some of the steps in this tutorial require handling your private keys in the unencrypted form. Do not expose them to anyone. Whoever knows your private keys can spend your coins on these keys. It'd good practice that after you are finished handling these keys, they should be considered compromised, even if they are not. Make sure your system is free of any malware.

We recommend using Ubuntu Linux. You can boot from a Live CD if you want, but if you do please refrain from sending your coins to a temporary wallet created in that environment, which would be lost e.g. on a power outage or computer failure. Your desired destination wallet should already be set up and you should have one of its receiving addresses or a QR code at hand.

Alternatively, you can also use Windows cmd shell or Powershell. Open Windows Search, search for and start `Command Prompt` or `Windows Powershell`. You will need administrator privledges so make sure you right click `Run as administrator`.

You should be at least a bit familiar with the Linux and shell commands. Commands `in fixed-width font like this` are meant to be executed as a shell command. Before you execute each command by pressing return, make sure to understand what it does or if will need to adjust some file or directory names. Commands starting with `sudo apt` will ask for your permission to install software by requiring your Ubuntu user password.

## PREPARATION

### Linux

On your PC, within your Linux shell, install the following Ubuntu packages if missing:

    sudo apt install openjdk-8-jdk android-tools-adb openssl git gradle

### Windows (Installing OpenSSL with Powershell and Chocolatey)

On your PC, within Powershell, you will need to install openssl if it has not previously been installed. This tutorial will use Chocolatey as a package manager. Assuming you have [Chocolately](https://chocolatey.org/) installed, your first task will be to install OpenSSL on Windows. (If not, you can use these [installation isntructions](https://chocolatey.org/install) as a guide). 

Once Chocolatey is installed open up your PowerShell console and run the command `choco install OpenSSL.Light`

### Android Device

On your Android device, go to Settings > Developer Options and enable "USB debugging". On most recent devices you will need to first go to Settings > About and tap on the devices "Build number" multiple times until you see the "You are now a developer" message.

## LOCATING THE BACKUP FILES

If you followed the app's guidance your backup files will be located on a share of the storage access framework, very likely Google Drive. Watch out for filenames starting with `dimecoin-wallet-keys-`. The date the key was created will be appended to the end of the file name in this format (YYYY-MM-DD).

Historically, the backup can also be saved to your email account (as a file attachment of an email sent to yourself) or on your SD card in the `/Download` folder. Once located, move the backup file to your PCs desktop.

## DECRYPTING

You now have your backup file on your PC. Wallet backups are encrypted. Let's decrypt it using the following command in your Windows shell (For Linux, replace dekstop with the file location of your keys on your system):

    openssl enc -d -aes-256-cbc -md md5 -a -in desktop/Dimecoin-wallet-keys-YYYY-MM-DD 
    
It will ask you for a decryption password, which is your backup password. If it prints
"bad password" you've got the wrong password, but if it doesn't print anything your password might
still be wrong. Your keys should print in the command shell if the password is input properly.

If it prints something else or nothing, you likely didn't get the password right. Passwords are case sensitive, and make sure you didn't accidentally type a space character in front or after the password.

## RECOVERING YOUR COINS AND IMPORTING INTO DESKTOP WALLET

You'll see each line contains a key in WIF (wallet import format), technically Base58. The datetime string after each key is the birthdate of that key which you can ignore for the purpose of this one-time recovery.

You can import each individual key into a PC wallet like [Windows 64](https://github.com/dime-coin/dimecoin/releases/download/1.10.0.1/dimecoin-1.10.0.1-win64-setup.exe)
or [Mac](https://github.com/dime-coin/dimecoin/releases/download/1.10.0.1/dimecoin-1.10.0.1-MacOSX.zip) using the debug console (located under Tools on the topbar menu).

Using the debug console of the desktop wallet you do the following:

   1. If encrypted, unlock it by entering 

   2. `walletpassphrase "YourPassphrase" 600`
      //this will unlock the wallet for 600 seconds(for example)
      
   3. Then use the command `importprivkey yourprivatekey`

A rescan has to occur for your balance to reflect properly. It should do it automatically 

As soon as you see your whole balance again, empty your entire wallet to the desired destination wallet. You will need to import each private key from your Android wallet indivudually. Please do not continue to use the imported wallet. Remember you just operated on unencrypted keys which can be dangerous, so it's good practice to handle them as if they were compromised, even if they were not.

## CONCLUSION

Let us know if this document helped you with recovering your coins! If you have any questions please feel free to contact us via [Email Support](mailto:developer@dimecoinnetwork.com) or [Live Support](t.me/dimeofficialsupport)
