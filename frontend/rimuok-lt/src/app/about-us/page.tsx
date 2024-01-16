import type { Metadata } from "next"
import { SITE_BASE_URL } from "../config";

export async function generateMetadata() : Promise<Metadata> {
  const pageName = "Apie mus";
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
        <h1>Apie mus</h1>
        <p>Sveiki, mano vardas Maksimas Pavlenko. 
          Aš esu profesionalus bosistas ir programuotojas. 
          Man dažnai užduoda tokį klausimą - kam tu sukūrei šitą svetainę? 
          Viskas prasidėjo kovido laikais nuo mano kuklių savarankiškų bandymų rašyti dainas. 
          Ieškodamas kaip užbaigti eilutę juodraštyje, užklydau į keletą rimų paieškos svetainių Anglų ir Rusų kalbomis. 
          Dauguma iš jų buvo paprastos, vieni rodė geresnius rezultatus ir turėjo filtrus, kitų dizainas atsilikdavo dvidešimčiai metų.
        </p>

        <p>Panašių svetainių Lietuvių kalba beveik nebuvo. 
          Remiantis ankstesnį patirtį naudojant kitų kalbų rimų žodynus, aš padariau geriausią tokio pobūdžio Lietuvišką svetainę. 
          Buvo panaudotos šiolaikines tinklalapio kurimo technologijos ir sukurtas išplestinis paieškos funkcionalas, 
          leidžiantis pasirinkti pageidaujamą kalbos dalį arba rimavimo rūšį. 
          Tai duoda galimybė netik ieškoti rimus rašant eileraščius, bet ir dainas bei sunkiai rimuotą repą.</p>

        <p>Kas naudos šitą svetainę? Visi smalsus ir atvirų pažiūrų žmonės. 
          Rimuok.lt yra unikalus instrumentas, padedantis visiemas, kas dirba su rimuotu tekstu.</p>

        <p>Šis projektas pastoviai vystomas ir atnaujinamas. Jeigu jūs turite idėjų arba pasūlymų kaip pagerinti naudotojų patirtį, interfeisą arba funkcionalūmą, galite laisvai susisiekti su manimi nuejus į skiltį <b>Kontaktai</b>.</p>

        <p style={{display:"none"}}>Rimuok.lt yra nemokamas rimų paieškos portalas, todėl kviečiame paaukoti projektu.</p>
      </article>
    </div>
  )
}