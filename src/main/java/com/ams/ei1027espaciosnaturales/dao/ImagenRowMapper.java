package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Imagen;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImagenRowMapper implements RowMapper<Imagen> {

    @Override
    public Imagen mapRow(ResultSet resultSet, int i) throws SQLException {
        Imagen imagen = new Imagen();
        imagen.setId(resultSet.getInt("id"));
        imagen.setArchivo(resultSet.getString("archivo"));
        imagen.setEspacioPublico(resultSet.getString("nombre"));

        return imagen;
    }
}
