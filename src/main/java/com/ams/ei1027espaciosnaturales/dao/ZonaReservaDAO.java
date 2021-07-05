package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.ZonaReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZonaReservaDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addZonaReserva(ZonaReserva zonaReserva) throws DuplicateKeyException {
        jdbcTemplate.update("INSERT INTO zona_reserva VALUES(?,?)",
                zonaReserva.getZona(),
                zonaReserva.getReserva()
        );
    }

    public void deleteZonaReserva(ZonaReserva zonaReserva) {
        jdbcTemplate.update("DELETE FROM zona_reserva WHERE id=? AND num_reserva=?",
                zonaReserva.getZona(),
                zonaReserva.getReserva()
        );
    }

    public void deleteZonaReserva(int zona, int reserva) {
        jdbcTemplate.update("DELETE FROM zona_reserva WHERE id=? AND num_reserva=?",
                zona,
                reserva
        );
    }

    public ZonaReserva getZonaReserva(int zona, int reserva) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM zona_reserva WHERE id=? AND num_reserva=?",
                    new ZonaReservaRowMapper(),
                    zona,
                    reserva
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<ZonaReserva> getZonasReservas() {
        try {
            return jdbcTemplate.query("SELECT * FROM zona_reserva",
                    new ZonaReservaRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
