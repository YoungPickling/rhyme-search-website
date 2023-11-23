package lt.rimuok.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "zodziai_kalbos_dalys_morfologija")
public class JunctionTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "zodzio_id", nullable = false)
    private Words zodziai;

    @ManyToOne
    @JoinColumn(name = "kalbos_dalies_id", nullable = false)
    private PartOfSpeech kalbosDalys;

    @ManyToOne
    @JoinColumn(name = "morfologijos_id", nullable = false)
    private Morphology morfologija;

    @Column(name = "kircio_vieta")
    private Short kircioVieta;

    @Column(name = "kircio_zenklas")
    private Short kircioZenklas;
}