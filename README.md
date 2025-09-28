# TeaVM WebAssembly + Vaadin Lit Components

This project demonstrates how to integrate Vaadin Lit Components with TeaVM WebAssembly to mimic Flow in the browser.

## Quick Start

```bash
# Build and start development server
mvn clean package jetty:run
```

Then open browser to http://localhost:8080

## Mobile Deployment 📱

This application can be deployed to Android devices using Capacitor! See [mobile-setup.md](mobile-setup.md) for detailed instructions.

### Quick Mobile Build
```bash
# Build the project
mvn clean package

# Sync with mobile app (from src/main/webapp)
cd src/main/webapp
npm run cap:android
```

### Features on Mobile
- ✅ Full UI functionality (TextField, Buttons, Notifications)
- ✅ Local Java logic compiled to WASM  
- ✅ Vaadin web components
- ❌ Server API calls (expected limitation)
