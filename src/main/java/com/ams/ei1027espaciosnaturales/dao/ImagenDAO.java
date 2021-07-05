package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImagenDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addImagen(Imagen imagen) {
        jdbcTemplate.update("INSERT INTO imagen(archivo, nombre) VALUES(?,?)",
                imagen.getArchivo(),
                imagen.getEspacioPublico()
        );
    }

    public void deleteImagen(Imagen imagen) {
        jdbcTemplate.update("DELETE FROM imagen WHERE id=?",
                imagen.getId());
    }

    public void deleteImagen(int id) {
        jdbcTemplate.update("DELETE FROM imagen WHERE id=?", id);
    }

    public void deleteImagen(String nombre) {
        jdbcTemplate.update("DELETE FROM imagen WHERE nombre=?", nombre);
    }


    public void updateImagen(Imagen imagen) {
        jdbcTemplate.update("UPDATE imagen SET archivo=?, nombre=? WHERE id=?",
                imagen.getArchivo(),
                imagen.getEspacioPublico(),
                imagen.getId()
        );
    }

    public Imagen getImagen(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM imagen WHERE id=?",
                    new ImagenRowMapper(),
                    id
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<Imagen> getImagenes(String espacioPublico) {
        try {
            return jdbcTemplate.query("SELECT * FROM imagen WHERE nombre=?",
                    new ImagenRowMapper(),
                    espacioPublico
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
