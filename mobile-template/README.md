# Mobile Template

This directory contains templates for setting up Capacitor mobile projects.

## Setup New Mobile App

1. Copy `capacitor.config.template.json` to your app's `mobile/` directory as `capacitor.config.json`
2. Copy `package.template.json` to your app's `mobile/` directory as `package.json`
3. Update the values:
   - `appId`: Your app's unique identifier (e.g., `com.example.myapp`)
   - `appName`: Your app's display name
   - `name` in package.json: Your app's npm package name

4. Initialize Capacitor:
   ```bash
   cd your-app/mobile
   npm install
   npx cap add android
   ```

5. Build and run:
   ```bash
   # From project root
   mvn clean package

   # From mobile directory
   cd your-app/mobile
   npm run cap:android
   ```
