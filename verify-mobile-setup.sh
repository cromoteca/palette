#!/bin/bash

echo "🚀 Palette Mobile Deployment Verification"
echo "========================================"

# Check if we're in the right directory
if [ ! -f "pom.xml" ]; then
    echo "❌ Please run this script from the project root directory"
    exit 1
fi

echo "✅ Project structure verified"

# Check if Android project exists
if [ ! -d "src/main/webapp/android" ]; then
    echo "❌ Android project not found. Run 'npx cap add android' first."
    exit 1
fi

echo "✅ Android project found"

# Check if WASM files exist
if [ ! -f "target/generated/wasm/teavm/classes.wasm" ]; then
    echo "❌ WASM files not found. Run 'mvn package' first."
    exit 1
fi

echo "✅ WASM files generated"

# Check if files are copied to Android
if [ ! -f "src/main/webapp/android/app/src/main/assets/public/teavm/classes.wasm" ]; then
    echo "❌ WASM files not copied to Android. Run 'npm run cap:build' first."
    exit 1
fi

echo "✅ WASM files copied to Android project"

# Check Android tools
if ! command -v java &> /dev/null; then
    echo "⚠️  Java not found. Install JDK 11+ for Android development."
else
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    echo "✅ Java found: $JAVA_VERSION"
fi

echo ""
echo "🎉 Your Palette app is ready for Android deployment!"
echo ""
echo "Next steps:"
echo "1. Install Android Studio"
echo "2. Open the Android project: npx cap open android"
echo "3. Build and run on device/emulator"
echo ""
echo "Features that will work on mobile:"
echo "✅ UI Components (TextField, Buttons, Notifications)"
echo "✅ Local Java logic compiled to WASM"
echo "✅ Web components (Shoelace)"
echo "❌ Server API calls (expected limitation)"
