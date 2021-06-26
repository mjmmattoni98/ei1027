package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserProvider implements UserDAO {

    private final BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Map<String, UserInterno> getUserList(){
        Map<String, UserInterno> knownUsers = new HashMap<>();

        try {
            List<UserInterno> userInternos = jdbcTemplate.query("SELECT usuario, password, dni " +
                            "FROM gestor_municipal;",
                    new UserInternoRowMapper());
            for(UserInterno user : userInternos){
                user.setRol("gestor");
                user.setPassword(encryptor.encryptPassword(user.getPassword()));
                knownUsers.put(user.getUsername(), user);
            }

            userInternos = jdbcTemplate.query("SELECT usuario, password, dni FROM ciudadano;",
                    new UserInternoRowMapper());
            for(UserInterno user : userInternos){
                user.setRol("ciudadano");
                user.setPassword(encryptor.encryptPassword(user.getPassword()));
                knownUsers.put(user.getUsername(), user);
            }

        }catch (EmptyResultDataAccessException ignored){}

        return  knownUsers;
    }


    @Override
    public UserInterno loadUserByUsername(String username, String password) {
        UserInterno user = getUserList().get(username.trim());
        if (user == null) {
            return null;
        }

        if (encryptor.checkPassword(password, user.getPassword())) {
//            System.out.println("llega hasta aqui");
            return user;
        } else {
            return null;
        }
    }

    @Override
    public Collection<UserInterno> listAllUsers() {
        return getUserList().values();
    }
}
