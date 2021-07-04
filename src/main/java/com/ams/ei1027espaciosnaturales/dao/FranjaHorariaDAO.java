package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.FranjaHoraria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FranjaHorariaDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addFranjaHoraria(FranjaHoraria franjaHoraria) throws DuplicateKeyException {
        jdbcTemplate.update("INSERT INTO franja_horaria VALUES(?,?,?)",
                franjaHoraria.getInicio(),
                franjaHoraria.getFin(),
                franjaHoraria.getEspacioPublico()
        );
    }

    public void deleteFranjaHoraria(FranjaHoraria franjaHoraria) {
        jdbcTemplate.update("DELETE FROM franja_horaria WHERE hora_inicio=? AND hora_fin=? AND nombre=?",
                franjaHoraria.getInicio(),
                franjaHoraria.getFin(),
                franjaHoraria.getEspacioPublico()
        );
    }

    public void deleteFranjaHoraria(LocalTime inicio, LocalTime fin, String espacioPublico) {
        jdbcTemplate.update("DELETE FROM franja_horaria WHERE hora_inicio=? AND hora_fin=? AND nombre=?",
                inicio,
                fin,
                espacioPublico
        );
    }

    public List<FranjaHoraria> getFranjaHoraria(String espacioPublico){
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM franja_horaria WHERE nombre=?",
                    new FranjaHorariaRowMapper(),
                    espacioPublico
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public FranjaHoraria getFranjaHoraria(LocalTime inicio, LocalTime fin, String espacioPublico) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM franja_horaria WHERE hora_inicio=? AND hora_fin=? AND nombre=?",
                    new FranjaHorariaRowMapper(),
                    inicio,
                    fin,
                    espacioPublico
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<FranjaHoraria> getFranjasHorarias() {
        try {
            return jdbcTemplate.query("SELECT * FROM franja_horaria",
                    new FranjaHorariaRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
