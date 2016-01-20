package com.epam.brest.courses.testers.rest;

import com.epam.brest.courses.testers.UserNotFoundException;
import com.epam.brest.courses.testers.domain.User;
import com.epam.brest.courses.testers.view.UserView;
import com.epam.brest.courses.testers.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by xalf on 27.12.15.
 */
@RestController
public class UserRest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_PASSWORD = "!!!!!!!!!!#!!!!!!!!!!";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    @JsonView(UserView.Summary.class)
    @ResponseBody
    public List<User> getUsers() {
        LOGGER.debug("UserRest.getUsers()");
        List<User> users = userService.getUsers();
        for (User user : users) {
            user.setPassword(DEFAULT_PASSWORD);
            user.setPasswordConfirm(DEFAULT_PASSWORD);
        }
        return users;
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    @JsonView(UserView.Summary.class)
    @ResponseBody
    public User getUserByLogin(@PathVariable(value = "username") String username) throws UserNotFoundException {
        LOGGER.debug("UserRest.getUserByLogin()");
        try {
            List<User> users = userService.getUserByLogin(username);
            User user = users.get(0);
            user.setPassword(DEFAULT_PASSWORD);
            user.setPasswordConfirm(DEFAULT_PASSWORD);
            return user;
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException(username);
        }
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    @PreAuthorize("isFullyAuthenticated()")
    @ResponseBody
    public void addUser(@RequestBody String jsonUser) throws UserNotFoundException {
        LOGGER.debug("UserRest.addUser()");
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = mapper.readValue(jsonUser, User.class);
            userService.addUser(user);
        } catch (IOException e) {
            LOGGER.debug("UserRest.addUser()\n" + e.fillInStackTrace());
        }
    }

    @RequestMapping(value = "/user/put", method = RequestMethod.PUT)
    @PreAuthorize("isFullyAuthenticated()")
    @ResponseBody
    public void editUser(@RequestBody String jsonUser) throws UserNotFoundException {
        LOGGER.debug("UserRest.editUser()");
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = mapper.readValue(jsonUser, User.class);
            if (user.getPassword().equals(DEFAULT_PASSWORD)) {
                userService.updateUserWithoutPassword(user);
            } else {
                userService.updateUserWithPassword(user);
            }
        } catch (IOException e) {
            LOGGER.debug("UserRest.editUser()\n" + e.fillInStackTrace());
        }
    }



}
