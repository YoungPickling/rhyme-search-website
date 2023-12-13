"use client"
import RimuokLogo from "@/components/RimuokLogo";
import Image from "next/image";
import { useSearchParams } from "next/navigation"

export default function SearchPage() {
  const searchParams = useSearchParams()

  const query = searchParams.get("q")

  return(
  <>
    <header>
      <RimuokLogo />
    </header>
    <main>
      <form></form>
      <div className="search_bar">
        <input className="form-control form-control-lg r_font" type="search" placeholder="įveskite žodį" id="search" />
      </div>
      <div>
        <button type="button" className="btn btn-light"><Image src="./search_icon.svg" width="32" height="32" alt="Paieška" /></button>
      </div>
      <div>
        {query}
      </div>
    </main>
    <footer>
      <p>© 2023 visos teisės saugomos</p>
    </footer>
  </>
  )
}