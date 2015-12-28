package com.epam.brest.courses.testers.service;

import com.epam.brest.courses.testers.domain.User;

import java.util.List;

/**
 * Created by xalf on 27.12.15.
 */
public interface UserService {

    List<User> getUsers();

    List<User> getUserById(Integer userId);

    List<User> getUserByLogin(String login);

    Integer getTotalUsersCount();

    Integer addUser(User user);

    void updateUser(User user);

    void deleteUser(Integer userId);

}
