/** @type {import("next").NextConfig} */
const nextConfig = {
  env: {
    ENV_API_BASE_URL: process.env.ENV_API_BASE_URL
  }
}

module.exports = nextConfig
