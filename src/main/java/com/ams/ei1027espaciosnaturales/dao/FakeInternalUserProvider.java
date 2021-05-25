package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FakeInternalUserProvider implements UserDAO {

    final Map<String, UserInterno> knownUsers = new HashMap<>();

    public FakeInternalUserProvider() {
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

        // Usuario de prueba - gestormu1
        UserInterno gestormunicipal1 = new UserInterno();
        gestormunicipal1.setUsername("gestorm1");
        gestormunicipal1.setPassword(encryptor.encryptPassword("gestorm1"));
        knownUsers.put("gestorm1", gestormunicipal1);
    }

    @Override
    public UserInterno loadUserByUsername(String username, String password) {
        UserInterno user = knownUsers.get(username.trim());
        if (user == null) {
            return null;
        }
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        if (encryptor.checkPassword(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }

    }

    @Override
    public Collection<UserInterno> listAllUsers() {
        return knownUsers.values();
    }
}
