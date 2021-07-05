package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZonaDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addZona(Zona zona) {
        jdbcTemplate.update("INSERT INTO zona(capacidad, nombre) VALUES(?,?)",
                zona.getCapacidad(),
                zona.getNombreEspacioPublico()
        );
    }

    public void deleteZona(Zona zona) {
        jdbcTemplate.update("DELETE FROM zona WHERE id=?",
                zona.getId());
    }

    public void deleteZona(int id) {
        jdbcTemplate.update("DELETE FROM zona WHERE id=?", id);
    }

    public void updateZona(Zona zona) {
        jdbcTemplate.update("UPDATE zona SET capacidad=?, ocupacion=?, nombre=? WHERE id=?",
                zona.getCapacidad(),
                zona.getOcupacion(),
                zona.getNombreEspacioPublico(),
                zona.getId()
        );
    }

    public Zona getZona(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM zona WHERE id=?",
                    new ZonaRowMapper(),
                    id
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public void updateOcupacionZonas(int id, int capacidad){
             jdbcTemplate.update("UPDATE zona SET ocupacion=? WHERE id=?",
                    capacidad,
                    id
            );

    }

    public List<Zona> getZonas(String nombre) {
        try {
            return jdbcTemplate.query("SELECT * FROM zona WHERE nombre=?",
                    new ZonaRowMapper(),
                    nombre
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
