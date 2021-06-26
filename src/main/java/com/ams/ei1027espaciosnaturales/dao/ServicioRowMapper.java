package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Servicio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ServicioRowMapper implements RowMapper<Servicio>{
    @Override
    public Servicio mapRow(ResultSet resultSet, int i) throws SQLException {
        Servicio servicio = new Servicio();
        servicio.setTipo(resultSet.getString("tipo"));
        servicio.setDescripcion(resultSet.getString("descripcion"));

        return servicio;
    }
}
