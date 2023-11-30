package lt.rimuok.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinedTable {
    private String word;
    private int distance;
    private int kdId;
    private Integer stress_at;
    private Integer stress_type;
}
