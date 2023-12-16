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
public class SyllableCountModel {
    @JsonProperty("sc")
    private int syllablesCount;

    @JsonProperty("rc")
    private int rowCount;
}
