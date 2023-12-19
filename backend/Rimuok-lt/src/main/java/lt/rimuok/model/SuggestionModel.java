package lt.rimuok.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.rimuok.Utils;

import java.util.List;

@Data
public class SuggestionModel {
    private int[] vowelAt;
    private List<String> indexes;
    private String end;

    public SuggestionModel(final String word) {
        this.vowelAt = Utils.getVowelIndexes(word);
        this.indexes = Utils.getAllRhymeIndexes(Utils.extractVowelGroups(word));
        this.end = Utils.getEnding(word);
    }
}
