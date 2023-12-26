import type { Metadata } from "next"
import RimuokLogo from "./components/RimuokLogo"
import { patrickHand } from "./font";
import Script from "next/script";
import "./bootstrap.css"
import "./style.css"

export const metadata: Metadata = {
  metadataBase: new URL('http://192.168.10.127:3000'),
  title: "Rimuok.lt - rimų paieškos svetainė",
  description: "Lietuviškų rimų žodinas su išplėstinė paieška, skirta muzikantams, poetams ir ne tik.",
  openGraph: {
    title: "Rimuok.lt - rimų paieškos svetainė",
    description: "Rimu paieškos sistema su išplėstinė paieška, skirta muzikantams, poetams ir ne tik.",
    images: {
      url: "http://192.168.10.127:3000/api/og",
      width: 1200,
      height: 630,
      alt: "Rimuok.lt žodžių paieška"
    }
  },
  twitter: {
    card: "summary_large_image",
    title: 'Rimuok.lt - rimų paieškos svetainė',
    description: "Rimu paieškos sistema su išplėstinė paieška, skirta muzikantams, poetams ir ne tik.",
    creator: '@Neeemax',
    images: {
      url: 'http://192.168.10.127:3000/api/og',
      alt: 'Rimuok.lt žodžių paieška',
    }
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
        type="text/javascript" 
        src="./logoScript.js" 
        strategy="afterInteractive"
        />
      <body className={patrickHand.className}>
        <div className="r_container">
          <header>
            <RimuokLogo />
          </header>
          <main>
            {children}
          </main>
          <footer>
            <p>Maksim Pavlenko © { new Date().getFullYear() === 2023 ? "2023" : `2023-${new Date().getFullYear()}`} visos teisės saugomos</p>
          </footer>
        </div>
      </body>
    </html>
  )
}
