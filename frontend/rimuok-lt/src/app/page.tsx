import Link from "next/link";
import SearchBar from "./components/SearchBar";
import ShowMore from "@/app/components/ShowMore";
import type { Metadata } from "next"
import { SITE_BASE_URL, API_BASE_URL } from "./config"
import Filters from "./components/Filters";

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

export interface SearchPageProps {
  searchParams: {
      q: string | undefined, // query word
      index: string | undefined,
      at: string | undefined,
      end: string | undefined,
      type: string | undefined,
      pfs: number | undefined,
      k: number | undefined,
    }
}

export async function generateMetadata(
  {searchParams}: SearchPageProps
) : Promise<Metadata> {
  let titleName: string;
  let urlName: string;
  if(searchParams.q === undefined) {
    titleName = "Rimuok.lt - išplėstinė rimų paieškos svetainė";
    urlName = `${SITE_BASE_URL}/api/og`;
  } else {
    titleName = `${searchParams.q} | Rimuok.lt rimų paieška`;
    urlName = `${SITE_BASE_URL}/api/og?${new URLSearchParams({q: searchParams.q})}`;
  }

  return {
    title: titleName,
    openGraph: {
      title: titleName,
      images: {
        url: urlName,
        width: 1200,
        height: 630,
        alt: titleName,
      }
    },
    twitter: {
      title: urlName,
      images: {
        url: urlName,
        alt: "Rimuok.lt žodžių paieška",
      }
    },
  };
}

export default async function Home({searchParams}: SearchPageProps) {
  const queryParam = searchParams.q;
  const indexParam = searchParams.index;
  const endParam = searchParams.end === undefined ? "" : searchParams.end;
  const keyParam: number = searchParams.k === undefined ? 0 : searchParams.k;

  const rhymeTypeParam = searchParams.type !== "end" ? "aso" : searchParams.type;
  const partOfSpeechParam = searchParams.pfs === undefined || searchParams.pfs > 13 || searchParams.pfs < 0 ? 0 : searchParams.pfs;

  const uniqueKey = ((keyParam + 1) * 10) + (rhymeTypeParam === "end" ? 100 : 200) + (partOfSpeechParam * 1000);

  let searchResults: JsonResponse | null = null;
  let showSearchResults = false;

  let nextPageList: number[] | null = null;
  let showMoreButtons: boolean[];
  let totalResults: number | null = null;

  let suggestion: SuggestionResponse | null = null;
  let showSuggestion = false;

  if(queryParam !== undefined && (indexParam === undefined || endParam === undefined)) {
    const fetchURL = searchParams.pfs === undefined || searchParams.pfs > 13 || searchParams.pfs < 1 ?
      `${API_BASE_URL}/api/search/${rhymeTypeParam}/${queryParam}` :
      `${API_BASE_URL}/api/search/${rhymeTypeParam}f/${queryParam}/${partOfSpeechParam}` ;

    // console.log(`Initial search ${fetchURL}`)

    await fetch(fetchURL)
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
        showSuggestion = true;
        suggestion = data as SuggestionResponse;
      }      
    })
    .catch((error) => {
      console.log(`error in ${fetchURL}`)
    })
  } else if(queryParam !== undefined && indexParam !== undefined && endParam !== undefined) {
    
    const fetchURL = searchParams.pfs === undefined || searchParams.pfs > 13 || searchParams.pfs < 1 ?
      `${API_BASE_URL}/api/search/${rhymeTypeParam}p/${queryParam}/${indexParam}` :
      `${API_BASE_URL}/api/search/${rhymeTypeParam}fp/${queryParam}/${indexParam}/${partOfSpeechParam}` ;
    
    // console.log(`Broad search ${fetchURL}`)
    
    await fetch(fetchURL)
    .then((response) => {
      if (!response.ok) {
        throw response.json();
      }

      return response.json();
    })
    .then((data: JsonResponse) => {
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
      console.log(`error in ${fetchURL}`)
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
                      <Link scroll={false} 
                        href={`/?${queryParam === undefined ? `` : `q=${encodeURIComponent(queryParam)}` }` + 
                          `&index=${encodeURIComponent((suggestion as SuggestionResponse).indexes[(suggestion as SuggestionResponse).vowelAt.indexOf(i)])}` +
                          `&end=${encodeURIComponent((suggestion as SuggestionResponse).end)}` +
                          `${partOfSpeechParam === 0 ? `` : `&pfs=${encodeURIComponent(partOfSpeechParam)}` }` +
                          `${rhymeTypeParam !== "aso" ? `` : `&type=${encodeURIComponent(rhymeTypeParam)}` }`
                        }
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

      <div className="r_card" key={uniqueKey + new Date().getTime()}>
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
                      <Link scroll={false}
                        href={`/?${new URLSearchParams({
                          q: queryParam as string,
                          index: (searchResults as JsonResponse).rv[(searchResults as JsonResponse).va.indexOf(index)],
                          end: (searchResults as JsonResponse).end,
                          pfs: partOfSpeechParam + "",
                          type: rhymeTypeParam,
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
          
          <Filters 
            searchParams={searchParams}
          />

          
          { totalResults === 0 ? (
            <div className="r_devider mt-10 mb-10">
              <h1>¯\_(ツ)_/¯</h1>
              <h1>Nerasta nei vieno žodžio</h1>
            </div>
          ) : (
            <>
            <h6 key={uniqueKey + new Date().getTime()} className="card-subtitle mb-2 text-muted">rasta {totalResults} žodžių</h6>

              {searchResults !== null && (searchResults as JsonResponse).res?.map((syllableGroup, index) => (
                <div key={index}>
                  <h2 className="card-title">
                    {syllableGroup[0].sy === 1
                    ? '1 skiemuo'
                    : syllableGroup[0].sy >= 2 && syllableGroup[0].sy <= 9
                    ? `${syllableGroup[0].sy} skiemenys`
                    : `${syllableGroup[0].sy} skiemenų`}
                  </h2>

                  <ShowMore key={index + uniqueKey + new Date().getTime()}
                    params={{
                      syllableGroup: syllableGroup,  
                      rhymeIndex: (searchResults as JsonResponse).in as string, 
                      syllableCount: syllableGroup[0].sy as number,
                      totalWordCount: (searchResults as JsonResponse).co[index]?.rc as number,
                      rhymeType: rhymeTypeParam,
                      ending: endParam,
                      pfs: searchParams.pfs,
                    }}
                  />

                </div>
              ))}
            </>
          )}
          
        </div>
      </div>
      )}
    </>
  )
}
