package lt.rimuok.mapper;

import lt.rimuok.model.WordModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WordModelMapper implements RowMapper<WordModel> {
    @Override
    public WordModel mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        WordModel result = new WordModel();
        result.setWord(resultSet.getString("zodis"));
        result.setSyllable(resultSet.getInt("skiemenu_k"));
        result.setStressAt(resultSet.getInt("kircio_vieta"));
        result.setStressType(resultSet.getInt("kircio_zenklas"));
        result.setPartOfSpeech(resultSet.getInt("kalbos_dalies_id"));

        return result;
    }
}
