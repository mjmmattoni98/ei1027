package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EstadoReserva;
import com.ams.ei1027espaciosnaturales.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRowMapper implements RowMapper<Reserva> {
    @Override
    public Reserva mapRow(ResultSet resultSet, int i) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setDni(resultSet.getString("dni"));
        reserva.setEspacioPublico(resultSet.getString("nombre"));
        reserva.setNumReserva(resultSet.getInt("num_reserva"));
        reserva.setNumPersonas(resultSet.getInt("num_personas"));
        reserva.setZona(resultSet.getInt("id"));
        reserva.setHoraAcceso(resultSet.getObject("hora_acceso", LocalTime.class));
        reserva.setHoraSalida(resultSet.getObject("hora_salida", LocalTime.class));
        reserva.setInicioFranjaHoraria(resultSet.getObject("hora_inicio", LocalTime.class));
        reserva.setFinFranjaHoraria(resultSet.getObject("hora_fin", LocalTime.class));
        reserva.setFechaAcceso(resultSet.getObject("fecha_acceso", LocalDate.class));
        reserva.setFechaCreacion(resultSet.getObject("fecha_creacion", LocalDate.class));
        reserva.setEstado(EstadoReserva.stringToTipo(resultSet.getString("estado")));

        return reserva;
    }
}
