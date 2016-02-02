package com.epam.brest.courses.testers.dao;

import com.epam.brest.courses.testers.domain.User;

import java.util.List;

/**
 * Created by xalf on 25.12.15.
 */
public interface UserDao {

    List<User> getUsers();

    List<User> getUserById(Integer userId);

    List<User> getManagers(String role);

    List<User> getUserByLogin(String login);

    Integer getTotalUsersCount();

    Integer addUser(User user);

    void updateUser(User user, boolean isPasswordNeedUpdate);

    void deleteUser(Integer userId);

}
