# Palette

A Java UI framework for building web and mobile applications entirely in Java, without touching JavaScript.

## What is this?

Write UI code in Java → Compile to WebAssembly via TeaVM → Run in browsers and mobile apps.

Uses Shoelace web components wrapped in type-safe Java APIs.

## Project Structure

Multi-module Maven project:

- **`palette-framework/`** - Reusable UI framework (components, services)
- **`greeting-example/`** - Demo application showing framework usage
- **`mobile-template/`** - Templates for Capacitor mobile apps

## Quick Start

```bash
./run.sh
```

Visit http://localhost:8080

### Mobile (Android)
```bash
# Build WASM first
mvn clean package

cd greeting-example/mobile
npm install

# Open Android Studio
npm run cap:android

# Or run directly on device/emulator
npm run cap:run:android
```

## Framework Components

Java wrappers for Shoelace web components:
- `Button`, `TextField`, `Notification`
- `Layout`, `HorizontalLayout`, `VerticalLayout`
- `HttpService` for network requests
- `LLMService` for on-device AI inference (Android: MediaPipe Gemma 3, Browser: Mock)

Example usage in `greeting-example/src/main/java/com/example/greeting/client/views/MainView.java`

## Tech Stack

- **Java 11+** with TeaVM (Java → WebAssembly compiler)
- **Shoelace** web components
- **Vite** for frontend bundling
- **Capacitor** for mobile deployment
- **Maven** for builds

## Package Structure

- Framework: `com.cromoteca.palette.*`
- Example apps: `com.example.<appname>.*`

## Status

⚠️ **Prototype/Experimental** - Breaking changes expected. Focus on rapid iteration, not production stability.
