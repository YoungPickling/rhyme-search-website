import type { Metadata } from "next"
import { CONTACT_EMAIL, SITE_BASE_URL } from "../config";

export async function generateMetadata() : Promise<Metadata> {
  const pageName = "Kontaktai";
  const titleName = `${pageName} | Rimuok.lt puslapis`;
  const urlName = `${SITE_BASE_URL}/api/og?${new URLSearchParams({q: pageName})}`;

  return {
    title: titleName,
    openGraph: {
      title: titleName,
      images: {
        url: urlName,
        width: 1200,
        height: 630,
        alt: titleName,
      }
    },
    twitter: {
      title: urlName,
      images: {
        url: urlName,
        alt: `Rimuok.lt ${pageName}`,
      }
    },
  };
}

export default function DonatePage() {
  return (
    <div className="r_card r_article">
      <article className="r_card_body">
        <h1>Kontaktai</h1>
        <p>Jei turite klausimų arba pasiūlymų, susisiekite su mumis el. paštu:</p>
        <a href="mailto:bigyellowmagic@gmail.com" style={{fontSize:"1.45em"}}>{CONTACT_EMAIL}</a>
      </article>
    </div>
  )
}