import { useState } from "react"

export default function Cookies() {
  
  return(
  <div className="r_ck_container">
    <div className="r_ck">
      <p>
        Norint užtikrinti teikiamų paslaugų kokybę, ši svetainė naudoja sausainiukus (slapukus). Privatumo politika
      </p>
      <div className="r_ck_btn_section">
        <button className="r_ck_btn r_ck_btn_dark r_ck_btn_l">Nustatymai</button>
        <button className="r_ck_btn r_ck_btn_dark r_ck_btn_mid">Uždryti</button>
        <button className="r_ck_btn ml-2 r_ck_btn_r">Sutinku</button>
      </div>
    </div>
  </div>
  )
}