package lt.rimuok.service;

import lt.rimuok.Utils;
import lt.rimuok.exceptions.EmptyRhymeIndexException;
import lt.rimuok.exceptions.InputValidationException;
import lt.rimuok.model.AssonanceSearchModel;
import lt.rimuok.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    private final String validationRegex = "^[a-pr-vyza-pr-vyzA-PR-VYZąčęėįšųūžĄČĘĖĮŠŲŪŽ]*$";

    public List<AssonanceSearchModel> searchAssonance(final String word) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        String rhymeIndex = searchRepository.getRhymeIndex(word);
        if (rhymeIndex == null)
            throw new EmptyRhymeIndexException("no index found");

        return searchRepository.searchAssonance(rhymeIndex);
    }

    public List<AssonanceSearchModel> searchAssonanceWithEnding(final String word) {
        if (!word.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        String rhymeIndex = searchRepository.getRhymeIndex(word);
        if (rhymeIndex == null) {
            throw new EmptyRhymeIndexException("no index found");
        }

        // TODO fix Assonance search with ending
        String ending = Utils.getEnding(word);
        return searchRepository.searchAssonanceWithEnding(rhymeIndex, ending.length(), ending);
    }

    // These methods below are for those cases when the initial word searched word wasn't in the dictionary:
    public List<AssonanceSearchModel> searchAssonanceByIndex(final String rhymeIndex) {
        if (!rhymeIndex.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        return searchRepository.searchAssonance(rhymeIndex);
    }

    public List<AssonanceSearchModel> searchAssonanceWithEndingByIndex(final String rhymeIndex, final String ending) {
        if (!rhymeIndex.matches(validationRegex) || !ending.matches(validationRegex))
            throw new InputValidationException("invalid characters found");

        return searchRepository.searchAssonanceWithEnding(rhymeIndex, ending.length(), ending);
    }
}
