# Palette Mobile Setup with Capacitor

Your Wasm application is now ready for mobile deployment! 🚀

## Prerequisites

Before building for Android, make sure you have:
1. **Android Studio** installed (for Android development)
2. **Java Development Kit (JDK)** (version 11 or newer)
3. **Android SDK** configured in Android Studio

## Quick Start (Android)

```bash
# 1. Build server-side / WASM artifacts
mvn clean package

# 2. Enter web app (first time: install deps)
cd src/main/webapp
npm install        # first time only

# 3. Build + copy web assets + sync native project
npm run cap:build

# 4. Open Android Studio (for emulator/device run & debug)
npm run cap:android

# (Alternative direct deploy without opening the IDE)
# npm run cap:run:android
```

That is the 90% workflow. Re-run steps 1 (if Java/WASM changed) and 3 for each iteration.

## Detailed Steps (What Happens)

1. `mvn clean package` – compiles Java, runs TeaVM, produces `target/generated/wasm/teavm/*` and Vite build output.
2. `npm run cap:build` – runs Vite build (again, ensuring web assets reflect latest source), then copies assets into the native Android project and syncs Capacitor plugins.
3. `npm run cap:android` – opens the Android project in Android Studio.
4. `npm run cap:run:android` – builds & deploys directly to an attached device/emulator (skipping interactive IDE launch).

## Features

✅ **Local Functionality**: The "Say Hello (Local)" button will work perfectly on mobile
❌ **Server Calls**: The "Say Hello (Server)" button won't work (as expected, since there's no server)

## Mobile Optimizations Applied

- ✅ HTTPS scheme configured for Android security
- ✅ Web assets properly configured to point to generated WASM output
- ✅ WebAssembly compatibility maintained
- ✅ Responsive viewport settings

## Iteration Workflow

1. Edit Java / client code
2. `mvn package` (needed only if Java → WASM changed)
3. `npm run cap:build`
4. Launch / rerun from Android Studio (hot swap some resources) or `npm run cap:run:android`

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

## Advanced / Manual Capacitor Commands (Optional)

You rarely need these individually, but they help when troubleshooting or doing partial updates:

```bash
# Copy updated web assets only (after a manual front-end build)
npx cap copy android

# Copy + update native plugins (run after adding/removing a Capacitor plugin)
npx cap sync android

# Open Android project directly
npx cap open android

# Deploy/run without using the npm script wrapper
npx cap run android
```

Prefer the npm scripts for consistency. Drop to these when isolating a failing step, skipping an unnecessary rebuild, or performing a plugin-only sync.

## Notes on npm vs npx

- Scripts (`npm run cap:*`) enforce a consistent sequence and are easier for teams.
- `npx cap <cmd>` still uses the locally installed CLI (no global mismatch) and is fine for ad‑hoc use.
- If in doubt, use the script variant first.

