package com.epam.brest.courses.testers.rest;

import com.epam.brest.courses.testers.UserNotFoundException;
import com.epam.brest.courses.testers.domain.User;
import com.epam.brest.courses.testers.service.UserService;
import com.epam.brest.courses.testers.view.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.util.Assert.*;

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

    @RequestMapping(value = "/managers/{role}", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    @JsonView(UserView.Summary.class)
    @ResponseBody
    public List<User> getManagers(@PathVariable(value = "role") String role) {
        LOGGER.debug("UserRest.getManagers()");
        List<User> users = userService.getManagers(role);
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

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addUser(@RequestBody String jsonUser) throws UserNotFoundException {
        LOGGER.debug("UserRest.addUser()");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = mapper.readValue(jsonUser, User.class);
            if ((user.getLogin() == null) || (user.getLogin().length() == 0)) {
                return getErrorMessage("User login should not be empty.", httpHeaders);
            }
            try {
                if (userService.getUserByLogin(user.getLogin()).size() > 0) {
                    return getErrorMessage("User login should be unique.", httpHeaders);
                }

                if (!user.getPassword().equals(user.getPasswordConfirm())) {
                    return getErrorMessage("Password does not match the confirm password.", httpHeaders);
                }
            } catch (EmptyResultDataAccessException ex) {
                userService.addUser(user);
            } catch (IllegalArgumentException ex) {
                return getErrorMessage(ex.getMessage(), httpHeaders);
            }
        } catch (IOException ex) {
            LOGGER.error("UserRest.addUser()\n" + ex.fillInStackTrace());
            return getErrorMessage(ex.getMessage(), httpHeaders);
        }
        return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);
    }

    private ResponseEntity<String> getErrorMessage(String message, HttpHeaders httpHeaders) {
        return new ResponseEntity<String>(message, httpHeaders, HttpStatus.BAD_REQUEST);
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

    @RequestMapping(value = "/user/delete/{userId}", method = RequestMethod.DELETE)
    @PreAuthorize("isFullyAuthenticated()")
    @ResponseBody
    public void deleteUser(@PathVariable(value = "userId") Integer userId) throws UserNotFoundException {
        LOGGER.debug("UserRest.deleteUser()");
        List<User> users = userService.getUserById(userId);
        notEmpty(users);
        isTrue(!users.get(0).getRole().equals(User.Role.ROLE_ADMIN));
        userService.deleteUser(userId);
    }

}
