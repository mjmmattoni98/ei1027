package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.FranjaHoraria;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class FranjaHorariaRowMapper implements RowMapper<FranjaHoraria> {
    @Override
    public FranjaHoraria mapRow(ResultSet resultSet, int i) throws SQLException {
        FranjaHoraria franjaHoraria = new FranjaHoraria();
        franjaHoraria.setInicio(resultSet.getObject("hora_inicio", LocalTime.class));
        franjaHoraria.setFin(resultSet.getObject("hora_fin", LocalTime.class));
        franjaHoraria.setEspacioPublico(resultSet.getString("nombre"));

        return franjaHoraria;
    }
}
