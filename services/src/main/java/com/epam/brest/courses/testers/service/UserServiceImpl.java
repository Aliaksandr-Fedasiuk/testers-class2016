package com.epam.brest.courses.testers.service;

import com.epam.brest.courses.testers.dao.UserDao;
import com.epam.brest.courses.testers.domain.User;
import com.epam.brest.courses.testers.security.UserContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xalf on 27.12.15.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUsers() {
        LOGGER.debug("getUsers()");
        return userDao.getUsers();
    }

    @Override
    public List<User> getUserById(Integer userId) {
        LOGGER.debug("getUserById({})", userId);
        return userDao.getUserById(userId);
    }

    @Override
    public List<User> getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})", login);
        return userDao.getUserByLogin(login);
    }

    @Override
    public Integer getTotalUsersCount() {
        LOGGER.debug("getTotalUsersCount()");
        return userDao.getTotalUsersCount();
    }

    @Override
    public Integer addUser(User user) {
        LOGGER.debug("addUser({})", user);
        return userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser({})", user);
        User srcUser = userDao.getUserById(user.getUserId()).get(0);
        if (!srcUser.equals(user)) {
            userDao.updateUser(user);
        } else {
            LOGGER.debug("Users are equals. Username: " + user.getLogin());
        }
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.debug("deleteUser({})", userId);
        userDao.deleteUser(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.getUserByLogin(login).get(0);
        return new UserContext(user);
    }
}
