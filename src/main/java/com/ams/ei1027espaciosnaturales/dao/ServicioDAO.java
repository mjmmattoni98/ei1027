package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Municipio;
import com.ams.ei1027espaciosnaturales.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ServicioDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addServicio(Servicio s) {
        if(s.getSupertipo().equals("E"))
            jdbcTemplate.update("INSERT INTO servicio_estacional VALUES(?,?)",
                    s.getTipo(),
                    s.getDescripcion()
            );
        else
            jdbcTemplate.update("INSERT INTO servicio_permanente VALUES(?,?)",
                    s.getTipo(),
                    s.getDescripcion()
            );
    }

    public void deleteServicio(Servicio s) {
        if(s.getSupertipo().equals("E"))
            jdbcTemplate.update("DELETE FROM servicio_estacional WHERE tipo=?",
                    s.getTipo()
            );
        else
            jdbcTemplate.update("DELETE FROM servicio_permanente WHERE tipo=?",
                    s.getTipo()
            );
    }

    public void deleteServicio(String tipo, String supertipo) {
        if(supertipo.equals("E"))
            jdbcTemplate.update("DELETE FROM servicio_estacional WHERE tipo=?",
                    tipo
            );
        else
            jdbcTemplate.update("DELETE FROM servicio_permanente WHERE tipo=?",
                    tipo
            );
    }

    public void updateServicio(Servicio s) {
        if(s.getSupertipo().equals("E"))
            jdbcTemplate.update("UPDATE servicio_estacional SET tipo=?, descripcion=? WHERE tipo=?",
                    s.getTipo(),
                    s.getDescripcion(),
                    s.getTipo()
            );
        else
            jdbcTemplate.update("UPDATE servicio_permanente SET tipo=?, descripcion=? WHERE tipo=?",
                    s.getTipo(),
                    s.getDescripcion(),
                    s.getTipo()
            );
    }

    public Servicio getServicio(String tipo, String supertipo) {
        try {
            if (supertipo.equals("E"))
                return jdbcTemplate .queryForObject(
                        "SELECT * FROM servicio_estacional WHERE tipo=?",
                        new ServicioRowMapper(),
                        tipo
                );
            return jdbcTemplate .queryForObject(
                    "SELECT * FROM servicio_permanente WHERE tipo=?",
                    new ServicioRowMapper(),
                    tipo
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<Servicio> getServicios() {
        try {
            List<Servicio> serviciosEstacional = jdbcTemplate.query("SELECT * FROM servicio_estacional",
                    new ServicioRowMapper()
            );
            for (Servicio s : serviciosEstacional)
                s.setSupertipo("E");
            List<Servicio> serviciosPermanentes = jdbcTemplate.query("SELECT * FROM servicio_permanente",
                    new ServicioRowMapper()
            );
            for (Servicio s : serviciosPermanentes)
                s.setSupertipo("P");

            List<Servicio> servicios = new LinkedList<>(serviciosEstacional);
            servicios.addAll(serviciosPermanentes);
            return servicios;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
