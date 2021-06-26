package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Comentario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComentarioRowMapper implements RowMapper<Comentario> {
    @Override
    public Comentario mapRow(ResultSet resultSet, int i) throws SQLException {
        Comentario comentario = new Comentario();
        comentario.setId(resultSet.getInt("id"));
        comentario.setDescripcion(resultSet.getString("descripcion"));
        comentario.setEspacioPublico(resultSet.getString("nombre"));

        return comentario;
    }
}
