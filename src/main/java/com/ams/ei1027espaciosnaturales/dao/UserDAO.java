package com.ams.ei1027espaciosnaturales.dao;

import com.ams.ei1027espaciosnaturales.model.UserInterno;
import java.util.Collection;

public interface UserDAO {

    UserInterno loadUserByUsername(String username, String password);
    Collection<UserInterno> listAllUsers();

}
