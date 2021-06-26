package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Email;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmailRowMapper implements RowMapper<Email> {
    @Override
    public Email mapRow(ResultSet resultSet, int i) throws SQLException {
        Email email = new Email();
        email.setId(resultSet.getInt("id"));
        email.setFecha(resultSet.getObject("fecha", LocalDate.class));
        email.setRemitente(resultSet.getString("remitente"));
        email.setDestinatario(resultSet.getString("destinatario"));
        email.setAsunto(resultSet.getString("asunto"));
        email.setCuerpo(resultSet.getString("cuerpo"));

        return email;
    }
}
