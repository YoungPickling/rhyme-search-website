package lt.rimuok.repository;

import lt.rimuok.mapper.AssonanceSearchModelRowMapper;
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



//    public List<SearchModel> searchQuery(String search) {
//        return searchQuery(search, false);
//    }
//
//    public List<SearchModel> searchQuery(String search, boolean limit) {
//        return jdbcTemplate.query("SELECT zodis AS word, levenshtein(zodis,?) AS distance, skiemenu_k AS syllables, kirciavimas AS stress FROM junction_table JOIN zodziai USING(zodzio_id) ORDER BY distance " + (limit ? "LIMIT 1000" : ""),
//                (resultSet, rowNum) -> {
//                    SearchModel searchModel = new SearchModel();
//                    searchModel.setWord(resultSet.getString("word"));
//                    searchModel.setDistance(resultSet.getInt("distance"));
//                    searchModel.setSyllables(resultSet.getInt("syllables"));
//
//                    // Retrieve "stress" value
//                    String stressString = resultSet.getString("stress");
//
//                    // Convert "stress" string to a 2D integer array
//                    int[][] stressArray = Utils.convertStringTo2DArray(stressString);
//                    searchModel.setStress(stressArray);
//
//                    return searchModel;
//                }, search);
//    }

    public String getRhymeIndex(final String word) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT m.rhyme_index FROM zodziai_kalbos_dalys_morfologija AS m JOIN zodziai AS z USING(zodzio_id) WHERE zodis = ? LIMIT 1",
                String.class,
                word);
    }

    public List<SyllableCountModel> syllableCountTable(final String rhymeIndex) {
        return jdbcTemplate.query(
                "SELECT DISTINCT ON (skiemenu_k) skiemenu_k, COUNT(*) OVER (PARTITION BY skiemenu_k) AS row_count FROM zodziai_kalbos_dalys_morfologija AS m JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE '%Ai' ORDER BY skiemenu_k, row_count",
                (resultSet, rowNum) ->
                    new SyllableCountModel(resultSet.getInt("skiemenu_k"), resultSet.getInt("row_count")),
                "%" + rhymeIndex);
    }

    public List<WordModel> searchAssonance(final String rhymeIndex) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk <= 100",
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex);
    }

    public List<WordModel> searchAssonancePage(final String rhymeIndex, final int syllableCount, final int from, final int to) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND skiemenu_k = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk > ? AND rnk <= ?",
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex,
                syllableCount,
                from,
                to);
    }

    public List<WordModel> filteredAssonance(final String rhymeIndex, final int partOfSpeech) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND kalbos_dalies_id = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk <= 100",
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex,
                partOfSpeech);
    }

    public List<WordModel> filteredAssonancePage(final String rhymeIndex, final int partOfSpeech, final int syllableCount, final int from, final int to) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND kalbos_dalies_id = ? AND skiemenu_k = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk > ? AND rnk <= ?",
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex,
                partOfSpeech,
                syllableCount,
                from,
                to);
    }

    public List<WordModel> searchEnding(final String rhymeIndex, final String ending) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND RIGHT(zodis, ?) LIKE ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk <= 100",
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex,
                ending.length(),
                "%" + ending);
    }

    public List<WordModel> searchEndingPage(final String rhymeIndex, final String ending, final int syllableCount, final int from, final int to) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND RIGHT(zodis, ?) LIKE ? AND skiemenu_k = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk > ? AND rnk <= ?",
                new AssonanceSearchModelRowMapper(),
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
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex,
                partOfSpeech,
                ending.length(),
                "%" + ending);
    }

    public List<WordModel> filteredEndingPage(final String rhymeIndex, final int partOfSpeech, final String ending, final int syllableCount, final int from, final int to) {
        return jdbcTemplate.query(
                "WITH list AS( SELECT DISTINCT ON (zodis,skiemenu_k) m.zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m INNER JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND kalbos_dalies_id = ? AND RIGHT(zodis, ?) LIKE ? AND skiemenu_k = ? ORDER BY skiemenu_k), filtered AS ( SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id, ROW_NUMBER() OVER (PARTITION BY skiemenu_k ORDER BY zodzio_id) AS rnk FROM list ) SELECT zodzio_id, zodis, skiemenu_k, kircio_vieta, kircio_zenklas, kalbos_dalies_id FROM filtered WHERE rnk > ? AND rnk <= ?",
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex,
                partOfSpeech,
                ending.length(),
                ending,
                syllableCount,
                from,
                to);
    }
}