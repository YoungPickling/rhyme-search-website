"use client"
import Image from "next/image"
import { setCookie, getCookie } from "./CookieFunctions"
import { useState, useEffect } from "react";

export default function ThemeButton() {
  const [theme, setTheme] = useState<string>("light");

  useEffect(() => {
    if(getCookie("theme").length > 0) {
      setTheme(getCookie("theme"))

      if(getCookie("theme") === "dark")
        document.body.classList.add("r_dark_theme")
    }
  },[])

  const handleTheme = () => {
      document.body.classList.toggle("r_dark_theme")

      if(theme === "light")
        setTheme("dark")
      else 
        setTheme("light")
      
      if(getCookie("theme").length > 0) {
        if(theme === "light") 
          setCookie("theme", "dark", 365)
        else 
          setCookie("theme", "light", 365)
      }
  }
  
  return (
  <button 
    className="r_theme_btn"
    onClick={handleTheme}  
  >
    <Image 
      style={{width:"100%",height:"auto"}}
      src={theme === "dark" ? "/moon.svg" : "/sun.svg"}
      alt="Keisti temą"
      width={60}
      height={60}
      />
  </button>
  )
}