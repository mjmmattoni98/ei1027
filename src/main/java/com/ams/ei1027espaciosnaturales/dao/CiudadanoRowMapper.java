package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Ciudadano;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CiudadanoRowMapper implements RowMapper<Ciudadano> {


    @Override
    public Ciudadano mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setNombre(resultSet.getString("nombre"));
        ciudadano.setApellidos(resultSet.getString("apellidos"));
        ciudadano.setEdad(resultSet.getInt("edad"));
        ciudadano.setCalle(resultSet.getString("calle"));
        ciudadano.setNumero(resultSet.getInt("numero"));
        ciudadano.setCodigoPostal(resultSet.getInt("cp"));
        ciudadano.setPoblacion(resultSet.getString("poblacion"));
        ciudadano.setTelefono(resultSet.getString("telefono"));
        ciudadano.setEmail(resultSet.getString("email"));
        ciudadano.setDni(resultSet.getString("dni"));
        ciudadano.setCodigo(resultSet.getString("codigo"));
        ciudadano.setPin(resultSet.getInt("pin"));
        return ciudadano;
    }
}
