"use client"
import React, { useState, useEffect } from "react"
import { useRouter } from "next/navigation";
import Image from "next/image";

export default function SearchBar() {
  const router = useRouter()
  // const emptySuggestionResponse : SuggestionResponse = { vowelAt: [], indexes: [], end: "" };

  // Search input handler
  const [searchInput, setSearchInput] = useState<string>(""); // search
  const [validInput, setValidInput] = useState<boolean>(true); // search
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    setValidInput( Boolean(searchInput === "" || searchInput.match(/^[a-pr-vyza-pr-vyząčęėįšųūž]*$/i)) );
  }, [searchInput])

  const handleSearch = async (e:any) => {
    e.preventDefault();

    if(searchInput === "" || !validInput) return
    setLoading(true);

    router.push(
      `/?${new URLSearchParams({
      q: searchInput,
    })}`)

  }

  const handleChange = (e: any) => {
    setSearchInput(e.target.value);
  };

  return (
    <>
    {loading ? (
      <div className="r_loading">
      <h1>Kraunasi...</h1>
    </div>
    ) : (
      <form className="r_center_form" onSubmit={handleSearch}>

      <div className="search_bar">
        <input 
          className={`form-control form-control-lg r_font ${!validInput ? "is-invalid" : ""}`}
          type="text"
          name="searchQuery"
          value={searchInput}
          onChange={handleChange}
          placeholder="įveskite žodį"
          id="search"
        />
        <div 
          className="invalid-feedback r_fat_outline"
        >Įveskite žodį lietuviškomis raidėmis be tarpų
        </div>
      </div>

      <div>
        <button 
        type="submit" 
        className="btn btn-light"
        disabled={searchInput.length < 1 ? true : false}
        aria-label="Paieška"
        >
          <Image src="/search_icon.svg" width="32" height="32" alt="Paieška"/>
        </button>
      </div>

    </form>
    )}
    
    </>
  )
}
