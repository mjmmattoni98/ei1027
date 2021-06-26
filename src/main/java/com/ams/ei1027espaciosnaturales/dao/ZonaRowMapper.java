package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Zona;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ZonaRowMapper implements RowMapper<Zona> {
    @Override
    public Zona mapRow(ResultSet resultSet, int i) throws SQLException {
        Zona zona = new Zona();
        zona.setId(resultSet.getInt("id"));
        zona.setCapacidad(resultSet.getInt("capacidad"));
        zona.setOcupacion(resultSet.getInt("ocupacion"));
        zona.setNombreEspacioPublico(resultSet.getString("nombre"));

        return zona;
    }
}
