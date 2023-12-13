package lt.rimuok.controller;

import lombok.RequiredArgsConstructor;
import lt.rimuok.model.SearchModel;
import lt.rimuok.repository.SearchRepository;
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

    private final String validationRegex = "^[a-pr-vyza-pr-vyzA-PR-VYZ]*$"; // "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž0-9_-]*$"

//    @GetMapping("/search/{word}")
//    public List<SearchModel> getRhyme(@PathVariable String word) { //@RequestParam(name = "search") String search
//        List<SearchModel> response = searchRepository.searchQuery(word, true);
//        return response;
//    }

    @GetMapping("/search/{word}")
    public List<SearchModel> getEndingRhyme(@PathVariable String word) {
        List<SearchModel> response = searchRepository.searchQuery(word, true);
        return response;
    }

}
