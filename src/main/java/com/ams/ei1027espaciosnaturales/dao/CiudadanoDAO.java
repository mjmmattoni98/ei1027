package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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

    public void addCiudadano(Ciudadano c) throws DuplicateKeyException {
        //TODO throws PSQLException para la edad
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
                c.getUsuario(),
                c.getPassword()
        );
    }

    public void deleteCiudadano(Ciudadano c) {
        jdbcTemplate.update("DELETE FROM Ciudadano WHERE dni=?",
                c.getDni());
    }

    public void deleteCiudadanoDNI(String dni) {
        jdbcTemplate.update("DELETE FROM Ciudadano WHERE dni=?", dni);
    }

    public void updateCiudadano(Ciudadano c) {
        //TODO throws PSQLException para la edad
        jdbcTemplate.update("UPDATE Ciudadano SET nombre=?, apellidos=?, edad=?, calle=?, numero=?, cp=?, " +
                        "poblacion=?, telefono=?, email=?, usuario=?, password=? WHERE dni=?",
                c.getNombre(),
                c.getApellidos(),
                c.getEdad(),
                c.getCalle(),
                c.getNumero(),
                c.getCodigoPostal(),
                c.getPoblacion(),
                c.getTelefono(),
                c.getEmail(),
                c.getUsuario(),
                c.getPassword(),
                c.getDni()
        );
    }

    public Ciudadano getCiudadano(String dni) {
        try {
            return jdbcTemplate .queryForObject(
                    "SELECT * FROM Ciudadano WHERE dni=?",
                    new CiudadanoRowMapper(),
                    dni
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<Ciudadano> getCiudadanos() {
        try {
            return jdbcTemplate.query("SELECT * FROM Ciudadano",
                    new CiudadanoRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
