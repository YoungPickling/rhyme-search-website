package lt.rimuok.service;


import lt.rimuok.exceptions.EmptyRhymeIndexException;
import lt.rimuok.model.AsonanceSearchModel;
import lt.rimuok.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    public List<AsonanceSearchModel> searchAsonance(String word) {
        String rhymeIndex = searchRepository.tryGetRhymeIndex(word);
        if (rhymeIndex == null) {
            throw new EmptyRhymeIndexException("Empty Order cannot be saved, add something");
        }
        return searchRepository.searchAsonance(rhymeIndex);
    }

    public List<AsonanceSearchModel> searchAsonanceWithAssonance(String word) {
        String rhymeIndex = searchRepository.tryGetRhymeIndex(word);
        if (rhymeIndex == null) {
            throw new EmptyRhymeIndexException("Empty Order cannot be saved, add something");
        }
        return searchRepository.searchAsonance(rhymeIndex);
    }
}
