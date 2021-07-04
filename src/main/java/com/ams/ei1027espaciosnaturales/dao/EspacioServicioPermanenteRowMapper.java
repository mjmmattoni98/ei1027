package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EspacioServicioPermanente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class EspacioServicioPermanenteRowMapper implements RowMapper<EspacioServicioPermanente> {
    @Override
    public EspacioServicioPermanente mapRow(ResultSet resultSet, int i) throws SQLException {
        EspacioServicioPermanente espSerPer = new EspacioServicioPermanente();
        espSerPer.setEspacioPublico(resultSet.getString("nombre"));
        espSerPer.setTipo(resultSet.getString("tipo"));
        espSerPer.setDescripcion(resultSet.getString("descripcion"));

        return espSerPer;
    }
}
