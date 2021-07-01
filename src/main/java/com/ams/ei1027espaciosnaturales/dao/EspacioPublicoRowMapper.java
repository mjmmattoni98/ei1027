package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EspacioPublico;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class EspacioPublicoRowMapper implements RowMapper<EspacioPublico> {

    @Override
    public EspacioPublico mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    	EspacioPublico espacioPublico = new EspacioPublico();
    	espacioPublico.setNombre(resultSet.getString("nombre"));
    	espacioPublico.setLocalicacionGeografica(resultSet.getString("localizacion_geografica"));
    	espacioPublico.setTEspacio(resultSet.getString("t_espacio"));
    	espacioPublico.setTSuelo(resultSet.getString("t_suelo"));
    	espacioPublico.setDescripcion(resultSet.getString("descripcion"));
    	espacioPublico.setLongitud(resultSet.getInt("longitud"));
    	espacioPublico.setAnchura(resultSet.getInt("anchura"));
    	espacioPublico.setOrientacion(resultSet.getString("orientacion"));
    	espacioPublico.setIdMunicipio(resultSet.getInt("id"));
    	espacioPublico.setTAcceso(resultSet.getString("t_acceso"));

        return espacioPublico;
    }
}
