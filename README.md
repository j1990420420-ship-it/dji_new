# DJI Android App Setup Guide

Follow these steps to get your DJI app working on your Pixel 9 Pro phone.

## Step 1: Get Your DJI API Key

1. Visit [DJI Developer Portal](https://developer.dji.com/)
2. Sign up or log in with your DJI account
3. Go to "My Apps" and create a new app
4. Copy your **API Key**: `86f8b445a80476aeec8f1162`

## Step 2: Clone the Repository

```bash
git clone https://github.com/j1990420420-ship-it/dji_new.git
cd dji_new
```

## Step 3: Open in Android Studio

1. Open Android Studio
2. Click **File → Open**
3. Select the `dji_new` folder
4. Wait for Gradle to sync

## Step 4: Connect Your Pixel 9 Pro

1. Enable USB Debugging:
   - Settings → About Phone → tap Build Number 7 times
   - Settings → Developer Options → USB Debugging (ON)
2. Connect via USB cable to your computer

## Step 5: Build & Run

1. Click the green **Run** button in Android Studio
2. Select your Pixel 9 Pro device
3. Wait for the app to build and install

## Step 6: Test the App

1. Open the app on your phone
2. You should see **"Register Success"** message
3. Connect your DJI drone via remote control
4. The app will show live video feed from your drone

## Project Structure

```
dji_new/
├── app/
│   ├── src/main/
│   │   ├── java/com/dji/sdkdemo/
│   │   │   ├── MApplication.java          (SDK initialization)
│   │   │   ├── DemoApplication.java        (SDK callbacks)
│   │   │   └── MainActivity.java           (Main UI)
│   │   ├── res/
│   │   └── AndroidManifest.xml            (Permissions & API Key)
│   └── build.gradle                       (DJI SDK V5.18.0)
└── README.md
```

## Troubleshooting

### "Register Failed" Error
- Check your API Key in AndroidManifest.xml
- Ensure internet connection is active
- Verify your DJI app is registered at developer.dji.com

### "Permission Denied" Error
- Make sure all permissions are granted on your Pixel 9 Pro
- Go to Settings → Apps → DJI App → Permissions
- Enable all required permissions

### USB Connection Issues
- Try a different USB cable
- Enable USB Debugging on your phone
- Restart Android Studio and your phone

## API Key Information

- **App Name:** dji
- **Package Name:** com.dji.sdkdemo
- **API Key:** 86f8b445a80476aeec8f1162
- **Category:** Film shooting
- **SDK Version:** Mobile SDK V5.18.0

## Next Steps

Once the app is running, you can:
- ✅ See live video feed from your drone camera
- ✅ Control drone movement
- ✅ Adjust camera settings
- ✅ Take photos/videos

For more DJI SDK features, visit the [DJI Developer Documentation](https://developer.dji.com/doc/).

---

**Happy Flying! 🚁**
