package lt.rimuok.repository;

import lt.rimuok.model.JoinedTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JunctionTableRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<JoinedTable> customQuery(String search, boolean limit) {
        return jdbcTemplate.query("SELECT zodis AS word, levenshtein(zodis,?) AS distance, kalbos_dalies_id AS kdId, kircio_vieta AS stress_at, kircio_zenklas AS stress_type FROM zodziai_kalbos_dalys_morfologija INNER JOIN zodziai USING(zodzio_id) JOIN kalbos_dalys USING(kalbos_dalies_id) ORDER BY distance " + (limit ? "LIMIT 100" : ""),
                BeanPropertyRowMapper.newInstance(JoinedTable.class), search);
    }

    public List<JoinedTable> customQuery(String search) {
        return customQuery(search, false);
    }
}
