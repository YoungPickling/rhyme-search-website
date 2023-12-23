import type { Metadata } from "next"
import RimuokLogo from "@/components/RimuokLogo"
import { Patrick_Hand } from "next/font/google"
import Script from "next/script";
import "./bootstrap.css"
import "./style.css"

const patrickHand = Patrick_Hand ({ 
  subsets: ["latin","latin-ext"],
  weight: ["400"],
 })

export const metadata: Metadata = {
  title: "Rimuok.lt - rimų paieškos svetainė",
  description: "Rimų žodinas, skirtas muzikantams, poetams ir ne tik.",
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
            <p>© 2023 visos teisės saugomos</p>
          </footer>
        </div>
      </body>
    </html>
  )
}
