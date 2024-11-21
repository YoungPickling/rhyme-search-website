/** @type {import("next").NextConfig} */
const nextConfig = {
  env: {
    API_BASE_URL: process.env.ENV_API_BASE_URL,
    SITE_BASE_URL: process.env.ENV_SITE_BASE_URL
  }
}

module.exports = nextConfig
