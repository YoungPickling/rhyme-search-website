package lt.rimuok.mapper;

import lt.rimuok.model.AsonanceSearchModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AsonanceSearchModelRowMapper implements RowMapper<AsonanceSearchModel> {
    @Override
    public AsonanceSearchModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        AsonanceSearchModel result = new AsonanceSearchModel();
        result.setWo(resultSet.getString("zodis"));
        result.setSy(resultSet.getInt("skiemenu_k"));
        result.setSa(resultSet.getInt("kircio_vieta"));
        result.setSt(resultSet.getInt("kircio_zenklas"));
        result.setPs(resultSet.getInt("kalbos_dalies_id"));

        return result;
    }
}
