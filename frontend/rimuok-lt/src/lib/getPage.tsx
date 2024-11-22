import { API_BASE_URL } from "@/app/config";
// import { SuggestionResponse, JsonResponse } from "@/app/page";

export default async function getPage(index: string, syllableCount: string, pageNumber: number, partOfSpeech: number | undefined, ending: number | undefined) {
  const res = await fetch(`${API_BASE_URL}/api/search/${ ending !== undefined ? `end` : `aso` }${ partOfSpeech !== undefined ? `f` : `` }/${index}${ partOfSpeech !== undefined ? `/${partOfSpeech}` : `` }${ ending !== undefined ? `/${ending}` : `` }/${syllableCount}/${pageNumber}`);
  // const data: WordModel[] = await response.json();

  if (!res.ok) undefined // throw new Error("failed to fech next page")

  return res.json;
}