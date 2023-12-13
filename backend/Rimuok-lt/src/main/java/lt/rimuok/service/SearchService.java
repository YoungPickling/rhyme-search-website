package lt.rimuok.service;

import lt.rimuok.Utils;
import lt.rimuok.exceptions.EmptyRhymeIndexException;
import lt.rimuok.exceptions.InputValidationException;
import lt.rimuok.model.WordModel;
import lt.rimuok.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    private final String validationRegex = "^[a-pr-vyza-pr-vyzA-PR-VYZąčęėįšųūžĄČĘĖĮŠŲŪŽ]*$";
    private final String vowelValidationRegex = "^[aeiyouąęėįųūAEIYOUĄĘĖĮŲŪ]*$";

    public List<WordModel> searchAssonance(final String word) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        String rhymeIndex;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found");
        }

        rhymeIndex = rhymeIndex.substring(Utils.findLastCapital(rhymeIndex));

        return searchRepository.searchAssonance(rhymeIndex);
    }

    public List<WordModel> searchAssonancePage(final String index, final int syllableCount, final int page) {
        if (!index.matches(vowelValidationRegex))
            throw new InputValidationException("invalid characters found");

        if (page < 1)
            throw new InputValidationException("invalid page number");

        int to = page * 100;
        int from = to - 100;

        return searchRepository.searchAssonancePage(index, syllableCount, from, to);
    }
    public List<WordModel> filteredAssonance(final String word, final int partOfSpeech) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 12)
            throw new InputValidationException("invalid number");

        String rhymeIndex;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found");
        }

        rhymeIndex = rhymeIndex.substring(Utils.findLastCapital(rhymeIndex));

        return searchRepository.filteredAssonance(rhymeIndex, partOfSpeech);
    }

    public List<WordModel> filteredAssonancePage(final String index, final int partOfSpeech, final int syllableCount, final int page) {
        if (!index.matches(vowelValidationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 12)
            throw new InputValidationException("invalid number");

        if (page < 1)
            throw new InputValidationException("invalid page number");

        int to = page * 100;
        int from = to - 100;

        return searchRepository.filteredAssonancePage(index, partOfSpeech, syllableCount, from, to);
    }

    public List<WordModel> searchEnding(final String word) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        String rhymeIndex;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found");
        }

        String ending = Utils.getEnding(word);
        return searchRepository.searchEnding(rhymeIndex, ending);
    }

    public List<WordModel> searchEndingPage(final String index, final String ending, final int syllableCount, final int page) {
        if (!index.matches(vowelValidationRegex) || !ending.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        if (page < 1 || syllableCount < 1)
            throw new InputValidationException("invalid number");

        int to = page * 100;
        int from = to - 100;

        return searchRepository.searchEndingPage(index, ending, syllableCount, from, to);
    }

    public List<WordModel> filteredEnding(final String word, final int partOfSpeech) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 12)
            throw new InputValidationException("invalid number");

        String rhymeIndex;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found");
        }

        String ending = Utils.getEnding(word);
        return searchRepository.filteredEnding(rhymeIndex, partOfSpeech, ending);
    }

    public List<WordModel> filteredEndingPage(final String index, final int partOfSpeech, final String ending, final int syllableCount, final int page) {
        if (!index.matches(vowelValidationRegex) || !ending.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 12)
            throw new InputValidationException("invalid number");

        if (page < 1 || syllableCount < 1)
            throw new InputValidationException("invalid number");

        int to = page * 100;
        int from = to - 100;

        return searchRepository.filteredEndingPage(index, partOfSpeech, ending, syllableCount, from, to);
    }
    // These methods below are for those cases when the initial word searched word wasn't in the dictionary:
//    public List<AssonanceSearchModel> searchAssonanceByIndex(final String rhymeIndex) {
//        if (!rhymeIndex.matches(validationRegex))
//            throw new InputValidationException("invalid characters found");
//
//        return searchRepository.searchAssonance(rhymeIndex);
//    }

//    public List<AssonanceSearchModel> searchAssonanceWithEndingByIndex(final String rhymeIndex, final String ending) {
//        if (!rhymeIndex.matches(validationRegex) || !ending.matches(validationRegex))
//            throw new InputValidationException("invalid characters found");
//
//        return searchRepository.searchEnding(rhymeIndex, ending.length(), ending);
//    }
}
