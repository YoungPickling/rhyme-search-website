"use client"
import RimuokLogo from "@/components/RimuokLogo"
import React, { useState, FormEvent, useEffect, useRef  } from "react"
import Image from "next/image"
import { useRouter, useSearchParams } from "next/navigation"

interface ResultElement{
  word: string,
  distance: number,
  syllables: number,
  kdId: number,
  stress_at: number | null,
  stress_type: number | null
}

// async function getSearchResults(searchWord: string | null): Promise<string> {
//   const settings = {
//     method: "GET",
//     headers: {
//         Accept: "application/json",
//         "Content-Type": "application/json",
//     }
//   }

//   try {
//     const searchQuery = await fetch("http://localhost:8081/api/search/" + searchWord, settings).then((res) => res.json)
    
//     if (!searchQuery.ok) {
//       throw new Error(`Search request failed with status: ${searchQuery.status}`)
//     }

//     const response =  await searchQuery.json()
//       // console.log(response) 
//     return String(response)
//   } catch (e) {
//     console.log(e)
//     return String(e);
//   }    
// }

export default function Home() {
  const [searchInput, setSearchInput] = useState("");
  const [searchResults, setSearchResults] = useState([]);

  const router = useRouter()
  const [inputBar, setInputBar] = useState("")
  const [Items, setItems] = useState([])
  const [isLoading, setIsLoading] = useState(false);

  const inputRef = useRef<string>()

  const searchParams = useSearchParams()
  const queryWord = searchParams.get("q")

  // useEffect(() => {
  //   if(queryWord) { 
  //     const query = getSearchResults(queryWord)
  //     searchResult = query
  //   }
  // }, []);

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
    console.log("spoiler  ")
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

    const value = inputRef.current
    if(value === "" ) return

    fetch(`http://localhost:8081/api/search/aso/${searchInput}`)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setSearchResults(data);
      })
      .catch((error) => {
        console.error(error);
      });

    router.push("/?q=" + searchInput);
      // const query = getSearchResults(inputBar)
      // inputBar = query
      // console.log(query)
  }

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
        <div className="r_card">
          <div className="r_card_body">
            <h6 className="card-subtitle mb-2 text-muted">rasta 839</h6>
            <h4 className="card-title">1 skiemuo</h4>

            <div className="r_rhyme_box">
              <div>agni</div>
              <div>aidi</div>
              <div>aidim</div>
              <div>aidit</div>
              <div>aiksi</div>
              <div>aiksit</div>
              <div>aikštį</div>
              <div>aikštims</div>
              <div>aikštis</div>
              <div>aikštys</div>
              <div>aini</div>
              <div>ainį</div>
            </div>


            {/* <div className="r_word_container">

            </div> */}
            {/* <a href="#" className="card-link">Card link</a> */}

            {/* <span id={"more" + 1} style={{display:"none"}}><p>erisque enim ligula venenatis dolor. Maecenas nisl est, ultrices nec congue eget, auctor vitae massa. Fusce luctus vestibulum augue ut aliquet. Nunc sagittis dictum nisi, sed ullamcorper ipsum dignissim ac. In at libero sed nunc venenatis imperdiet sed ornare turpis. Donec vitae dui eget tellus gravida venenatis. Integer fringilla congue eros non fermentum. Sed dapibus pulvinar nibh tempor porta.</p></span>
            <button onChange={() => spoiler(1)} id={"myBtn" + 1}>...rodyti daugiau</button> */}
            {sections.map((section) => (
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
            ))}
            {/* <details>
              <summary>
                <div className="r_devider">
                  <u>...rodyti daugiau</u>
                </div>
              </summary>
              <p>Epcot is a theme park at Walt Disney World Resort featuring exciting attractions, international pavilions, award-winning fireworks and seasonal special events.</p>
            </details> */}
          </div>
        </div>
        {/* {searchResults.map((result: any) => (
          <li key={result.kdId}>
            <p>Word: {result.word}</p>
            <p>Distance: {result.distance}</p>
            <p>Syllables: {result.syllables}</p>
            <p>Stress At: {result.stress_at}</p>
            <p>Stress Type: {result.stress_type}</p>
          </li>
        ))} */}
      </main>
      <footer>
        <p>© 2023 visos teisės saugomos</p>
      </footer>
    </>
  )
}
