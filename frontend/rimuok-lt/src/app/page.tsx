"use client"
import RimuokLogo from "@/components/RimuokLogo"
import React, { useState, FormEvent, useEffect, useRef  } from "react"
import Image from "next/image"
import { useRouter, useSearchParams } from "next/navigation"
import { Base64 } from "js-base64";

type CountModel = {
  sc: number;
  rc: number;
}

type WordModel = {
  wo: string;
  sy: number;
  sa: number;
  st: number;
  ps: number;
}

type JsonResponse = {
  in: string;
  end: string;
  co: CountModel[];
  res: WordModel[][];
}

type ErrorMessage = {
  message: string;
  status: number;
}

function syllabify(word:string) {
  
}

export default function Home() {
  const stressSigns = ["","̀","́","̃"];

  // Search input handler
  const [searchInput, setSearchInput] = useState<string>("");

  // "show more" functionality
  const [showMoreButtons, setShowMoreButtons] = useState<boolean[]>([]);
  const [nextPageList, setNextPageList] = useState<number[]>([]);
  let nextPageArray : number[] = [];

  // Fetched results and related data
  const [totalResults, setTotalResults] = useState<number>(0);
  const [searchResults, setSearchResults] = useState<JsonResponse>({ in: "", end: "", co: [], res: [] });

  const router = useRouter()
  const [isLoading, setIsLoading] = useState(false);

  const inputRef = useRef<string>()

  const searchParams = useSearchParams()
  let queryParamBuffer = searchParams.get("q") || ""
  let queryParamDecoded = Base64.decode(queryParamBuffer)
  const queryWord = queryParamDecoded.substring(0 , queryParamDecoded.indexOf(','))

  useEffect(() => {
    if(queryWord != null) {
      setSearchInput(queryWord);
    }
  }, [])

  const handleSearch = async (e:any) => {
    e.preventDefault();

    const value = inputRef.current as string;
    if(value === "") return

    setIsLoading(true);

    fetch(`http://192.168.10.127:8081/api/search/aso/${searchInput}`)
    .then((response) => {
      if (!response.ok) {
        throw response.json();
      }
      return response.json();
    })
      .then((data: JsonResponse) => {       
        setSearchResults(data);
        setNextPageList(Array.from({ length: data.co.length }, () => 2))
        setShowMoreButtons(data.co.map((group : CountModel) => group.rc >= 100))
        let sum = 0
        data.co.forEach((el : CountModel) => {
          sum = sum + el.rc
        });
        setTotalResults(sum)
        
        router.push("/?q=" + Base64.encode(searchInput + ',' + data.in + ','  + data.end));
      })
      .catch((error) => {
        error.then((errorData: ErrorMessage) => {
          if(errorData.message === "no records") {
            console.log("error catched")
          } else {
            console.log("ERROR not catched")
            console.error(errorData);
          }
        })
      })
      .finally(() => {
        setIsLoading(false);
      });
  }

  const handleShowMoreButton = async (index: number) => {
    try {
      const response = await fetch(`http://192.168.10.127:8081/api/search/aso/${searchResults.in}/${searchResults.co[index].sc}/${nextPageList[index]}`);
      const data: WordModel[] = await response.json();
  
      setShowMoreButtons((prevShowMoreButtons) => {
        let newShowMoreButtons = [...prevShowMoreButtons];
        newShowMoreButtons[index] = searchResults.co[index].rc - 100 >= 100;
        return newShowMoreButtons;
      });

      setSearchResults((prevSearchResults) => {
        const newSearchResults = { ...prevSearchResults };
        newSearchResults.res[index] = newSearchResults.res[index].concat(data);
        newSearchResults.co[index].rc = newSearchResults.co[index].rc - 100; // data.length
        return newSearchResults;
      });

      setNextPageList((prevNextPageList) => {
        let newNextPageList = [...prevNextPageList];
        newNextPageList[index] = newNextPageList[index] + 1;
        return newNextPageList;
      });

    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e: any) => {
      setSearchInput(e.target.value);
  };

  return (
    <>
      <header>
        <RimuokLogo />
      </header>
      <main>
        <form onSubmit={handleSearch}>
          <div className="search_bar">
            <input 
              className="form-control form-control-lg r_font"
              type="text"
              value={searchInput}
              onChange={handleChange}
              placeholder="įveskite žodį"
              id="search"
            />
          </div>
          <div>
            <button type="submit" className="btn btn-light">
              <Image src="/search_icon.svg" width="32" height="32" alt="Paieška" />
            </button>
          </div>
        </form>

        <div className="r_devider">
        </div>

        { isLoading ? (
          <div className="r_loading">
            <h1>Kraunasi...</h1>
          </div>
        ) : 
        searchResults && searchResults.res as WordModel[][] && searchResults.res.length === 0 ? 
        (<></>) : (
        <div className="r_card">
          <div className="r_card_body">
            <h6 className="card-subtitle mb-2 text-muted">rasta {totalResults} žodžių</h6>

            {searchResults.res?.map((syllableGroup, index) => (
              <div key={index}>
                <h2 className="card-title">
                  {syllableGroup[0].sy === 1
                  ? '1 skiemuo'
                  : syllableGroup[0].sy >= 2 && syllableGroup[0].sy <= 9
                  ? `${syllableGroup[0].sy} skiemenys`
                  : `${syllableGroup[0].sy} sliemenų`}
                </h2>
                
                <div className="r_rhyme_box">
                  {syllableGroup.map((word, wordIndex) => (
                    <div key={wordIndex}>{word.wo.slice(0, word.sa - 1)}<b>{word.wo.slice(word.sa - 1, word.sa) + stressSigns[word.st]}</b>{word.wo.slice(word.sa)}</div>
                  ))}
                </div>

                 <div className="r_devider"> 
                  {showMoreButtons[index] && (
                    <u onClick={() => handleShowMoreButton(index)} style={{cursor:"pointer"}}>rodyti daugiau</u>
                  )}
                </div>
              </div>
            ))}

          </div>
        </div>
      )}
      </main>
      <footer>
        <p>© 2023 visos teisės saugomos</p>
      </footer>
    </>
  )
}
