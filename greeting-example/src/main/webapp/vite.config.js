import { defineConfig } from 'vite';

export default defineConfig({
  build: {
    outDir: '../../../target/generated/wasm',
    emptyOutDir: false, // Don't empty the dir since TeaVM outputs are there too
    rollupOptions: {
      input: {
        main: 'index.html'
      },
      output: {
        // Preserve the structure for the WAR plugin
        assetFileNames: '[name].[ext]',
        chunkFileNames: '[name].js',
        entryFileNames: '[name].js'
      }
    }
  },
  server: {
    port: 3000,
    open: true
  },
  // Ensure compatibility with WebAssembly
  optimizeDeps: {
    exclude: ['teavm']
  }
});
