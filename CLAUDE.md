# Project: Palette

A Java UI framework for building web and mobile applications entirely in Java, without touching JavaScript.

## ⚠️ Prototype Project - IMPORTANT

This is a **prototype/experimental framework**:
- **No backwards compatibility** - make breaking changes freely
- **No history preservation** - rewrite/refactor aggressively
- **Keep it minimal** - delete everything unnecessary
- **No overproduction** - avoid creating excessive documentation, helper scripts, or unnecessary files
- **Focus on code** - rapid iteration over polish

### Documentation Policy
- README.md is for humans (GitHub visitors)
- CLAUDE.md is for you (the AI assistant)
- When implementing features, update only the README with minimal necessary info
- Don't create additional markdown files, guides, or documentation unless explicitly requested

## Tech Stack Rules

### Java
- **Use Java 11** - leverage all features available in Java 11 (var, switch expressions, etc.)
- TeaVM compiles Java to WebAssembly for client-side execution

### JavaScript
- **Use JavaScript, NOT TypeScript** - all frontend framework code is plain JavaScript
- Vite for bundling
- Shoelace web components

### Mobile
- Capacitor for Android deployment
- Android project located in `greeting-example/mobile/`

## Project Structure

Multi-module Maven project:

```
palette/
├── pom.xml                              # Root multi-module POM
├── palette-framework/                   # FRAMEWORK (reusable)
│   ├── pom.xml
│   ├── src/main/java/.../palette/
│   │   └── client/
│   │       ├── components/              # UI components
│   │       └── services/                # HttpService
│   └── src/main/resources/web/          # JavaScript/CSS assets
│       ├── shoelace-components.js
│       └── theme.css
│
├── greeting-example/                    # EXAMPLE APP
│   ├── pom.xml
│   ├── src/main/java/.../greeting/
│   │   ├── Application.java
│   │   ├── client/views/
│   │   ├── client/services/
│   │   ├── server/
│   │   └── shared/
│   ├── src/main/webapp/                 # Web frontend
│   └── mobile/                          # Capacitor Android
│
└── mobile-template/                     # Templates for new apps
```

## Package Naming

- **Framework**: `com.cromoteca.palette.*`
- **Example Apps**: `com.example.<appname>.*`

## Build & Development

### Full Build
```bash
mvn clean package
cd greeting-example
mvn jetty:run
```

### Frontend Dev
```bash
cd greeting-example/src/main/webapp
npm run dev
```

### Mobile
```bash
mvn clean package
cd greeting-example/mobile
npm install
npm run cap:android
```

## Framework Components

### Java (palette-framework)
- `client/components/` - Component, Button, TextField, Notification, Layout, HorizontalLayout, VerticalLayout, Div
- `client/services/HttpService` - HTTP utilities

### Web Assets (resources/web/)
- `shoelace-components.js` - Shoelace imports (JavaScript)
- `theme.css` - Theme customization

## Key Files

- `pom.xml` (root) - Multi-module config
- `palette-framework/pom.xml` - Framework JAR
- `greeting-example/pom.xml` - Example WAR with TeaVM, Jetty, frontend-maven-plugin
- `greeting-example/src/main/webapp/vite.config.js` - Vite config
- `greeting-example/mobile/capacitor.config.json` - Capacitor config
