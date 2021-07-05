package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ComentarioDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addComentario(Comentario comentario) {
        jdbcTemplate.update("INSERT INTO comentario(descripcion, nombre) VALUES(?,?)",
                comentario.getDescripcion(),
                comentario.getEspacioPublico()
        );
    }

    public void deleteComentario(Comentario comentario) {
        jdbcTemplate.update("DELETE FROM comentario WHERE id=?",
                comentario.getId());
    }

    public void deleteComentario(String nombre) {
        jdbcTemplate.update("DELETE FROM comentario WHERE nombre=?", nombre);
    }

    public void deleteComentario(int id) {
        jdbcTemplate.update("DELETE FROM comentario WHERE id=?", id);
    }

    public void updateComentario(Comentario comentario) {
        jdbcTemplate.update("UPDATE comentario SET descripcion=?, nombre=? WHERE id=?",
                comentario.getDescripcion(),
                comentario.getEspacioPublico(),
                comentario.getId()
        );
    }

    public Comentario getComentario(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM comentario WHERE id=?",
                    new ComentarioRowMapper(),
                    id
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<Comentario> getComentarios() {
        try {
            return jdbcTemplate.query("SELECT * FROM comentario",
                    new ComentarioRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
