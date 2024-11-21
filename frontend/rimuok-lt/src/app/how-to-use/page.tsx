import type { Metadata } from "next"
import { SITE_BASE_URL } from "../config";
import Image from "next/image";

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
        <ol>
          <li>
           <p>Paieškos laukelyje įveskite norimą žodį lietuviškomis raidėmis be tarpų, 
              skaičių ir kitų simbolių. Paskui galite paspausti mygtuką <b>ieškoti </b> 
              su nupieštu didinamuoju stiklu arba klaviatūroje paspausti mygtuką <b>ENTER</b>.
            </p>
          </li>
        </ol>
        <div style={{textAlign:"center"}}>
          <Image 
          style={{width:"100%",maxWidth:"430px",height:"auto"}}
          src="/tut1.webp"
          alt="paieškos laukelio nuotrauka"
          width={430}
          height={73}
          ></Image>
        </div>

        <ol start={2}>
          <li>
            <p>Jeigu žodis įvestas teisingai, galite iškart gauti atsakymą. 
              Per vidurį didelėmis raidėmis bus parašytas ieškomas žodis. 
              Spalvotai žymima kirčiuojama raidė. Pabrauktos balsės raidės - ant jų 
              paspaudus keičiama kirčio vietą. Kirčiuotos raidės keitimas gali užtrukti kelias sekundės.
              Toliau dešinėje yra mygtukas <b>filtrai</b> ir rastų rimų skaičius. 
              Visi rezultatai rūšiuojami skiemenų skaičių tvarka bei abėcėlės tvarka.
            </p>
          </li>
        </ol>
        <div style={{textAlign:"center"}}>
          <Image 
          style={{width:"100%",maxWidth:"260px",height:"auto"}}
          src="/tut2.webp"
          alt="pavyzdys"
          width={485}
          height={433}
          ></Image>
        </div>

        <ol start={3}>
          <li>
            <p>Jeigu žodis įvestas su klaida, galite padėti kirčio ženklą 
              ant pageidaujamos pabrauktos raidės ir tęsti paiešką.</p>
          </li>
        </ol>
        <div style={{textAlign:"center"}}>
          <Image 
          style={{width:"100%",maxWidth:"260px",height:"auto"}}
          src="/tut3.webp"
          alt="pavyzdys"
          width={484}
          height={259}
          ></Image>
        </div>

        <ol start={4}>
          <li>
            <p>Atidarius skiltį <b>filtrai</b>, galite pasirinkti rimo rūšį bei kalbos dalį.</p>
            <p><b>Asonansas</b> - vienodų balsių pasikartojimas</p>
            <p><b>Galūnė</b> - rimai, kur sutampa galūnės</p>
          </li>
        </ol>
        <div style={{textAlign:"center"}}>
          <Image 
          style={{width:"100%",maxWidth:"260px",height:"auto"}}
          src="/tut4.webp"
          alt="Pavyzdys"
          width={484}
          height={392}
          ></Image>
        </div>

        <ol start={5}>
          <li>
            <p>Pasirinkus norimus filtrus, paspauskite mygtuką <b>filtruoti </b> 
            kad atnaujinti paieškos rezultatus. Norint grįžti į pagrindinį puslapį, 
            paspauskite ant <b>Rimuok.lt</b> užrašo puslapio viršuje.</p>
          </li>
        </ol>
      </article>
    </div>
  )
}