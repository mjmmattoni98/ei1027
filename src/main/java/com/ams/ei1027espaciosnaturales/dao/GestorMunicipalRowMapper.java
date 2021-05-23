package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.GestorMunicipal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorMunicipalRowMapper implements RowMapper<GestorMunicipal> {
    @Override
    public GestorMunicipal mapRow(ResultSet resultSet, int i) throws SQLException {
        GestorMunicipal gestorMunicipal = new GestorMunicipal();
        gestorMunicipal.setDni(resultSet.getString("dni"));
        gestorMunicipal.setNombre(resultSet.getString("nombre"));
        gestorMunicipal.setApellidos(resultSet.getString("apellidos"));
        gestorMunicipal.setUsuario(resultSet.getString("usuario"));
        gestorMunicipal.setPassword(resultSet.getString("contraseña"));
        gestorMunicipal.setIdMunicipio(resultSet.getInt("id"));

        return gestorMunicipal;
    }
}
