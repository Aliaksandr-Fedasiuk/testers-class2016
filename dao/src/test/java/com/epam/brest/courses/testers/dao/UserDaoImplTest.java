package com.epam.brest.courses.testers.dao;

import com.epam.brest.courses.testers.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.brest.courses.testers.domain.User.Role;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional()
public class UserDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String USER_LOGIN = "login2";
    public static final String USER_PASSWORD = "password";

    @Autowired
    private UserDao userDao;

    private static final User user = new User(Role.ROLE_ADMIN, "FIO3", "login3", "password3");

    //@Test
    public void testGetAllUsers() throws Exception {
        LOGGER.debug("test: getUsers()");
        List<User> users = userDao.getUsers();
        assertTrue(users.size() == 2);
    }

    //@Test
    public void testGetUser() throws Exception {
        LOGGER.debug("test: getUser()");

        List<User> users = userDao.getUsers();
        assertTrue(users.size() > 0);

        Integer userId = users.get(0).getUserId();
        User user = userDao.getUserById(userId).get(0);
        assertNotNull(user);
        assertTrue(user.getUserId().equals(userId));
    }

    //@Test
    public void testGetUserByLogin() throws Exception {
        LOGGER.debug("test: getUserByLogin()");
        User user = userDao.getUserByLogin(USER_LOGIN).get(0);
        assertNotNull(user);
        assertTrue(user.getLogin().equals(USER_LOGIN));
    }

//    @Test
//    public void testCountUsers() throws Exception {
//        LOGGER.debug("test: countUsers()");
//        String login = userDao.getUsers().get(0).getLogin();
//        assertNotNull(login);
//        Integer usersCount = userDao.getCountUsers(login);
//        assertNotNull(usersCount);
//        assertTrue(usersCount.equals(1));
//    }
//
//    @Test
//    public void testZeroCountUsers() throws Exception {
//        LOGGER.debug("test: zeroCountUsers()");
//        String login = "qweqweqwe";
//        Integer usersCount = userDao.getCountUsers(login);
//        assertNotNull(usersCount);
//        assertTrue(usersCount.equals(0));
//    }

    //@Test
    public void testTotalUsersCount() throws Exception {
        LOGGER.debug("test: totalUsersCount()");
        Integer length = userDao.getUsers().size();
        Integer usersCount = userDao.getTotalUsersCount();
        assertEquals(length, usersCount);
    }

    @Test
    public void testAddUser() throws Exception {
        LOGGER.debug("test: addUser()");
        Integer userId = userDao.addUser(user);
        assertNotNull(userId);

        User newUser = userDao.getUserById(userId).get(0);
        assertNotNull(newUser);
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        //assertTrue(user.getUpdatedDate().equals(newUser.getUpdatedDate()));
        assertNotNull(newUser.getCreatedDate());
    }

    @Test
    public void testUpdateUser() throws Exception {
        LOGGER.debug("test: updateUser()");
        User user = userDao.getUserByLogin(USER_LOGIN).get(0);
        user.setAmount(2d);
        user.setName("FIO");

        userDao.updateUser(user, false);

        User newUser = userDao.getUserById(user.getUserId()).get(0);
        assertTrue(user.getName().equals(newUser.getName()));
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        assertTrue(user.getAmount().equals(newUser.getAmount()));
        assertTrue(user.getCreatedDate().equals(newUser.getCreatedDate()));
    }

//    @Test
//    public void testDeleteUser() throws Exception {
//        LOGGER.debug("test :deleteUser()");
//        List<User> users = userDao.getUsers();
//        assertTrue(users.size() > 0);
//        int sizeBefore = users.size();
//        userDao.deleteUser(users.get(0).getUserId());
//        assertTrue((sizeBefore - 1) == userDao.getUsers().size());
//    }
}