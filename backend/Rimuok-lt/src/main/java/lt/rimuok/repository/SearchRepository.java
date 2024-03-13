package lt.rimuok.repository;

import lt.rimuok.mapper.WordModelMapper;
import lt.rimuok.model.WordModel;
import lt.rimuok.model.SyllableCountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public List<SearchModel> searchQuery(String search, boolean limit) {
////        return jdbcTemplate.query("SELECT zodis AS word, levenshtein(zodis,?) AS distance, skiemenu_k AS syllables, kalbos_dalies_id AS kdId, kircio_vieta AS stress_at, kircio_zenklas AS stress_type FROM zodziai_kalbos_dalys_morfologija INNER JOIN zodziai USING(zodzio_id) JOIN kalbos_dalys USING(kalbos_dalies_id) WHERE kircio_vieta IS NOT NULL ORDER BY distance " + (limit ? "LIMIT 100" : ""),
////                BeanPropertyRowMapper.newInstance(SearchModel.class), search);
//
//        return jdbcTemplate.queryForObject("SELECT zodis AS word, levenshtein(zodis,?) AS distance, skiemenu_k AS syllables, kirciavimas AS stress FROM junction_table JOIN zodziai USING(zodzio_id) ORDER BY distance " + (limit ? "LIMIT 1000" : ""),
//                BeanPropertyRowMapper.newInstance(SearchModel.class), search);
//    }



    public String getRhymeIndex(final String word) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT m.rhyme_index FROM zodziai_kalbos_dalys_morfologija AS m JOIN zodziai AS z USING(zodzio_id) WHERE zodis = ? LIMIT 1",
                String.class,
                word);
    }

    public List<SyllableCountModel> syllableCountTable(final String rhymeIndex, final String ending, final int partOfSpeech) { // AND RIGHT(zodis, ?) LIKE ?
        return jdbcTemplate.query(
                "WITH list AS (SELECT DISTINCT ON (zodis, skiemenu_k) skiemenu_k FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? " + (ending == null ? "" : "AND RIGHT(zodis, '" + ending.length() + "') LIKE '" + ending + "'") + (partOfSpeech == 0 ? "" : " AND kalbos_dalies_id = " + partOfSpeech) +" ORDER BY zodis, skiemenu_k) SELECT skiemenu_k, COUNT(*) AS row_count FROM list GROUP BY skiemenu_k",
                (resultSet, rowNum) ->
                    new SyllableCountModel(resultSet.getInt("skiemenu_k"), resultSet.getInt("row_count")),
                "%" + rhymeIndex);
    }

    public List<WordModel> searchAssonance(final String rhymeIndex) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk <= 100",
                new WordModelMapper(),
                "%" + rhymeIndex);
    }

    public List<WordModel> searchAssonancePage(final String rhymeIndex, final int syllableCount, final int from, final int to) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND skiemenu_k = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk > ? AND rnk <= ?",
                new WordModelMapper(),
                "%" + rhymeIndex,
                syllableCount,
                from,
                to);
    }

    public List<WordModel> filteredAssonance(final String rhymeIndex, final int partOfSpeech) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND kalbos_dalies_id = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk <= 100",
                new WordModelMapper(),
                "%" + rhymeIndex,
                partOfSpeech);
    }

    public List<WordModel> filteredAssonancePage(final String rhymeIndex, final int partOfSpeech, final int syllableCount, final int from, final int to) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND kalbos_dalies_id = ? AND skiemenu_k = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk > ? AND rnk <= ?",
                new WordModelMapper(),
                "%" + rhymeIndex,
                partOfSpeech,
                syllableCount,
                from,
                to);
    }

    public List<WordModel> searchEnding(final String rhymeIndex, final String ending) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND RIGHT(zodis, ?) LIKE ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk <= 100",
                new WordModelMapper(),
                "%" + rhymeIndex,
                ending.length(),
                "%" + ending);
    }

    public List<WordModel> searchEndingPage(final String rhymeIndex, final String ending, final int syllableCount, final int from, final int to) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND RIGHT(zodis, ?) LIKE ? AND skiemenu_k = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk > ? AND rnk <= ?",
                new WordModelMapper(),
                "%" + rhymeIndex,
                ending.length(),
                ending,
                syllableCount,
                from,
                to);
    }

    public List<WordModel> filteredEnding(final String rhymeIndex, final int partOfSpeech, final String ending) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND kalbos_dalies_id = ? AND RIGHT(zodis, ?) LIKE ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk <= 100",
                new WordModelMapper(),
                "%" + rhymeIndex,
                partOfSpeech,
                ending.length(),
                "%" + ending);
    }

    public List<WordModel> filteredEndingPage(final String rhymeIndex, final int partOfSpeech, final String ending, final int syllableCount, final int from, final int to) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND kalbos_dalies_id = ? AND RIGHT(zodis, ?) LIKE ? AND skiemenu_k = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk > ? AND rnk <= ?",
                new WordModelMapper(),
                "%" + rhymeIndex,
                partOfSpeech,
                ending.length(),
                ending,
                syllableCount,
                from,
                to);
    }
}