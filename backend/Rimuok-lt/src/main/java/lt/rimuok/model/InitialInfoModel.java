package lt.rimuok.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitialInfoModel {
    @JsonProperty("in")
    private String rhymeIndex;

    @JsonProperty("va")
    private int[] vowelAt;

    @JsonProperty("rv")
    private List<String> indexes;

    @JsonProperty("end")
    private String ending;

    @JsonProperty("co")
    private List<SyllableCountModel> resultCount;

    @JsonProperty("res")
    private List<List<WordModel>> result;
}
