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

    @JsonProperty("co")
    private List<SyllableCountModel> resultCount;

    @JsonProperty("res")
    private List<List<WordModel>> result;
}
