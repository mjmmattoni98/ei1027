package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Municipio;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class MunicipioRowMapper implements RowMapper<Municipio> {

    @Override
    public Municipio mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    	Municipio municipio = new Municipio();
    	municipio.setId(resultSet.getInt("id"));
    	municipio.setNombre(resultSet.getString("nombre"));
    	municipio.setProvincia(resultSet.getString("provincia"));
        return municipio;
    }
}
