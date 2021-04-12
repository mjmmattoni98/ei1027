package com.ams.ei1027espaciosnaturales.dao;


import com.ams.ei1027espaciosnaturales.model.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CiudadanoDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addCiudadano(Ciudadano c) {
        jdbcTemplate.update("INSERT INTO Ciudadano VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",
                c.getDni(),
                c.getNombre(),
                c.getApellidos(),
                c.getEdad(),
                c.getCalle(),
                c.getNumero(),
                c.getCodigoPostal(),
                c.getPoblacion(),
                c.getTelefono(),
                c.getEmail(),
                c.getCodigo(),
                c.getPin()
        );
    }

    public void deleteCiudadano(Ciudadano c) {
        jdbcTemplate.update("DELETE FROM Ciudadano WHERE dni=?",
                c.getDni());
    }

    public void updateCiudadano(Ciudadano c) {
        jdbcTemplate.update("UPDATE Ciudadano SET dni=?, nombre=?, apellidos=?, edad=?, calle=?, numero=?, cp=?, poblacion=?, telefono=?, email=?, codigo=?, pin=?",
                c.getDni(),
                c.getNombre(),
                c.getApellidos(),
                c.getEdad(),
                c.getCalle(),
                c.getNumero(),
                c.getCodigoPostal(),
                c.getPoblacion(),
                c.getTelefono(),
                c.getEmail(),
                c.getCodigo(),
                c.getPin()
        );
    }

    public Ciudadano getCiudadano(String dni) {
        try {
            return jdbcTemplate .queryForObject(
                    "SELECT * FROM Ciudadano WHERE dni = '" + dni + "'",
                    new CiudadanoRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<Ciudadano> getCiudadanos() {
        try {
            return jdbcTemplate.query("SELECT * FROM Ciudadano",
                    new CiudadanoRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Ciudadano>();
        }
    }
}
