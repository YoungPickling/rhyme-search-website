package lt.rimuok.repository;

import lt.rimuok.mapper.AssonanceSearchModelRowMapper;
import lt.rimuok.model.AssonanceSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String getRhymeIndex(final String word) {
        return jdbcTemplate.queryForObject(
                "SELECT m.rhyme_index FROM zodziai_kalbos_dalys_morfologija AS m JOIN zodziai AS z USING(zodzio_id) WHERE zodis = ? LIMIT 1",
                String.class,
                word);
    }

    public List<AssonanceSearchModel> searchAssonance(final String rhymeIndex) {
        System.out.println(rhymeIndex);
        return jdbcTemplate.query(
                "SELECT zodis,skiemenu_k,kircio_vieta,kircio_zenklas,kalbos_dalies_id FROM zodziai_kalbos_dalys_morfologija AS m JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? ORDER BY m.id",
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex);
    }

    public List<AssonanceSearchModel> searchAssonanceWithEnding(final String rhymeIndex, final int length, final String ending) {
        return jdbcTemplate.query(
                "SELECT * FROM zodziai_kalbos_dalys_morfologija AS m JOIN zodziai USING(zodzio_id) WHERE m.rhyme_index LIKE ? AND RIGHT(zodis, ?) LIKE ? ORDER BY skiemenu_k",
                new AssonanceSearchModelRowMapper(),
                "%" + rhymeIndex,
                length,
                "%" + ending);
    }

//    (resultSet, rowNum) -> {
//        AsonanceSearchModel result = new AsonanceSearchModel();
//        result.setWo(resultSet.getString("zodis"));
//        result.setSy(resultSet.getInt("skiemenu_k"));
//        result.setSa(resultSet.getInt("kircio_vieta"));
//        result.setSt(resultSet.getInt("kircio_zenklas"));
//        result.setPs(resultSet.getInt("kalbos_dalies_id"));
//
//        return result;
//    }
}
