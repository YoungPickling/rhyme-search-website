package lt.rimuok.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "zodziai", indexes = {
        @Index(name = "zodis_idx", columnList = "zodis")
})
public class Words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zodzio_id")
    private int zodzioId;

    @Column(name = "zodis", nullable = false)
    private String zodis;
}
