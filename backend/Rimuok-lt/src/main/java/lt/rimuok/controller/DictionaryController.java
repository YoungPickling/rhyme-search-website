package lt.rimuok.controller;

import lombok.RequiredArgsConstructor;
import lt.rimuok.model.InitialInfoModel;
import lt.rimuok.model.WordModel;
import lt.rimuok.repository.SearchRepository;
import lt.rimuok.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>&emsp;Search controller class, made for public search support and paged data reading</p><br/>
 * <i>&emsp;Contains these endpoints:</i>
 * <ul style="list-style-type: square;">
 *   <li>{@link #getAssonanceRhyme      /api/search/aso/{word}                                      }</li>
 *   <li>{@link #getAssonancePage       /api/search/aso/{index}/{syllables}/{page}                  }</li>
 *   <li>{@link #filterAssonanceRhyme   /api/search/asof/{word}/{pfs}                               }</li>
 *   <li>{@link #filterAssonancePage    /api/search/asof/{index}/{pfs}/{syllables}/{page}           }</li>
 *   <li>{@link #getEndingRhyme         /api/search/end/{word}                                      }</li>
 *   <li>{@link #getEndingPage          /api/search/end/{index}/{ending}/{syllables}/{page}         }</li>
 *   <li>{@link #filterEndingRhyme      /api/search/endf/{word}/{pfs}                               }</li>
 *   <li>{@link #filterEndingPage       /api/search/endf/{index}/{pfs}/{ending}/{syllables}/{page}  }</li>
 * </ul>
 *
 * @version 1.0, 22 Nov 2023
 * @since 1.0, 22 Nov 2023
 * @author Maksim Pavlenko
 */

@CrossOrigin(origins = "http://192.168.10.127:3000", maxAge = 3600)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DictionaryController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/search/aso/{word}")
    public InitialInfoModel getAssonanceRhyme(@PathVariable String word) {
        return searchService.searchAssonance(word.toLowerCase());
    }

    @GetMapping("/search/aso/{index}/{syllables}/{page}")
    public List<WordModel> getAssonancePage(@PathVariable String index, @PathVariable int syllables, @PathVariable int page) {
        return searchService.searchAssonancePage(index, syllables, page);
    }

    @GetMapping("/search/asof/{word}/{pfs}") // pfs - part of speech
    public InitialInfoModel filterAssonanceRhyme(@PathVariable String word, @PathVariable int pfs) {
        return searchService.filteredAssonance(word.toLowerCase(), pfs);
    }

    @GetMapping("/search/asof/{index}/{pfs}/{syllables}/{page}")
    public List<WordModel> filterAssonancePage(@PathVariable String index, @PathVariable int pfs, @PathVariable int syllables, @PathVariable int page) {
        return searchService.filteredAssonancePage(index, pfs, syllables, page);
    }

    @GetMapping("/search/end/{word}")
    public InitialInfoModel getEndingRhyme(@PathVariable String word) {
        return searchService.searchEnding(word.toLowerCase());
    }

    @GetMapping("/search/end/{index}/{ending}/{syllables}/{page}")
    public List<WordModel> getEndingPage(@PathVariable String index, @PathVariable String ending, @PathVariable int syllables, @PathVariable int page) {
        return searchService.searchEndingPage(index, ending, syllables, page);
    }

    @GetMapping("/search/endf/{word}/{pfs}")
    public InitialInfoModel filterEndingRhyme(@PathVariable String word, @PathVariable int pfs) {
        return searchService.filteredEnding(word.toLowerCase(), pfs);
    }

    @GetMapping("/search/endf/{index}/{pfs}/{ending}/{syllables}/{page}")
    public List<WordModel> filterEndingPage(@PathVariable String index, @PathVariable int pfs, @PathVariable String ending, @PathVariable int syllables, @PathVariable int page) {
        return searchService.filteredEndingPage(index, pfs, ending, syllables, page);
    }
}
