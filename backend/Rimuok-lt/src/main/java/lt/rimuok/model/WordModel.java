package lt.rimuok.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordModel {
    @JsonProperty("wo")
    private String word;

    @JsonProperty("sy")
    private int syllable;

    @JsonProperty("sa")
    private int stressAt;

    @JsonProperty("st")
    private int stressType;

    @JsonProperty("ps")
    private int partOfSpeech;
}
