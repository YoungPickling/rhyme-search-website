import { SuggestionResponse, JsonResponse } from "@/app/page";

export default async function getResults(input: string, partOfSpeech: number | undefined, ending: number | undefined) {
  const res = await fetch(`http://192.168.10.127:8081/api/search/${ ending !== undefined ? `end` : `aso` }${ partOfSpeech !== undefined ? `f` : `` }/${input}${ partOfSpeech !== undefined ? `/${partOfSpeech}` : `` }${ ending !== undefined ? `/${ending}` : `` }`);
  // const data: WordModel[] = await response.json();

  if (!res.ok) undefined // throw new Error("failed to fech word")

  return res.json;
}