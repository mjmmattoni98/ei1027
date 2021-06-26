package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Municipio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MunicipioDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addMunicipio(Municipio m) throws DuplicateKeyException {
        jdbcTemplate.update("INSERT INTO municipio(nombre, provincia) VALUES(?,?)",
                m.getNombre(),
                m.getProvincia()
        );
    }

    public void deleteMunicipio(Municipio m) {
        jdbcTemplate.update("DELETE FROM municipio WHERE id=?",
                m.getId());
    }

    public void deleteMunicipio(int id) {
        jdbcTemplate.update("DELETE FROM municipio WHERE id=?", id);
    }

    public void updateMunicipio(Municipio m) {
        jdbcTemplate.update("UPDATE municipio SET id=?, nombre=?, provincia=? WHERE id=?",
        		m.getId(),
                m.getNombre(),
        		m.getProvincia(),
        		m.getId()
        );
    }

    public Municipio getMunicipio(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM municipio WHERE id=?",
                    new MunicipioRowMapper(),
                    id
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<Municipio> getMunicipios() {
        try {
            return jdbcTemplate.query("SELECT * FROM municipio",
                    new MunicipioRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
