import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// TODO: maybe will need this https://stackoverflow.com/questions/69744253/vite-build-always-using-static-paths

export default defineConfig({
  plugins: [react()]
})
