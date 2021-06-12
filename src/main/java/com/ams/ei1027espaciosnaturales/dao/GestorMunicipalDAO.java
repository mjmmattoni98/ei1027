package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Ciudadano;
import com.ams.ei1027espaciosnaturales.model.GestorMunicipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GestorMunicipalDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addGestorMunicipal(GestorMunicipal g) {
        //TODO throws PSQLException para la edad
        jdbcTemplate.update("INSERT INTO gestor_municipal VALUES(?,?,?,?,?,?)",
                g.getDni(),
                g.getNombre(),
                g.getApellidos(),
                g.getUsuario(),
                g.getPassword(),
                g.getIdMunicipio()
        );
    }

    public void deleteGestorMunicipal(GestorMunicipal g) {
        jdbcTemplate.update("DELETE FROM gestor_municipal WHERE dni=?",
                g.getDni()
        );
    }

    public void deleteGestorMunicipal(String dni) {
        jdbcTemplate.update("DELETE FROM gestor_municipal WHERE dni=?", dni);
    }

    public void updateGestorMunicipal(GestorMunicipal g) {
        jdbcTemplate.update("UPDATE gestor_municipal SET dni=?, nombre=?, apellidos=?, usuario=?, password=?, id=? WHERE dni=?",
                g.getDni(),
                g.getNombre(),
                g.getApellidos(),
                g.getUsuario(),
                g.getPassword(),
                g.getIdMunicipio(),
                g.getDni()
        );
    }

    public GestorMunicipal getGestorMunicipal(String dni) {
        try {
            return jdbcTemplate .queryForObject(
                    "SELECT * FROM gestor_municipal WHERE dni=?",
                    new GestorMunicipalRowMapper(),
                    dni
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<GestorMunicipal> getGestoresMunicipales() {
        try {
            return jdbcTemplate.query("SELECT * FROM gestor_municipal",
                    new GestorMunicipalRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
