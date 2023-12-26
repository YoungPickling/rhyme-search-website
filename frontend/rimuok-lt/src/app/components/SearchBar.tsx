"use client"
import React, { useState, useEffect } from "react"
// import { JsonResponse, SuggestionResponse, CountModel } from "@/app/page";
import { useRouter } from "next/navigation";
import Image from "next/image";

export default function SearchBar() {
  const router = useRouter()
  // const emptyJsonResponse : JsonResponse = { in: "", va: [], rv: [], end: "", co: [], res: [] };
  // const emptySuggestionResponse : SuggestionResponse = { vowelAt: [], indexes: [], end: "" };

  // Search input handler
  const [searchInput, setSearchInput] = useState<string>(""); // search
  const [validInput, setValidInput] = useState<boolean>(true); // search
  // const [savedInput, setSavedInput] = useState<string>(""); // ?
  
  // Fetched results and related data
  // const [totalResults, setTotalResults] = useState<number>(0); // main
  // const [searchResults, setSearchResults] = useState<JsonResponse>(emptyJsonResponse); // main
  // const [suggestion, setSuggestion] = useState<SuggestionResponse>(emptySuggestionResponse); // main

  // const [isLoading, setIsLoading] = useState(false); // main
  
  // "show more" functionality
  // const [showMoreButtons, setShowMoreButtons] = useState<boolean[]>([]); // main
  // const [nextPageList, setNextPageList] = useState<number[]>([]); // main

  // const inputRef = useRef<string>()

  useEffect(() => {
    setValidInput( Boolean(searchInput === "" || searchInput.match(/^[a-pr-vyza-pr-vyząčęėįšųūž]*$/i)) );
  }, [searchInput])

  const handleSearch = async (e:any) => {
    e.preventDefault();

    if(searchInput === "" || !validInput) return

    router.push(
      `/?${new URLSearchParams({
      q: searchInput,
    })}`)

    // setIsLoading(true);

    // fetch(`http://192.168.10.127:8081/api/search/aso/${searchInput}`)
    // .then((response) => {
    //   if (!response.ok) {
    //     throw response.json();
    //   }

    //   setSavedInput(searchInput);
    //   setSearchResults(emptyJsonResponse);
    //   setSuggestion(emptySuggestionResponse);
    //   setShowMoreButtons([]);

    //   return response.json();
    // })
    // .then((data: JsonResponse | SuggestionResponse) => {
    //   if((data as SuggestionResponse).vowelAt === undefined) {
    //     setSearchResults(data as JsonResponse);
    //     setNextPageList(Array.from({ length: (data as JsonResponse).co.length }, () => 2));
    //     setShowMoreButtons((data as JsonResponse).co.map((group : CountModel) => group.rc >= 100));
        
    //     let sum = 0;
    //     (data as JsonResponse).co.forEach((el : CountModel) => {
    //       sum = sum + el.rc
    //     });

    //     setTotalResults(sum)

    //     console.log(data)
    //     console.log((data as JsonResponse).in)
        
    //     router.push(`/?q=${encodeURIComponent(savedInput)}&index=${encodeURIComponent((data as JsonResponse).in)}${ data.end === `` ? `` : `&end=${encodeURIComponent(data.end)}` }`)
    //   } else if((data as JsonResponse).in === undefined) {
    //     setSuggestion(data as SuggestionResponse)

    //     router.push(`/?s=${encodeURIComponent(savedInput)}`)
    //   }      
    // })
    // .catch((error) => {
    //   console.log(error)
    //   // if (error instanceof Error) {
    //   //   // Handle the error directly
    //   //   console.error(error.message);
    //   // }
    //   // error.then((errorData: SuggestionResponse) => {
    //   //   if(errorData.vowelAt?.length > 0) {
    //   //     // console.log("error catched")
    //   //     console.log(errorData)
    //   //   } else {
    //   //     console.log("ERROR not catched")
    //   //     console.error(errorData);
    //   //   }
    //   // })
    // })
    // .finally(() => {
    //   setIsLoading(false);
    // })
  }

  const handleChange = (e: any) => {
    setSearchInput(e.target.value);
  };

  return (
    <form className="r_center_form" onSubmit={handleSearch}> {/* action={searchProducts} onSubmit={handleSearch} */}

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
            // style={{ display: (!validInput ? "block" : "none") }}
          >Įveskite žodį lietuviškomis raidėmis be tarpų
          </div>
        </div>

        <div>
          <button 
          type="submit" 
          className="btn btn-light"
          disabled={searchInput.length < 1 ? true : false}
          >
            <Image src="/search_icon.svg" width="32" height="32" alt="Paieška" />
          </button>
        </div>

      </form>
  )
}
