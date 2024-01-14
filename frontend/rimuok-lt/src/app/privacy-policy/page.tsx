import type { Metadata } from "next"
import { SITE_BASE_URL } from "../config";

export async function generateMetadata() : Promise<Metadata> {
  const pageName = "Privatumo politika";
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

export default function PrivacyPolicyPage() {
  return (
    <div className="r_card r_article">
      <article className="r_card_body">
        <h1>Privatumo politika</h1>

        <p>Šioje privatumo politikoje aprašoma, 
          kaip internetinė svetainė Rimuok.lt (toliau - svetainė) naudoja ir saugo bet kokią jūsų pateiktą informaciją. 
          Esame įsipareigoję užtikrinti, kad jūsų privatumas būtų apsaugotas. 
          Jeigu per svetainę pateikiate mums asmeninę informaciją, 
          galite būti tikri, kad ji bus naudojama tik pagal šį privatumo pareiškimą.
        </p>

        <p>Tvarkant asmens duomenys, internetinė svetainė Rimuok.lt remiasi bendruoju duomenų apsaugos reglamentu (BDAR) arba GDPR (angl. General Data Protection Regulation)</p>

        <h1>Kokia informaciją saugojame</h1>

        <p><b>Svetainė nekaupia jokios lankytojų informacijos apie asmens duomenys. </b> 
          Kaip ir dauguma svetainių, Rimuok.lt renka neasmeniškai identifikuojančią informaciją, 
          kurią paprastai pateikia interneto naršyklės užklausos metu, pvz., naršyklės tipą, kalbos pasirinkimą, 
          nukreipiančiąją svetainę, kiekvieno lankytojo užklausos datą, laiką. 
          Svetainęs tikslas renkant neasmeniškai identifikuojančią informaciją - geriau suprasti, 
          kiek ir kaip lankytojai naudojasi svetaine bei apsisaugoti nuo ieškančių sistemos spragų interneto robotų ir skanerių.</p>

        <p>Tokia informacija laikoma svetainės serveryje neilgiau kaip 2 savaitės.</p>

        <h1>Slapukų nustatymai</h1>

        <p>Internetinė svetainė Rimuok.lt (priklausomai nuo lankytojo sutikimo) naudoja tik funkcinius slapukus, 
          leidžiančius išsaugoti pasirinktą svetainės &#34;temą&#34;, pvz. šviesiąją arba tamsiąją. Visos duomenys apie slapukus laikomos tik lankytojo naršyklėje.</p>
        
        <h1>Susisiekite su mumis</h1>

        <p>Jei turite kitų klausimų apie šią privatumo politiką arba mūsų turimą informaciją apie jus, susisiekite su mumis el. paštu:</p>
        <a href="mailto:info@rimuok.lt" style={{fontSize:"1.45em"}}>info@rimuok.lt</a>
      </article>
    </div>
  )
}