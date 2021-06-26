package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservaDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addReserva(Reserva reserva) throws DuplicateKeyException {
        jdbcTemplate.update("INSERT INTO reserva VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",
                reserva.getNumReserva(),
                reserva.getHoraAcceso(),
                reserva.getHoraSalida(),
                reserva.getFechaAcceso(),
                reserva.getFechaCreacion(),
                reserva.getNumPersonas(),
                reserva.getEstado().getValue(),
                reserva.getDni(),
                reserva.getInicioFranjaHoraria(),
                reserva.getFinFranjaHoraria(),
                reserva.getEspacioPublico(),
                reserva.getZona()
        );
    }

    public void deleteReserva(Reserva reserva) {
        jdbcTemplate.update("DELETE FROM reserva WHERE num_reserva=?",
                reserva.getNumReserva());
    }

    public void deleteReserva(int numReserva) {
        jdbcTemplate.update("DELETE FROM reserva WHERE num_reserva=?", numReserva);
    }

    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE reserva SET hora_acceso=?, hora_salida=?, fecha_acceso=?, fecha_creacion=?," +
                        "num_presonas=?, estado=?, dni=?, hora_inicio=?, hora_fin=?, nombre=?, id=? WHERE num_reserva=?",
                reserva.getHoraAcceso(),
                reserva.getHoraSalida(),
                reserva.getFechaAcceso(),
                reserva.getFechaCreacion(),
                reserva.getNumPersonas(),
                reserva.getEstado().getValue(),
                reserva.getDni(),
                reserva.getInicioFranjaHoraria(),
                reserva.getFinFranjaHoraria(),
                reserva.getEspacioPublico(),
                reserva.getZona(),
                reserva.getNumReserva()
        );
    }

    public Reserva getReserva(int numReserva) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM reserva WHERE num_reserva=?",
                    new ReservaRowMapper(),
                    numReserva
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<Reserva> getReservas() {
        try {
            return jdbcTemplate.query("SELECT * FROM reserva",
                    new ReservaRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
