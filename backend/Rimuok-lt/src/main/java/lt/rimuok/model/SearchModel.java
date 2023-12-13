package lt.rimuok.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgresql.jdbc.PgArray;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchModel {
    private String word; // word
    private int distance; // distance
    private int syllables; // syllables
    private int kdId; // kalbos dalies Id
    private int[][] stress;
//    private Integer stress_at; // stress_at
//    private Integer stress_type; // stress_type
}
