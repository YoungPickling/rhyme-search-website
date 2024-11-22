import { API_BASE_URL } from "@/app/config";
// import { SuggestionResponse, JsonResponse } from "@/app/page";

export default async function getResults(input: string, partOfSpeech: number | undefined, ending: number | undefined) {
  const res = await fetch(`${API_BASE_URL}/${ ending !== undefined ? `end` : `aso` }${ partOfSpeech !== undefined ? `f` : `` }/${input}${ partOfSpeech !== undefined ? `/${partOfSpeech}` : `` }${ ending !== undefined ? `/${ending}` : `` }`);
  // const data: WordModel[] = await response.json();

  if (!res.ok) undefined // throw new Error("failed to fech word")

  return res.json;
}