"use client"
import Link from "next/link"
import { useState, useEffect } from "react"
import { getCookie, setCookie } from "./CookieFunctions";
import { isNull } from "util";

export default function Cookies() {
  const [close, setClose] = useState<boolean>(true);

  useEffect(() => {
    setClose(getCookie("theme").length > 0)
  },[])

  const handleConsent = () => {
    const rContainerElement = document.querySelector(".r_container");
    // setCookie("consent", "true", 365)

    if (rContainerElement) {
        const backgroundColor = window.getComputedStyle(rContainerElement).backgroundColor;

        // Logging the background-color value
        // console.log('Background color:', backgroundColor);

        if(backgroundColor === "rgb(0, 0, 0)")
          setCookie("theme", "dark", 365)
        else
          setCookie("theme", "light", 365)

    } else {
      setCookie("theme", "light", 365)
    }

    console.log(getCookie("theme"))
    setClose(true)
  }
  
  return(<>
    {close ? (<></>) : 
    (<div className="r_ck_container">
      <div className="r_ck">
        <p>
          Norint užtikrinti teikiamų paslaugų kokybę, ši svetainė naudoja funkcinius 
          slapukus. <Link href={"/privacy-policy"}
          >Privatumo politika</Link>
        </p>
        <div className="r_ck_btn_section">
          <Link href="/privacy-policy" className="r_ck_btn_l">
            <button className="r_ck_btn r_ck_btn_dark r_ck_btn_l">Daugiau</button>
          </Link>
          <button className="r_ck_btn r_ck_btn_dark r_ck_btn_mid" onClick={() => setClose(true)}>Uždryti</button>
          <button className="r_ck_btn ml-2 r_ck_btn_r" onClick={handleConsent}>Sutinku</button>
        </div>
      </div>
    </div>
    )}
  </>)
}