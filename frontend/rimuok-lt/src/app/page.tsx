import Link from "next/link";
import SearchBar from "./components/SearchBar";
import ShowMore from "@/app/components/ShowMore";

export type CountModel = {
  sc: number;
  rc: number;
}

export type WordModel = {
  wo: string;
  sy: number;
  sa: number;
  st: number;
  ps: number;
}

export type JsonResponse = {
  in: string;
  va: number[];
  rv: string[];
  end: string;
  co: CountModel[];
  res: WordModel[][];
}

export type SuggestionResponse = {
  vowelAt: number[];
  indexes: string[];
  end: string;
}

interface SearchPageProps {
  searchParams: {
      q: string | undefined, // query word
      index: string | undefined,
      at: string | undefined,
      end: string | undefined,
      type: string | undefined,
      pfs: string | undefined,
      k: number | undefined,
    }
}

export const stressSigns = ["","̀","́","̃"];

export default async function Home({searchParams}: SearchPageProps) {
  const queryParam = searchParams.q;
  const indexParam = searchParams.index;
  const endParam = searchParams.end;
  const keyParam = searchParams.k as number;

  let searchResults: JsonResponse | null = null;
  let showSearchResults = false;

  let nextPageList: number[] | null = null;
  let showMoreButtons: boolean[];
  let totalResults: number | null = null;

  let suggestion: SuggestionResponse | null = null;
  let showSuggestion = false;

  if(queryParam !== undefined && (indexParam === undefined || endParam === undefined)) {
    await fetch(`http://192.168.10.127:8081/api/search/aso/${searchParams.q}`)
    .then((response) => {
      if (!response.ok) {
        throw response.json();
      }

      return response.json();
    })
    .then((data: JsonResponse | SuggestionResponse) => {
      if((data as SuggestionResponse).vowelAt === undefined) {
        showSearchResults = true;
        searchResults = data as JsonResponse;
        nextPageList = Array.from({ length: (data as JsonResponse).co.length }, () => 2);
        showMoreButtons = (data as JsonResponse).co.map((group : CountModel) => group.rc >= 100);
        
        let sum = 0;
        (data as JsonResponse).co.forEach((el : CountModel) => {
          sum = sum + el.rc
        });

        totalResults = sum

      } else if((data as JsonResponse).in === undefined) {
        // console.log("here")
        showSuggestion = true;
        suggestion = data as SuggestionResponse;
      }      
    })
    .catch((error) => {
      console.log(error)
    })
  } else if(queryParam !== undefined && indexParam !== undefined && endParam !== undefined) {

    await fetch(`http://192.168.10.127:8081/api/search/asop/${queryParam}/${indexParam}`)
    .then((response) => {
      if (!response.ok) {
        throw response.json();
      }

      return response.json();
    })
    .then((data: JsonResponse) => {
        // console.log(data)
        showSearchResults = true;
        searchResults = data;
        nextPageList = Array.from({ length: data.co.length }, () => 2);
        showMoreButtons = data.co.map((group : CountModel) => group.rc >= 100);
        
        let sum = 0;
        (data as JsonResponse).co.forEach((el : CountModel) => {
          sum = sum + el.rc
        });
        totalResults = sum    
    })
    .catch((error) => {
      console.log(error)
    })
  }

  return (
    <>
      <SearchBar />
      <div className="r_devider"></div>
      { !searchResults ?  

      !suggestion ? 
      (<></>) : (
        <div className="r_card">
          <div className="r_card_body">

          <table className="r_center_form"> 
            <tbody>
              <tr className="r_pick_a_letter">
                {queryParam && (queryParam as string).split('').map((letter, i) => (
                  <td key={i}>
                    {!(suggestion as SuggestionResponse).vowelAt?.includes(i) ? 
                    <span>{letter}</span>
                    :
                    <u> {/* onClick={() => handleStressPicker(index)} style={{cursor:"pointer"}} */}
                      <Link scroll={false} prefetch={false} passHref={true}
                        href={`/?${new URLSearchParams({
                          q: queryParam,
                          index: (suggestion as SuggestionResponse).indexes[(suggestion as SuggestionResponse).vowelAt.indexOf(i)],
                          end: (suggestion as SuggestionResponse).end,
                        })}`}
                      >{letter}</Link>
                    </u>
                    }
                  </td>
                ))}
              </tr>
            </tbody>
          </table>
         
            <div className="r_devider">
              <p>Kur dėti kirčio ženklą?</p>
            </div>

          </div>
        </div>
      ) 
      : (

      <div className="r_card">
        <div className="r_card_body">

          <table className="r_center_form"> 
            <tbody>
              <tr className="r_pick_a_letter">
                {searchResults !== undefined && queryParam && queryParam.split('').map((letter, index) => (
                  <td key={index}>
                    {!(searchResults as JsonResponse).va?.includes(index) ? 
                    <span>{letter}</span>
                    :
                    (searchResults as JsonResponse).rv[(searchResults as JsonResponse).va.indexOf(index)] === (searchResults as JsonResponse).in ?
                    <b>{letter}</b>
                    :
                    <u>
                      <Link scroll={false} prefetch={false} passHref={true}
                        href={`/?${new URLSearchParams({
                          q: queryParam as string,
                          index: (searchResults as JsonResponse).rv[(searchResults as JsonResponse).va.indexOf(index)],
                          end: (searchResults as JsonResponse).end,
                          k: index + "",
                        })}`}
                      >{letter}</Link>
                    </u>
                    }
                  </td>
                ))}
              </tr>
            </tbody>
          </table>

          <h6 className="card-subtitle mb-2 text-muted">rasta {totalResults} žodžių</h6>

          {searchResults !== null && (searchResults as JsonResponse).res?.map((syllableGroup, index) => (
            <div key={index}>
              <h2 className="card-title">
                {syllableGroup[0].sy === 1
                ? '1 skiemuo'
                : syllableGroup[0].sy >= 2 && syllableGroup[0].sy <= 9
                ? `${syllableGroup[0].sy} skiemenys`
                : `${syllableGroup[0].sy} skiemenų`}
              </h2>

              <ShowMore key={index + (keyParam * keyParam)}
                params={{
                  syllableGroup: syllableGroup,  
                  rhymeIndex: (searchResults as JsonResponse).in as string, 
                  syllableCount: syllableGroup[0].sy as number,
                  totalWordCount: (searchResults as JsonResponse).co[index]?.rc as number,
                }}
              />
          
            </div>
          ))}
          
        </div>
      </div>
      )}
    </>)

    // <div className="r_devider"></div>
                  
    // { isLoading ? (
    //   <div className="r_loading">
    //     <h1>Kraunasi...</h1>
    //   </div>
    // ) : 

    // searchResults && searchResults.res?.length === 0 ? 

    // suggestion && suggestion.indexes.length === 0 ?
    // (<></>) : (
    //   <div className="r_card">
    //     <div className="r_card_body">

    //     <table className="r_center_form"> 
    //         <tbody>
    //           <tr className="r_pick_a_letter">
    //             {savedInput.split('').map((letter, i) => (
    //               <td key={i}>
    //                 {!suggestion.vowelAt?.includes(i) ? 
    //                 <span>{letter}</span>
    //                 :
    //                 <u> {/* onClick={() => handleStressPicker(index)} style={{cursor:"pointer"}} */}
    //                   <Link 
    //                     href={`/?${new URLSearchParams({
    //                       q: savedInput,
    //                       index: suggestion.indexes[suggestion.vowelAt.indexOf(i)],
    //                       end: suggestion.end,
    //                     })}`}
    //                   >{letter}</Link>
    //                 </u>
    //                 }
    //               </td>
    //             ))}
    //           </tr>
    //         </tbody>
    //       </table>
          
    //       <div className="r_devider">
    //         <p>Kur dėti kirčio ženklą?</p>
    //       </div>

    //     </div>
    //   </div>
    // ) 
    
    // : (

    // <div className="r_card">
    //   <div className="r_card_body">

    //     <table className="r_center_form"> 
    //       <tbody>
    //         <tr className="r_pick_a_letter">
    //           {savedInput.split('').map((letter, index) => (
    //             <td key={index}>
    //               {!searchResults.va?.includes(index) ? 
    //               <span>{letter}</span>
    //               :
    //               searchResults.rv[searchResults.va.indexOf(index)] === searchResults.in ?
    //               <b>{letter}</b>
    //               :
    //               <u>
    //                 <Link 
    //                   href={`/?${new URLSearchParams({
    //                     q: savedInput,
    //                     index: suggestion.indexes[suggestion.vowelAt.indexOf(index)],
    //                     end: suggestion.end,
    //                   })}`}
    //                 >{letter}</Link>
    //               </u>
    //               }
    //             </td>
    //           ))}
    //         </tr>
    //       </tbody>
    //     </table>

    //     <h6 className="card-subtitle mb-2 text-muted">rasta {totalResults} žodžių</h6>

    //     {searchResults.res?.map((syllableGroup, index) => (
    //       <div key={index}>
    //         <h2 className="card-title">
    //           {syllableGroup[0].sy === 1
    //           ? '1 skiemuo'
    //           : syllableGroup[0].sy >= 2 && syllableGroup[0].sy <= 9
    //           ? `${syllableGroup[0].sy} skiemenys`
    //           : `${syllableGroup[0].sy} sliemenų`}
    //         </h2>
            
    //         <div className="r_rhyme_box">
    //           {syllableGroup.map((word, wordIndex) => (
    //             <div key={wordIndex}>{word.wo.slice(0, word.sa - 1)}<b>{word.wo.slice(word.sa - 1, word.sa) + stressSigns[word.st]}</b>{word.wo.slice(word.sa)}</div>
    //           ))}
    //         </div>

    //           <div className="r_devider"> 
    //           {showMoreButtons[index] && (
    //             <u onClick={() => handleShowMoreButton(index)} style={{cursor:"pointer"}}>rodyti daugiau</u>
    //           )}
    //         </div>
    //       </div>
    //     ))}

    //   </div>
    // </div>

    // )}
}
