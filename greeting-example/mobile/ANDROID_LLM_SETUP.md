# Android LLM Setup

Setup instructions for on-device AI with MediaPipe Gemma 3.

## Requirements

- Android device with 4GB+ RAM
- ~530MB storage for Gemma 3 1B model

## Quick Setup

1. **Download the model**

```bash
cd greeting-example/mobile/android/app/src/main/assets/models

# Download Gemma 3 1B INT4 quantized model (~529MB)
wget https://huggingface.co/litert-community/Gemma3-1B-IT/resolve/main/gemma3-1b-it-int4.task

# Or use curl
curl -L -o gemma3-1b-it-int4.task https://huggingface.co/litert-community/Gemma3-1B-IT/resolve/main/gemma3-1b-it-int4.task
```

**Note**: The `models/` directory is gitignored, so model files won't be committed to your repository.

2. **Build and deploy**

```bash
# From project root
mvn clean package

cd greeting-example/mobile
npm install
npm run cap:sync

# Open in Android Studio
npm run cap:android

# Or run directly
npm run cap:run:android
```

That's it! The app will automatically detect and load the model from assets.

## How It Works

The [LLMInference.java](android/app/src/main/java/com/cromoteca/palette/LLMInference.java) class:
- Automatically checks for `gemma3-1b-it-int4.task` in `assets/models/`
- Copies it to cache directory on first run
- Initializes MediaPipe with Gemma 3 1B
- Shows helpful error message if model is missing

**No code changes needed** - just drop the model file in `assets/models/`!

## Expected Performance

- **Speed**: ~2585 tokens/sec on prefill
- **Model size**: 529MB (INT4 quantized)
- **Memory**: 4GB+ RAM recommended
- **Latency**: First run slower (model copy), subsequent runs fast (cached)

## Testing Without Model

The app works without a model file - it will show instructions for downloading the model. Useful for testing UI and framework integration.

## Model Information

**Gemma 3 1B INT4** (Recommended)
- Size: 529MB
- Performance: Fast on mobile
- Download: [HuggingFace - litert-community/Gemma3-1B-IT](https://huggingface.co/litert-community/Gemma3-1B-IT)

This is a quantized version optimized for mobile deployment with MediaPipe LLM Inference API.

## Troubleshooting

**Model not loading**
```bash
adb logcat | grep LLMInference
```
Check logs for initialization errors.

**Out of memory**
- Close other apps
- Use INT4 quantized model (already the default)
- Ensure device has 4GB+ RAM

**Slow performance**
- First run is slower (copies model to cache)
- Subsequent runs use cached model
- GPU acceleration is automatic via MediaPipe
