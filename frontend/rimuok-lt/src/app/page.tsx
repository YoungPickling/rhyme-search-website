"use client"
import RimuokLogo from "@/components/RimuokLogo"
import React, { useState, FormEvent, useEffect, useRef  } from "react"
import Image from "next/image"
import { useRouter, useSearchParams } from "next/navigation"
import { Base64 } from "js-base64";

interface CountModel {
  sc: number;
  rc: number;
}

interface WordModel {
  wo: string;
  sy: number;
  sa: number;
  st: number;
  ps: number;
}

interface JsonResponse {
  in: string;
  co: CountModel[];
  res: WordModel[][];
}

export default function Home() {
  const stressSigns = ["","̀","́","̃"];

  const [searchInput, setSearchInput] = useState<string>("");
  const [showMoreButtons, setShowMoreButtons] = useState([]);

  const [rhymeIndex, setRhymeIndex] = useState<string>("");
  const [totalResults, setTotalResults] = useState<number>(0);
  const [searchResults, setSearchResults] = useState<JsonResponse>({ in: "", co: [], res: [] });

  const router = useRouter()
  const [inputBar, setInputBar] = useState("")
  const [Items, setItems] = useState([])
  const [isLoading, setIsLoading] = useState(false);

  const inputRef = useRef<string>()

  const searchParams = useSearchParams()
  let queryWordBuffer = searchParams.get("q") || ""
  const queryWord = Base64.decode(queryWordBuffer)

  useEffect(() => {
    if(queryWord != null) {
      setSearchInput(queryWord);
    }
  }, [])

  useEffect(() => {
    if(searchResults && searchResults.co && searchResults.co.length !== 0) {
      let sum = 0
      searchResults.co.forEach((el) => {
        sum = sum + el.rc
      });
      setTotalResults(sum)
    }
  }, [searchResults])

  useEffect(() => {
    // Check if the conditions are met to show the buttons
    const newShowFetchButtons = searchResults.res.map((syllableGroup) => {
      const rcSum = syllableGroup.reduce((sum, word) => sum + word.rc, 0);
      return rcSum >= 100;
    });
  
    setShowMoreButtons(newShowFetchButtons);
  }, [searchResults]);

  const initialSections = Array.from({ length: 3 }, (_, index) => ({
    showMore: false,
    id: index + 1,
  }));

  const [sections, setSections] = useState(initialSections);

  const toggleShowMore = (id: number) => {
    setSections((prevSections) =>
      prevSections.map((section) =>
        section.id === id ? { ...section, showMore: !section.showMore } : section
      )
    );
  };

  const spoiler = (id: number) => {
    // const moreText = document.getElementById("more" + id) as HTMLElement;
    // const btnText = document.getElementById("btn" + id) as HTMLElement;
  
    // if (moreText.style.display === "inline") {
    //   btnText.innerHTML = "...rodyti daugiau"; 
    //   moreText.style.display = "none";
    // } else {
    //   btnText.innerHTML = "...rodyti mažiau"; 
    //   moreText.style.display = "inline";
    // }

    console.log("spoiler")
    if (typeof document === 'undefined') {
      // Do something appropriate when running in a non-browser environment
      console.log("hmm")
      return;
    }
  
    const moreText = document.getElementById("more" + id) as HTMLElement;
    const btnText = document.getElementById("btn" + id) as HTMLElement;
  
    if (moreText && btnText) {
      console.log("success")
      if (moreText.style.display === "inline") {
        moreText.style.display = "none";
        btnText.innerHTML = "Read more";
      } else {
        moreText.style.display = "inline";
        btnText.innerHTML = "Read less";
      }
    }
  }

  const handleSearch = (e:any) => {
    e.preventDefault();

    const value = inputRef.current as string;
    if(value === "") return

    setIsLoading(true);

    fetch(`http://localhost:8081/api/search/aso/${searchInput}`)
      .then((response) => response.json())
      .then((data) => {       
        setRhymeIndex(data.in)
        setSearchResults(data);
        // console.log(data);

        router.push("/?q=" + Base64.encode(searchInput));
      })
      .catch((error) => {
        console.error(error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }

  const handleShowMoreButton = (index) => {
    // Implement the logic to fetch data when the button is clicked
    // You can use fetch() or any other method here
    // After fetching, you may update the state or perform any other necessary actions
  };

  // const handleContinue = (e:any) => {
  //   e.preventDefault();

  //   fetch(`http://localhost:8081/api/search/aso/${searchInput}`)
  //     .then((response) => response.json())
  //     .then((data) => {       
  //       setRhymeIndex(data.in)
        
  //       setSearchResults(data);
  //       console.log(data);

  //       router.push("/?q=" + Base64.encode(searchInput));
  //     })
  //     .catch((error) => {
  //       console.error(error);
  //     });

  // }

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

        searchResults.res.length === 0 ?
        (<></>) : (
        
        <div className="r_card">
          <div className="r_card_body">
            <h6 className="card-subtitle mb-2 text-muted">rasta {totalResults}</h6>

            {searchResults.res.map((syllableGroup, index) => (
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
                 <div className=" r_devider"> {/* r_rhyme_box_devider */}
                  {showMoreButtons[index] && (
                    <button onClick={() => handleShowMoreButton(syllableGroup[0].sy)}>rodyti daugiau</button>
                  )}
                </div>
              </div>
            ))}
            
            {/* {sections.map((section) => (
              <div key={section.id}>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                </p>
                {section.showMore && (
                  <p>
                    Additional content for section {section.id}.
                  </p>
                )}
                <div className="r_devider">
                  <u onClick={() => toggleShowMore(section.id)} style={{cursor:"pointer"}}>
                    {section.showMore ? "...rodyti mažiau" : "...rodyti daugiau"}
                  </u>
                </div>  
              </div>
            ))} */}


            {/* {sections.map((section) => (
              <div key={section.id}>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                </p>
                {section.showMore && (
                  <p>
                    Additional content for section {section.id}.
                  </p>
                )}
                <div className="r_devider">
                  <u onClick={() => toggleShowMore(section.id)} style={{cursor:"pointer"}}>
                    {section.showMore ? "...rodyti mažiau" : "...rodyti daugiau"}
                  </u>
                </div>  
              </div>
            ))} */}
            
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
