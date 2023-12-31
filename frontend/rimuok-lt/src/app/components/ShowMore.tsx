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
  const [showButton, setShowButton] = useState<boolean>(params.totalWordCount - 100 >= 100);
  const [nextPage, setNextPage] = useState<number>(2);

  const handleShowMoreButton = async () => {
    try {
      const fetchURL = params.pfs === undefined || params.pfs > 13 || params.pfs < 1 ?
      params.ending === "aso" ?
      `${API_BASE_URL}/api/search/aso/${params.rhymeIndex}/${params.syllableCount}/${nextPage}` :
      `${API_BASE_URL}/api/search/end/${params.rhymeIndex}/${params.ending === `` ? `` : `${params.ending}` }/${params.syllableCount}/${nextPage}`
      : 
      params.ending === "aso" ?
      `${API_BASE_URL}/api/search/asof/${params.rhymeIndex}/${params.pfs}/${params.syllableCount}/${nextPage}` :
      `${API_BASE_URL}/api/search/endf/${params.rhymeIndex}/${params.pfs}/${params.ending === `` ? `` : `${params.ending}` }/${params.syllableCount}/${nextPage}`

      // console.log("next page" + fetchURL)

      const response = await fetch(fetchURL);
      const data: WordModel[] = await response.json();
  
      setShowButton(wordCount - 100 >= 100); // searchResults.co[innerIndex].rc

      setList((prevList) => {
        let newList: WordModel[] = [ ...prevList];  
        newList = newList.concat(data as WordModel[]);
        return newList;
      });

      setNextPage(nextPage + 1);

      setWordCount(wordCount - 100)

    } catch (error) {
      console.error(error);
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
      {showButton && (
        <u onClick={() => handleShowMoreButton()} style={{cursor:"pointer"}}>rodyti daugiau</u> 
      )}
    </div>
    </>
  )
}