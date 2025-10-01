# Project: Palette

A Java UI framework prototype that allows Java developers to build web and mobile applications entirely in Java, without touching JavaScript frameworks.

## Goal

Enable Java developers to stay in their comfort zone:
- Write UI code in Java (not JavaScript/TypeScript)
- Compile to WebAssembly via TeaVM
- Deploy to browser and mobile (Capacitor)
- Use standard Web Components (Shoelace) through Java wrappers

## ⚠️ Prototype Project

This is a **prototype/experimental framework**. Code is throwaway quality:
- **No backwards compatibility needed** - feel free to make breaking changes
- **No history preservation required** - rewrite/refactor aggressively as needed
- Focus on rapid iteration and experimentation over production quality
- Framework and demo are currently **mixed in a single application**

## Tech Stack

- **Backend**: Java + TeaVM (compiles to WebAssembly)
- **Frontend**: Vite, Shoelace Web Components
- **Mobile**: Capacitor (Android support)
- **Build**: Maven + npm

## Project Structure

- `src/main/java/com/cromoteca/palette/`
  - `client/` - TeaVM client code (compiled to WASM)
    - `components/` - Java wrappers for Shoelace web components
    - `views/` - Application views
    - `services/` - HTTP services
  - `server/` - Java servlets
  - `shared/` - Shared Java code
- `src/main/webapp/` - Web application root
  - Frontend assets, Vite config, Capacitor setup
- `docs/` - Documentation
- `target/` - Build output (excluded from git)

## Build & Development

```bash
# Full build and start server
mvn clean package jetty:run

# Frontend development (from src/main/webapp)
cd src/main/webapp
npm run dev

# Mobile build
cd src/main/webapp
npm run cap:android
```

## Key Files

- `pom.xml` - Maven configuration, TeaVM setup
- `src/main/webapp/package.json` - Frontend dependencies
- `src/main/webapp/shoelace-components.js` - Shoelace component imports
- `src/main/webapp/theme.css` - Theme customization
- `mobile-setup.md` - Capacitor/Android setup guide

## Notes

- TeaVM compiles Java to WASM for client-side execution
- Shoelace components are wrapped in Java for type-safe usage
- Mobile builds work offline (no server API calls in mobile context)
