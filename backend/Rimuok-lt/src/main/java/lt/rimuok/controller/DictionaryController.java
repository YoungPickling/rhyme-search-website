package lt.rimuok.controller;

import lombok.RequiredArgsConstructor;
import lt.rimuok.model.InitialInfoModel;
import lt.rimuok.model.WordModel;
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
    private SearchService searchService;

    @GetMapping("/search/aso/{word}")
    public InitialInfoModel getAssonanceRhyme(@PathVariable String word) {
        return searchService.searchAssonance(word);
    }

    @GetMapping("/search/aso/{index}/{syllables}/{page}")
    public List<WordModel> getAssonancePage(@PathVariable String index, @PathVariable int syllables, @PathVariable int page) {
        return searchService.searchAssonancePage(index, syllables, page);
    }

    @GetMapping("/search/asof/{word}/{pfs}") // pfs - part of speech
    public InitialInfoModel filterAssonanceRhyme(@PathVariable String word, @PathVariable int pfs) {
        return searchService.filteredAssonance(word, pfs);
    }

    @GetMapping("/search/asof/{index}/{pfs}/{syllables}/{page}")
    public List<WordModel> filterAssonancePage(@PathVariable String index, @PathVariable int pfs, @PathVariable int syllables, @PathVariable int page) {
        return searchService.filteredAssonancePage(index, pfs, syllables, page);
    }

    @GetMapping("/search/end/{word}")
    public InitialInfoModel getEndingRhyme(@PathVariable String word) {
        return searchService.searchEnding(word);
    }

    @GetMapping("/search/end/{index}/{ending}/{syllables}/{page}")
    public List<WordModel> getEndingPage(@PathVariable String index, @PathVariable String ending, @PathVariable int syllables, @PathVariable int page) {
        return searchService.searchEndingPage(index, ending, syllables, page);
    }

    @GetMapping("/search/endf/{word}/{pfs}")
    public InitialInfoModel filterEndingRhyme(@PathVariable String word, @PathVariable int pfs) {
        return searchService.filteredEnding(word, pfs);
    }

    @GetMapping("/search/endf/{index}/{pfs}/{ending}/{syllables}/{page}")
    public List<WordModel> filterEndingPage(@PathVariable String index, @PathVariable int pfs, @PathVariable String ending, @PathVariable int syllables, @PathVariable int page) {
        return searchService.filteredEndingPage(index, pfs, ending, syllables, page);
    }
}
