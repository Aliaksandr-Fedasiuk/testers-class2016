package com.epam.brest.courses.testers.rest;

import com.epam.brest.courses.testers.UserNotFoundException;
import com.epam.brest.courses.testers.domain.User;
import com.epam.brest.courses.testers.security.AuthenticationService;
import com.epam.brest.courses.testers.security.TokenInfo;
import com.epam.brest.courses.testers.security.TokenManager;
import com.epam.brest.courses.testers.view.UserView;
import com.epam.brest.courses.testers.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by xalf on 27.12.15.
 */
@RestController
public class UserRest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    @JsonView(UserView.Summary.class)
    @ResponseBody
    public List<User> getUsers() {
        LOGGER.debug("UserRest.getUsers()");
        return userService.getUsers();
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    @JsonView(UserView.Summary.class)
    @ResponseBody
    public User getUserByLogin(@PathVariable(value = "username") String username) throws UserNotFoundException {
        LOGGER.debug("UserRest.getUserByLogin()");
        try {
            List<User> users = userService.getUserByLogin(username);
            return users.get(0);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException(username);
        }
    }

}
