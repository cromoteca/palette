# WasmCF Mobile Setup with Capacitor

Your Wasm application is now ready for mobile deployment! 🚀

## Prerequisites

Before building for Android, make sure you have:
1. **Android Studio** installed (for Android development)
2. **Java Development Kit (JDK)** (version 11 or newer)
3. **Android SDK** configured in Android Studio

## Building for Mobile

### 1. Build the Project
```bash
# From the project root
mvn clean package

# This will:
# - Compile your Java code
# - Generate WASM files via TeaVM
# - Build web assets with Vite
```

### 2. Update Mobile App
```bash
# From src/main/webapp directory
cd src/main/webapp

# Copy web assets and sync with native project
npm run cap:build

# Or manually:
npx cap copy android
npx cap sync android
```

### 3. Open in Android Studio
```bash
# From src/main/webapp directory
npx cap open android

# Or use the npm script:
npm run cap:android
```

### 4. Run on Device/Emulator
```bash
# From src/main/webapp directory
npm run cap:run:android

# Or manually:
npx cap run android
```

## Features

✅ **Local Functionality**: The "Say Hello (Local)" button will work perfectly on mobile
❌ **Server Calls**: The "Say Hello (Server)" button won't work (as expected, since there's no server)

## Mobile Optimizations Applied

- ✅ HTTPS scheme configured for Android security
- ✅ Web assets properly configured to point to generated WASM output
- ✅ WebAssembly compatibility maintained
- ✅ Responsive viewport settings

## Development Workflow

1. Make changes to your Java code
2. Run `mvn package` to rebuild WASM
3. Run `npm run cap:build` to update mobile app
4. Test in Android Studio

## Troubleshooting

- If you see WASM loading issues, make sure all files in `target/generated/wasm/teavm/` are properly copied
- For CORS issues with local files, the Android HTTPS scheme should resolve them
- Check Android Studio logcat for any runtime errors

## What's Working

Your TeaVM WebAssembly application should work perfectly on Android with:
- UI components (TextField, Buttons, Notifications)
- Local Java logic compiled to WASM
- Standards-based web components (Shoelace)
- Full client-side functionality

The only limitation is server communication, which is expected in a mobile context without a backend.
