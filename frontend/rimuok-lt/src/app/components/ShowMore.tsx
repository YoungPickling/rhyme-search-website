"use client"
import React, { useState } from 'react'
import { WordModel } from '@/app/page';
import { API_BASE_URL } from '../config';

const stressSigns = ["","̀","́","̃"];

export default function ShowMore(
  { params }: { 
    params: { 
      syllableGroup: WordModel[], 
      rhymeIndex: string, 
      syllableCount: number, 
      totalWordCount: number, 
      rhymeType: string, 
      ending: string,
      pfs: number | undefined
    }
  }
  ) {
  const [list, setList] = useState<WordModel[]>(params.syllableGroup);
  
  const [wordCount, setWordCount] = useState<number>(params.totalWordCount);
  const [showButton, setShowButton] = useState<boolean>(list.length == 100);
  const [loading, setLoading] = useState<boolean>(false);
  const [nextPage, setNextPage] = useState<number>(2);

  const handleShowMoreButton = async () => {
    setLoading(true)
    try {
      const fetchURL = params.pfs === undefined || params.pfs > 13 || params.pfs < 1 ?
      // params.ending === "aso" ?
      // `${API_BASE_URL}/api/search/aso/${params.rhymeIndex}/${params.syllableCount}/${nextPage}` :
      // `${API_BASE_URL}/api/search/end/${params.rhymeIndex}/${params.ending === `` ? `` : `${params.ending}` }/${params.syllableCount}/${nextPage}`
      // : 
      // params.ending === "aso" ?
      // `${API_BASE_URL}/api/search/asof/${params.rhymeIndex}/${params.pfs}/${params.syllableCount}/${nextPage}` :
      // `${API_BASE_URL}/api/search/endf/${params.rhymeIndex}/${params.pfs}/${params.ending === `` ? `` : `${params.ending}` }/${params.syllableCount}/${nextPage}`
      params.rhymeType === "aso" ?
      `${API_BASE_URL}/api/search/aso/${params.rhymeIndex}/${params.syllableCount}/${nextPage}` :
      `${API_BASE_URL}/api/search/end/${params.rhymeIndex}/${params.ending === `` ? `` : `${params.ending}` }/${params.syllableCount}/${nextPage}`
      : 
      params.rhymeType === "aso" ?
      `${API_BASE_URL}/api/search/asof/${params.rhymeIndex}/${params.pfs}/${params.syllableCount}/${nextPage}` :
      `${API_BASE_URL}/api/search/endf/${params.rhymeIndex}/${params.pfs}/${params.ending === `` ? `` : `${params.ending}` }/${params.syllableCount}/${nextPage}`

      const response = await fetch(fetchURL);
      const data: WordModel[] = await response.json();

      if (data.length < 100 ) {
        setShowButton(false)
      } else {
        setShowButton(true); // searchResults.co[innerIndex].rc
      }

      setList((prevList) => {
        let newList: WordModel[] = [ ...prevList];  
        newList = newList.concat(data as WordModel[]);
        return newList;
      });

      setNextPage(nextPage + 1);

      setWordCount(wordCount - data.length)

    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false)
    }
  };

  return (
    <>
    <div className="r_rhyme_box">
      {list.map((word, wordIndex) => (
        <div key={wordIndex}>{word.wo.slice(0, word.sa - 1)}<b>{word.wo.slice(word.sa - 1, word.sa) + stressSigns[word.st]}</b>{word.wo.slice(word.sa)}</div>
      ))}
    </div>
    
    <div className="r_devider"> 
    {/* <p>{list.length}</p>
    <p>{wordCount}</p> */}
      {loading ?
      <b>Kraunasi...</b> 
      :
      showButton && <button onClick={() => handleShowMoreButton()} className="r_show_more_button">rodyti daugiau</button> 
      }
    </div>

    {/* <div className="r_devider"> 
      {showButton && (
        <u onClick={() => handleShowMoreButton()} style={{cursor:"pointer"}}>rodyti daugiau</u> 
      )}
    </div> */}
    </>
  )
}