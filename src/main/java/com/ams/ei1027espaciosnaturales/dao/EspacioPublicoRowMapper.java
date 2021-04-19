package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.EspacioPublico;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class EspacioPublicoRowMapper implements RowMapper<EspacioPublico> {

    @Override
    public EspacioPublico mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    	EspacioPublico espaciopublico = new EspacioPublico();
    	espaciopublico.setNombre(resultSet.getString("nombre"));
    	espaciopublico.setLocalicacionGeografica(resultSet.getString("localizaciónGeografica"));
    	espaciopublico.setTEspacio(resultSet.getString("tEspacio"));
    	espaciopublico.setTSuelo(resultSet.getString("tSuelo"));
    	espaciopublico.setDescripcion(resultSet.getString("descripcion"));
    	espaciopublico.setLongitud(resultSet.getInt("longitud"));
    	espaciopublico.setAnchura(resultSet.getInt("anchura"));
    	espaciopublico.setOrientacion(resultSet.getString("orientacion"));
    	espaciopublico.setIdMunicipio(resultSet.getInt("id"));
    	espaciopublico.setTAcceso(resultSet.getString("acceso"));
        return espaciopublico;
    }
}
