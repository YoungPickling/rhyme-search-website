import type { Metadata } from "next"
import { SITE_BASE_URL } from "../config";

export async function generateMetadata() : Promise<Metadata> {
  const pageName = "Kaip naudotis";
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

export default function HowToUsePage() {
  return (
    <div className="r_card r_article">
      <article className="r_card_body">
        <h1>Kaip naudotis</h1>
        <p>Paieškos laukelyje įveskite norimą žodį lietuviškomis raidemis be tarpų, 
          skaičių ir kitų simbolių. Paskui galite paspausti mygtuką &#34;ieškoti&#34; 
          su nupieštu didinamuoju stiklu arba klaviatūroje paspausti mygtuką &#34;ENTER&#34;</p>
      </article>
    </div>
  )
}