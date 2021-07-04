package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmailDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addEmail(Email email) {
        jdbcTemplate.update("INSERT INTO email(fecha, remitente, destinatario, asunto, cuerpo) VALUES(?,?,?,?,?)",
                email.getFecha(),
                email.getRemitente(),
                email.getDestinatario(),
                email.getAsunto(),
                email.getCuerpo()
        );
    }

    public void deleteEmail(Email email) {
        jdbcTemplate.update("DELETE FROM email WHERE id=?",
                email.getId());
    }

    public void deleteEmail(int id) {
        jdbcTemplate.update("DELETE FROM email WHERE id=?", id);
    }

    public void updateEmail(Email email) {
        jdbcTemplate.update("UPDATE email SET fecha=?, remitente=?, destinatario=?, asunto=?, cuerpo=? WHERE id=?",
                email.getFecha(),
                email.getRemitente(),
                email.getDestinatario(),
                email.getAsunto(),
                email.getCuerpo(),
                email.getId()
        );
    }

    public Email getEmail(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM email WHERE id=?",
                    new EmailRowMapper(),
                    id
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    public List<Email> getEmails(String destinatario) {
        try {
            return jdbcTemplate.query("SELECT * FROM email WHERE destinatario=?",
                    new EmailRowMapper(),
                    destinatario
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
