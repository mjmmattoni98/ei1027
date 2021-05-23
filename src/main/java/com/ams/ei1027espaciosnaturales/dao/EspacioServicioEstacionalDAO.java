package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EspacioServicioEstacional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EspacioServicioEstacionalDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addEspacioServicioEstacional(EspacioServicioEstacional e) {
        jdbcTemplate.update("INSERT INTO espacio_servicio_estacional VALUES(?,?,?,?,?,?,?)",
                e.getNombre(),
                e.getTipo(),
                e.getFechaIni(),
                e.getFechaFin(),
                e.getHoraIni(),
                e.getHoraFin(),
                e.getLugarContratacion()
        );
    }

    public void deleteEspacioServicioEstacional(EspacioServicioEstacional e) {
        jdbcTemplate.update("DELETE FROM espacio_servicio_estacional WHERE nombre=? AND tipo=? AND fecha_inicio=? and hora_inicio=?",
                e.getNombre(),
                e.getTipo(),
                e.getFechaIni(),
                e.getHoraIni()
        );
    }

    public void deleteEspacioServicioEstacional(String nombre, String tipo, LocalDate fechaInicio, LocalTime horaInicio) {
        jdbcTemplate.update("DELETE FROM espacio_servicio_estacional WHERE nombre=? AND tipo=? AND fecha_inicio=? and hora_inicio=?",
                nombre,
                tipo,
                fechaInicio,
                horaInicio
        );
    }

    public void updateEspacioServicioEstacional(EspacioServicioEstacional e) {
        jdbcTemplate.update("UPDATE espacio_servicio_estacional SET nombre=?, tipo=?, fecha_inicio=?, hora_inicio=?, fecha_fin=?, hora_fin=?, lugar_contratacion=? WHERE nombre=? AND tipo=? AND fecha_inicio=? AND hora_inicio=?",
                e.getNombre(),
                e.getTipo(),
                e.getFechaIni(),
                e.getHoraIni(),
                e.getFechaFin(),
                e.getHoraFin(),
                e.getLugarContratacion(),
                e.getNombre(),
                e.getTipo(),
                e.getFechaIni(),
                e.getHoraIni()
        );
    }

    public EspacioServicioEstacional getEspacioServicioEstacional(String nombre, String tipo, LocalDate fechaInicio, LocalTime horaInicio) {
        try {
            return jdbcTemplate .queryForObject(
                    "SELECT * FROM espacio_servicio_estacional WHERE nombre=? AND tipo=? AND fecha_inicio=? AND hora_inicio=?",
                    new EspacioServicioEstacionalRowMapper(),
                    nombre,
                    tipo,
                    fechaInicio,
                    horaInicio
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<EspacioServicioEstacional> getEspaciosServiciosEstacionales() {
        try {
            return jdbcTemplate.query("SELECT * FROM espacio_servicio_estacional",
                    new EspacioServicioEstacionalRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
