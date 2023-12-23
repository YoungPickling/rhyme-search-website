package lt.rimuok.service;

import lt.rimuok.Utils;
import lt.rimuok.exceptions.EmptyRhymeIndexException;
import lt.rimuok.exceptions.InputValidationException;
import lt.rimuok.model.InitialInfoModel;
import lt.rimuok.model.SyllableCountModel;
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

    public List<SyllableCountModel> resultCount(final String rhymeIndex, final String ending) {
        return searchRepository.syllableCountTable(rhymeIndex, ending);
    }

    public InitialInfoModel searchAssonance(final String word) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        String rhymeIndex;
        List<SyllableCountModel> resultCount;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
            resultCount = searchRepository.syllableCountTable(rhymeIndex, null);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found", word);
        }

        rhymeIndex = rhymeIndex.substring(Utils.findLastCapital(rhymeIndex));
        List<WordModel> results = searchRepository.searchAssonance(rhymeIndex);
        return new InitialInfoModel(rhymeIndex,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                Utils.getEnding(word),
                resultCount,
                Utils.groupBySyllable(results));
    }

    public InitialInfoModel searchAssonanceByIndex(final String word, final String index) {
        if (!index.matches(vowelValidationRegex) || !word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        List<SyllableCountModel> resultCount;
        try {
            resultCount = searchRepository.syllableCountTable(index, null);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no results found", word);
        }

        List<WordModel> results = searchRepository.searchAssonance(index);
        return new InitialInfoModel(index,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                Utils.getEnding(word),
                resultCount,
                Utils.groupBySyllable(results));
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
    public InitialInfoModel filteredAssonance(final String word, final int partOfSpeech) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 12)
            throw new InputValidationException("invalid number");

        String rhymeIndex;
        List<SyllableCountModel> resultCount;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
            resultCount = searchRepository.syllableCountTable(rhymeIndex, null);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found", word);
        }

        rhymeIndex = rhymeIndex.substring(Utils.findLastCapital(rhymeIndex));

        List<WordModel> results = searchRepository.filteredAssonance(rhymeIndex, partOfSpeech);
        return new InitialInfoModel(rhymeIndex,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                Utils.getEnding(word),
                resultCount,
                Utils.groupBySyllable(results));
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

    public InitialInfoModel searchEnding(final String word) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        String rhymeIndex;
        List<SyllableCountModel> resultCount;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
            resultCount = searchRepository.syllableCountTable(rhymeIndex, null);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found", word);
        }

        String ending = Utils.getEnding(word);
        List<WordModel> results = searchRepository.searchEnding(rhymeIndex, ending);
        return new InitialInfoModel(rhymeIndex,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                Utils.getEnding(word),
                resultCount,
                Utils.groupBySyllable(results));
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

    public InitialInfoModel filteredEnding(final String word, final int partOfSpeech) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 12)
            throw new InputValidationException("invalid number");

        String rhymeIndex;
        List<SyllableCountModel> resultCount;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
            resultCount = searchRepository.syllableCountTable(rhymeIndex, null);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found", word);
        }

        String ending = Utils.getEnding(word);
        List<WordModel> results = searchRepository.filteredEnding(rhymeIndex, partOfSpeech, ending);
        return new InitialInfoModel(rhymeIndex,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                Utils.getEnding(word),
                resultCount,
                Utils.groupBySyllable(results));
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
}
