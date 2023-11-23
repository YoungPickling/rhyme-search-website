package lt.rimuok.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "morfologija")
public class Morphology {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "morfologijos_id")
    private int morfologijosId;

    @Column(name = "gimine")
    private String gimine;

    @Column(name = "lynksnis")
    private String lynksnis;

    @Column(name = "skaicius")
    private String skaicius;

    @Column(name = "laipsnys")
    private String laipsnys;

    @Column(name = "ivardziotine_forma")
    private String ivardziotineForma;

    @Column(name = "laikas")
    private String laikas;

    @Column(name = "asmuo")
    private String asmuo;

    @Column(name = "nuosaka")
    private String nuosaka;

    @Column(name = "dalyvio_rusis")
    private String dalyvioRusis;

    @Column(name = "ivardzio_tipas")
    private String ivardzioTipas;

    @Column(name = "skaitvardzio_tipas")
    private String skaitvardzioTipas;

    @Column(name = "forma")
    private String forma;

    @Column(name = "trumpa_zodzio_forma")
    private boolean trumpaZodzioForma;
}
