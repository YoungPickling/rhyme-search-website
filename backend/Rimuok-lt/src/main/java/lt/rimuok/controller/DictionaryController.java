package lt.rimuok.controller;

import lombok.RequiredArgsConstructor;
import lt.rimuok.Utils;
import lt.rimuok.model.AssonanceSearchModel;
import lt.rimuok.repository.SearchRepository;
import lt.rimuok.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DictionaryController {
    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private SearchService searchService;

     // "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž0-9_-]*$"

//    @GetMapping("/search/{word}")
//    public List<SearchModel> getRhyme(@PathVariable String word) { //@RequestParam(name = "search") String search
//        List<SearchModel> response = searchRepository.searchQuery(word, true);
//        return response;
//    }

    @GetMapping("/search/aso/{word}")
    public List<AssonanceSearchModel> getAssonanceRhyme(@PathVariable String word) {
        return searchService.searchAssonance(word);
    }

    @GetMapping("/search/end/{word}")
    public List<AssonanceSearchModel> getEndingRhyme(@PathVariable String word) {
        return searchService.searchAssonanceWithEnding(word);
    }

}
