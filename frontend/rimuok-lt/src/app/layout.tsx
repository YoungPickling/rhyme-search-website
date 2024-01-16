import type { Metadata } from "next"
import RimuokLogo from "./components/RimuokLogo"
import { patrickHand } from "./font";
import Script from "next/script";
import "./bootstrap.css"
import "./style.css"
import { SITE_BASE_URL } from "./config";
// import Head from "next/head";
import Cookies from "./components/Cookies";
import Link from "next/link"
import { getCookie } from "./components/CookieFunctions";
import ThemeButton from "./components/ThemeButton";

export const metadata: Metadata = {
  metadataBase: new URL(SITE_BASE_URL),
  title: "Rimuok.lt - išplėstinė rimų paieškos svetainė",
  description: "Lietuviškų rimų žodynas su išplėstinė paieška, skirta muzikantams, poetams ir ne tik. Tobulas poeto, muzikanto ir reperio padėjėjas",
  applicationName: "Rimuok.lt",
  authors: {name: "Maksim Pavlenko"},
  robots: "index, follow",
  keywords: ["rimai", "žodynas", "asonansas", "galunė", "rimas", "filtras", "rimų paieška", "lithuanian rhymes"],
  icons: {
    icon:[
      {
        rel:"shortcut icon",
        url:"/favicon.ico"
      },
      {
        url: "/favicon-16.png",
        type: "image/png",
        sizes: "16x16"
      },
      {
        url: "/favicon-24.png",
        type: "image/png",
        sizes: "24x24"
      },
      {
        url: "/favicon-32.png",
        type: "image/png",
        sizes: "32x32"
      },
      {
        url: "/favicon-48.png",
        type: "image/png",
        sizes: "48x48"
      },
      {
        url: "/favicon-64.png",
        type: "image/png",
        sizes: "64x64"
      },
      {
        url: "/favicon-96.png",
        type: "image/png",
        sizes: "96x96"
      },
      {
        url: "/favicon-128.png",
        type: "image/png",
        sizes: "128x128"
      },
      {
        url: "/favicon-256.png",
        type: "image/png",
        sizes: "256x256"
      },
      {
        url: "/favicon-512.png",
        type: "image/png",
        sizes: "512x512"
      },
    ],
    apple:[
      {
        url: "/apple-icon-57.png",
        sizes: "57x57"
      },
      {
        url: "/apple-icon-60.png",
        sizes: "60x60"
      },
      {
        url: "/apple-icon-72.png",
        sizes: "72x72"
      },
      {
        url: "/apple-icon-76.png",
        sizes: "76x76"
      },
      {
        url: "/apple-icon-114.png",
        sizes: "114x114"
      },
      {
        url: "/apple-icon-120.png",
        sizes: "120x120"
      },
      {
        url: "/apple-icon-144.png",
        sizes: "144x144"
      },
      {
        url: "/apple-icon-152.png",
        sizes: "152x152"
      },
      {
        url: "/apple-icon-180.png",
        sizes: "180x180"
      },
    ],
  },
  openGraph: {
    title: "Rimuok.lt - išplėstinė rimų paieškos svetainė",
    description: "Rimų paieškos sistema su išplėstinė paieška, skirta muzikantams, poetams ir ne tik.",
    locale: "lt_LT",
    type: "website",
    images: {
      url: `${SITE_BASE_URL}/api/og`,
      width: 1200,
      height: 630,
      alt: "Rimuok.lt žodžių paieška"
    },
  },
  twitter: {
    card: "summary_large_image",
    title: "Rimuok.lt - išplėstinė rimų paieškos svetainė",
    description: "Rimų paieškos sistema su išplėstinė paieška, skirta muzikantams, poetams ir ne tik.",
    creator: '@Neeemax',
    images: {
      url: `${SITE_BASE_URL}/api/og`,
      alt: 'Rimuok.lt žodžių paieška',
    },
  },
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {  
  return (
    <html lang="lt">
      <Script 
        src="./logoScript.js" 
        type="text/javascript" 
        strategy="afterInteractive"
        />
      <body className={patrickHand.className} >
        <div className="r_container">
          <header>
            <RimuokLogo />
          </header>
          <nav>
              <Link href="/how-to-use">Kaip naudotis</Link><p> | </p>
              <Link href="/privacy-policy">Privatumo politika</Link><p> | </p>
              {/* <Link href="/donate">Paaukoti projektui</Link><p> | </p> */}
              <Link href="/contact-us">Kontaktai</Link><p> | </p>
              <Link href="/about-us">Apie mus</Link>
              <ThemeButton />
            </nav>
          <main>
            {children}
          </main>
          <footer>
            <p>© Maksim Pavlenko 2023-{new Date().getFullYear()} visos teisės saugomos</p>
          </footer>
        </div>
        <Cookies />
      </body>
    </html>
  )
}
