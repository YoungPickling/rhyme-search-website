// export const API_BASE_URL = "https://rimuok.lt"; // "http://192.168.10.127:8081"
// export const SITE_BASE_URL = "https://rimuok.lt"; // "http://192.168.10.127:3000"

// dev mode:

// export const API_BASE_URL = "http://192.168.10.127:8081";
// export const SITE_BASE_URL = "http://192.168.10.127:3000";

export const API_BASE_URL: string = typeof process.env.ENV_API_BASE_URL === 'string' ? process.env.ENV_API_BASE_URL :"http://localhost:8081";
export const SITE_BASE_URL: string = typeof process.env.ENV_API_BASE_URL === 'string' ? process.env.ENV_API_BASE_URL : "http://localhost:3000";

export const CONTACT_EMAIL: string = "bigyellowmagic@gmail.com";