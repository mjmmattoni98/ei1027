package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EspacioPublico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EspacioPublicoDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addEspacioPublico(EspacioPublico e) throws DuplicateKeyException {
        jdbcTemplate.update("INSERT INTO espacio_publico VALUES(?,?,?::tipo_espacio,?::tipo_suelo,?::tipo_acceso," +
                        "?,?,?,?,?)",
                e.getNombre(),
                e.getLocalizacionGeografica(),
                e.getTEspacio(),
                e.getTSuelo(),
                e.getTAcceso(),
                e.getDescripcion(),
                e.getLongitud(),
                e.getAnchura(),
                e.getOrientacion(),
                e.getIdMunicipio()
        );
    }

    public void deleteEspacioPublico(EspacioPublico e) {
        jdbcTemplate.update("DELETE FROM espacio_publico WHERE nombre=?",
                e.getNombre());
    }

    public void deleteEspacioPublicoNombre(String nombre) {
        jdbcTemplate.update("DELETE FROM espacio_publico WHERE nombre=?", nombre);
    }

    public void updateEspacioPublico(EspacioPublico e) {
        jdbcTemplate.update("UPDATE espacio_publico SET localizacion_geografica=?, t_espacio=?::tipo_espacio, " +
                        "t_suelo=?::tipo_suelo, t_acceso=?::tipo_acceso, descripcion=?, longitud=?, anchura=?, " +
                        "orientacion=?, id=? WHERE nombre=?",
                e.getLocalizacionGeografica(),
                e.getTEspacio(),
                e.getTSuelo(),
                e.getTAcceso(),
                e.getDescripcion(),
                e.getLongitud(),
                e.getAnchura(),
                e.getOrientacion(),
                e.getIdMunicipio(),
                e.getNombre()
        );
    }

    public EspacioPublico getEspacioPublico(String nombre) {
        try {
            return jdbcTemplate .queryForObject(
                    "SELECT * FROM espacio_publico WHERE nombre=?",
                    new EspacioPublicoRowMapper(),
                    nombre
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<EspacioPublico> getEspaciosPublicos() {
        try {
            return jdbcTemplate.query("SELECT * FROM espacio_publico",
                    new EspacioPublicoRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<EspacioPublico> getEspaciosPublicosRestringidos() {
        try {
            return jdbcTemplate.query("select * from espacio_publico WHERE t_acceso='restringido';",
                    new EspacioPublicoRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
