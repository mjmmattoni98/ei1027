package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EspacioServicioPermanente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EspacioServicioPermanenteDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addEspacioServicioPermanente(EspacioServicioPermanente e) {
        jdbcTemplate.update("INSERT INTO espacio_servicio_permanente VALUES(?,?)",
                e.getNombre(),
                e.getTipo()
        );
    }

    public void deleteEspacioServicioPermanente(EspacioServicioPermanente e) {
        jdbcTemplate.update("DELETE FROM espacio_servicio_permanente WHERE nombre=? AND tipo=?",
                e.getNombre(),
                e.getTipo()
        );
    }

    public void deleteEspacioServicioPermanente(String nombre, String tipo) {
        jdbcTemplate.update("DELETE FROM espacio_servicio_permanente WHERE nombre=? AND tipo=?",
                nombre,
                tipo
        );
    }

    public void updateEspacioServicioPermanente(EspacioServicioPermanente e) {
        jdbcTemplate.update("UPDATE espacio_servicio_permanente SET nombre=?, tipo=? WHERE nombre=? AND tipo=?",
                e.getNombre(),
                e.getTipo(),
                e.getNombre(),
                e.getTipo()
        );
    }

    public EspacioServicioPermanente getEspacioServicioPermanente(String nombre, String tipo) {
        try {
            return jdbcTemplate .queryForObject(
                    "SELECT * FROM espacio_servicio_permanente WHERE nombre=? AND tipo=?",
                    new EspacioServicioPermanenteRowMapper(),
                    nombre,
                    tipo
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<EspacioServicioPermanente> getEspaciosServiciosPermanentes() {
        try {
            return jdbcTemplate.query("SELECT * FROM espacio_servicio_permanente",
                    new EspacioServicioPermanenteRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
