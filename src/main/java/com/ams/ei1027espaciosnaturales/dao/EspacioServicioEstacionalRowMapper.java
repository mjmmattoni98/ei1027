package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EspacioServicioEstacional;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public final class EspacioServicioEstacionalRowMapper implements RowMapper<EspacioServicioEstacional> {

    @Override
    public EspacioServicioEstacional mapRow(@NotNull ResultSet resultSet, int i) throws SQLException {
        EspacioServicioEstacional espSerEst = new EspacioServicioEstacional();
        espSerEst.setEspacioPublico(resultSet.getString("nombre"));
        espSerEst.setTipo(resultSet.getString("tipo"));
        espSerEst.setFechaIni(resultSet.getObject("fecha_inicio", LocalDate.class));
        espSerEst.setFechaFin(resultSet.getObject("fecha_fin", LocalDate.class));
        espSerEst.setHoraIni(resultSet.getObject("hora_inicio", LocalTime.class));
        espSerEst.setHoraFin(resultSet.getObject("hora_fin", LocalTime.class));
        espSerEst.setLugarContratacion(resultSet.getString("lugar_contratacion"));
        espSerEst.setDescripcion(resultSet.getString("descripcion"));

        return espSerEst;
    }
}
