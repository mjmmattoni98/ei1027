package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.ZonaReserva;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZonaReservaRowMapper implements RowMapper<ZonaReserva> {

    @Override
    public ZonaReserva mapRow(@NotNull ResultSet resultSet, int i) throws SQLException {
        ZonaReserva zonaReserva = new ZonaReserva();
        zonaReserva.setZona(resultSet.getInt("id"));
        zonaReserva.setReserva(resultSet.getInt("num_reserva"));

        return zonaReserva;
    }
}
