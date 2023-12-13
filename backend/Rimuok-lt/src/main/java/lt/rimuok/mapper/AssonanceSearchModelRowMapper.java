package lt.rimuok.mapper;

import lt.rimuok.model.AssonanceSearchModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssonanceSearchModelRowMapper implements RowMapper<AssonanceSearchModel> {
    @Override
    public AssonanceSearchModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        AssonanceSearchModel result = new AssonanceSearchModel();
        result.setWo(resultSet.getString("zodis"));
        result.setSy(resultSet.getInt("skiemenu_k"));
        result.setSa(resultSet.getInt("kircio_vieta"));
        result.setSt(resultSet.getInt("kircio_zenklas"));
        result.setPs(resultSet.getInt("kalbos_dalies_id"));

        return result;
    }
}
