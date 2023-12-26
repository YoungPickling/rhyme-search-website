"use client"
import React, { useState } from "react";
import Link from "next/link";
import { patrickHand } from "../font";
import { SearchPageProps } from "../page";

//export default function Filters({ params }: { params: { searchParams: SearchPageProps /*partOfSpeech: number, rhymeType: string*/}}) {

export default function Filters({searchParams}: SearchPageProps) {
  const queryParam = searchParams.q;
  const indexParam = searchParams.index;
  const endParam = searchParams.end;

  const [showFilters, setShowFilters] = useState<boolean>(false)
  const [selectedRhymeType, setSelectedRhymeType] = useState<string>(searchParams.type !== "end" ? "aso" : searchParams.type);
  const [selectedPartOfSpeech, setSelectedPartOfSpeech] = useState<string>(searchParams.pfs === undefined || searchParams.pfs > 13 || searchParams.pfs < 0 ? "0" : searchParams.pfs + "");

  function handleShowFilters() {
    setShowFilters(!showFilters)
  }

  const handleRhymeTypeChange = (e: any) => {
    setSelectedRhymeType(e.target.value);
  };

  const handlePartOfSpeechChange = (e: any) => {
    setSelectedPartOfSpeech(e.target.value);
  };
  
  function handleSetDefaults() {
    setSelectedRhymeType("aso");
    setSelectedPartOfSpeech("0");
  }

  return (
    <>
    <button onClick={handleShowFilters} className="r_filter_button" >filtrai { showFilters ? "▲" : "▼"}</button>
    
    { !showFilters ? 
      (<></>) : (
      <>
        <div className="r_filterbox mb-3">
          <div className="mr-10">
            <fieldset className="form-group">
              <legend className="mt-2">Rimo rušis</legend>
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="radio"
                  value="aso"
                  checked={selectedRhymeType === "aso"}
                  onChange={handleRhymeTypeChange}
                />
                <label className="form-check-label">
                  Asonansas
                </label>
              </div>
              
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="radio"
                  value="end"
                  checked={selectedRhymeType === "end"}
                  onChange={handleRhymeTypeChange}
                />
                <label>
                  Galūnė
                </label>
              </div>
            </fieldset>
          </div>
          <div>
            <legend className="mt-2">Kalbos dalis</legend>
            <select 
              className="form-select" 
              style={{fontFamily: patrickHand.style.fontFamily, fontSize: "1.1em"}}
              value={selectedPartOfSpeech}
              onChange={handlePartOfSpeechChange}
            >
              <option value="0">Bet kuri</option>
              <option value="1">Būdvardis</option>
              <option value="2">Daiktavardis</option>
              <option value="3">Dalelytė</option>
              <option value="4">Dalyvis</option>
              <option value="5">Įvard. daiktavardis</option>
              <option value="6">Jaustukas</option>
              <option value="7">Jungtukas</option>
              <option value="8">Padalyvis</option>
              <option value="9">Prielinksnis</option>
              <option value="10">Prieveiksmis</option>
              <option value="11">Pusdalyvis</option>
              <option value="12">Skaitvardis</option>
              <option value="13">Veiksmažodis</option>
            </select>
          </div>
        </div>
        <div className="r_filterbox">

          <Link prefetch={false} passHref={true}
            // href="/"
            // href={`/?${new URLSearchParams({
            //   q: queryParam as string,
            //   index: indexParam as string,
            //   end: endParam as string,
            //   pfs: selectedPartOfSpeech ,
            //   type: selectedRhymeType,
            // })}`}
            href={`/?${queryParam === undefined ? `` : "q=" + encodeURIComponent(queryParam)}` + 
              `${indexParam === undefined ? `` : "&index=" + encodeURIComponent(indexParam)}` +
              `${endParam === undefined ? `` : "&end=" + encodeURIComponent(endParam)}` +
              `${selectedPartOfSpeech === "0" ? `` : "&pfs=" + encodeURIComponent(selectedPartOfSpeech)}` +
              `&type=${encodeURIComponent(selectedRhymeType)}`}
          >
            <button 
              type="button" 
              className="btn btn-success mb-4"
              >
                Taikyti nustatymus
              </button>
            </Link>

            <div></div>

            <button 
              type="button" 
              className="btn btn-secondary mb-4"
              onClick={handleSetDefaults}
              >
                Pagal nutylėjimą
            </button>
        </div>
      </>
      )}
    </>
  )
}
