package lt.rimuok.mapper;

import lt.rimuok.model.WordModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssonanceSearchModelRowMapper implements RowMapper<WordModel> {
    @Override
    public WordModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        WordModel result = new WordModel();
        result.setWo(resultSet.getString("zodis"));
        result.setSy(resultSet.getInt("skiemenu_k"));
        result.setSa(resultSet.getInt("kircio_vieta"));
        result.setSt(resultSet.getInt("kircio_zenklas"));
        result.setPs(resultSet.getInt("kalbos_dalies_id"));

        return result;
    }
}
