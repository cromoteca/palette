# TeaVM WebAssembly + Web Components (Shoelace)

This project demonstrates wrapping standard Web Components (currently [Shoelace](https://shoelace.style/) button, input, alert) from Java using TeaVM compiled to WebAssembly. The goal is to show a serverless, component-oriented programming model fully client‑side.

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

### Features
- ✅ Full UI functionality (Input, Buttons, Toast notifications via `sl-alert`)
- ✅ Local Java logic compiled to WASM
- ✅ Themeable design (light/dark auto, `theme.css` with CSS custom property overrides)
- ✅ Mobile packaging via Capacitor
- ❌ Server API calls (expected limitation)

### Theming
Shoelace ships with light and dark themes. We import the light theme plus a conditional dark theme in `shoelace-components.js`, and override design tokens in `theme.css` (e.g. `--sl-color-primary-500`, radii, shadows). You can quickly adjust brand colors by editing those variables.

To force dark mode manually:

```js
document.documentElement.classList.add('sl-theme-dark');
```

To add more components, import their modules in `shoelace-components.js` and create a Java wrapper extending `Component` with the appropriate tag name.
