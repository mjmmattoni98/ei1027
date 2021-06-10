package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInternoRowMapper implements RowMapper<UserInterno> {

    public UserInterno mapRow(ResultSet rs, int i) throws SQLException{
        UserInterno userInterno = new UserInterno();
        userInterno.setUsername(rs.getString("usuario"));
        userInterno.setPassword(rs.getString("contraseña"));
        return userInterno;
    }

}
