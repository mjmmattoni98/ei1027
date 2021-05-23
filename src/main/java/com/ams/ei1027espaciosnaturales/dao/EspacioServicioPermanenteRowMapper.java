package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EspacioServicioPermanente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EspacioServicioPermanenteRowMapper implements RowMapper<EspacioServicioPermanente> {
    @Override
    public EspacioServicioPermanente mapRow(ResultSet resultSet, int i) throws SQLException {
        EspacioServicioPermanente espSerPer = new EspacioServicioPermanente();
        espSerPer.setNombre(resultSet.getString("nombre"));
        espSerPer.setTipo(resultSet.getString("tipo"));

        return espSerPer;
    }
}
