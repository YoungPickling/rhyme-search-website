import {useState} from "react"

type ShowMoreForm = {
  index: string,
  syllables: string,
  page: number,
  wordsLeft: number,
}

export default function ShowMore({index, syllables, page, wordsLeft}: ShowMoreForm)  {
  const [isLoading, setIsLoading] = useState(false);

  const handleSearch = (e:any) => {
    e.preventDefault();

    setIsLoading(true);

    fetch(`http://localhost:8081/api/search/aso/${index}/${syllables}/${page}`)
      .then((response) => response.json())
      .then((data) => {       
        // setRhymeIndex(data.in)
        // setSearchResults(data);
      })
      .catch((error) => {
        console.error(error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }

  return (
    <></>
  )
}