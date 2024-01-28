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

    private final String validationRegex = "^[a-pr-vyzA-PR-VYZąčęėįšųūžĄČĘĖĮŠŲŪŽ]*$";
    private final String vowelValidationRegex = "^[aeiyouąęėįųūAEIYOUĄĘĖĮŲŪ]*$";

//    public List<SyllableCountModel> resultCount(final String rhymeIndex, final String ending, final int pfs) {
//        return searchRepository.syllableCountTable(rhymeIndex, ending, pfs);
//    }

    public InitialInfoModel searchAssonance(final String word) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        String rhymeIndex;
        List<SyllableCountModel> resultCount;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
            resultCount = searchRepository.syllableCountTable(rhymeIndex, null, 0);
        } catch(EmptyResultDataAccessException e) {
                throw new EmptyRhymeIndexException("no index found", word);
        }

        rhymeIndex = rhymeIndex.substring(Utils.findLastCapital(rhymeIndex));
        List<WordModel> results = searchRepository.searchAssonance(rhymeIndex);
//        List<List<WordModel>> words = Utils.groupBySyllable(results);
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
            resultCount = searchRepository.syllableCountTable(index, null, 0);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no results found", word);
        }

        List<WordModel> results = searchRepository.searchAssonance(index);
//        List<List<WordModel>> words = Utils.groupBySyllable(results);
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
            resultCount = searchRepository.syllableCountTable(rhymeIndex, null, partOfSpeech);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found", word);
        }

        rhymeIndex = rhymeIndex.substring(Utils.findLastCapital(rhymeIndex));

        List<WordModel> results = searchRepository.filteredAssonance(rhymeIndex, partOfSpeech);
//        List<List<WordModel>> words = Utils.groupBySyllable(results);
        return new InitialInfoModel(rhymeIndex,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                Utils.getEnding(word),
                resultCount,
                Utils.groupBySyllable(results));
    }

    public InitialInfoModel filteredAssonanceByIndex(final String word, final String index, final int partOfSpeech) {
        if (!index.matches(vowelValidationRegex) || !word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 13)
            throw new InputValidationException("invalid number");

        List<SyllableCountModel> resultCount;
        try {
            resultCount = searchRepository.syllableCountTable(index, null, partOfSpeech);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no results found", word);
        }

        List<WordModel> results = searchRepository.filteredAssonance(index, partOfSpeech);
//        List<List<WordModel>> words = Utils.groupBySyllable(results);
        return new InitialInfoModel(index,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                Utils.getEnding(word),
                resultCount,
                Utils.groupBySyllable(results));
    }

    public List<WordModel> filteredAssonancePage(final String index, final int partOfSpeech, final int syllableCount, final int page) {
        if (!index.matches(vowelValidationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 13)
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

        String ending = Utils.getEnding(word);

        String rhymeIndex;
        List<SyllableCountModel> resultCount;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
            resultCount = searchRepository.syllableCountTable(rhymeIndex, ending, 0);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found", word);
        }


        List<WordModel> results = searchRepository.searchEnding(rhymeIndex, ending);
//        List<List<WordModel>> words = Utils.groupBySyllable(results);
        return new InitialInfoModel(rhymeIndex,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                ending,
                resultCount,
                Utils.groupBySyllable(results));
    }

    public InitialInfoModel searchEndingByIndex(final String word, final String index) {
        if (!index.matches(vowelValidationRegex) || !word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        String ending = Utils.getEnding(word);

        List<SyllableCountModel> resultCount;
        try {
            resultCount = searchRepository.syllableCountTable(index, ending, 0);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no results found", word);
        }

        List<WordModel> results = searchRepository.searchEnding(index, ending);
//        List<List<WordModel>> words = Utils.groupBySyllable(results);
        return new InitialInfoModel(index,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                ending,
                resultCount,
                Utils.groupBySyllable(results));
    }

    public List<WordModel> searchEndingPage(final String index, final String ending, final int syllableCount, final int page) {
        if (!index.matches(vowelValidationRegex) || (!ending.matches(validationRegex) && !ending.isEmpty()))
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

        if (partOfSpeech < 1 || partOfSpeech > 13)
            throw new InputValidationException("invalid number");

        String ending = Utils.getEnding(word);
        String rhymeIndex;
        List<SyllableCountModel> resultCount;
        try {
            rhymeIndex = searchRepository.getRhymeIndex(word);
            resultCount = searchRepository.syllableCountTable(rhymeIndex, ending, partOfSpeech);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no index found", word);
        }

        List<WordModel> results = searchRepository.filteredEnding(rhymeIndex, partOfSpeech, ending);
//        List<List<WordModel>> words = Utils.groupBySyllable(results);
        return new InitialInfoModel(rhymeIndex,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                ending,
                resultCount,
                Utils.groupBySyllable(results));
    }

    public InitialInfoModel filteredEndingByIndex(final String word, final String index, final int partOfSpeech) {
        if (!index.matches(vowelValidationRegex) || !word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 13)
            throw new InputValidationException("invalid number");

        String ending = Utils.getEnding(word);

        List<SyllableCountModel> resultCount;
        try {
            resultCount = searchRepository.syllableCountTable(index, ending, partOfSpeech);
        } catch(EmptyResultDataAccessException e) {
            throw new EmptyRhymeIndexException("no results found", word);
        }

        List<WordModel> results = searchRepository.filteredEnding(index, partOfSpeech, ending);
//        List<List<WordModel>> words = Utils.groupBySyllable(results);
        return new InitialInfoModel(index,
                Utils.getVowelIndexes(word),
                Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word)),
                ending,
                resultCount,
                Utils.groupBySyllable(results));
    }

    public List<WordModel> filteredEndingPage(final String index, final int partOfSpeech, final String ending, final int syllableCount, final int page) {
        if (!index.matches(vowelValidationRegex) || (!ending.matches(validationRegex) && !ending.isEmpty()) )
            throw new InputValidationException("invalid characters found");

        if (partOfSpeech < 1 || partOfSpeech > 13)
            throw new InputValidationException("invalid number");

        if (page < 1 || syllableCount < 1)
            throw new InputValidationException("invalid number");

        int to = page * 100;
        int from = to - 100;

        return searchRepository.filteredEndingPage(index, partOfSpeech, ending, syllableCount, from, to);
    }
}
