import type { Metadata } from "next"
import { SITE_BASE_URL } from "../config";
// import PaypalDonateButton from "../components/PaypalDonateButton";

export async function generateMetadata() : Promise<Metadata> {
  const pageName = "Paaukoti projektui";
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
        <h1>Paaukoti projektui</h1>
        <p></p>
        {/* <PaypalDonateButton /> */}
      </article>
    </div>
  )
}