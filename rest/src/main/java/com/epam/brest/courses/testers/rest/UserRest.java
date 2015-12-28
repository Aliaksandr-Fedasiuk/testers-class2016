package com.epam.brest.courses.testers.rest;

import com.epam.brest.courses.testers.domain.User;
import com.epam.brest.courses.testers.view.UserView;
import com.epam.brest.courses.testers.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xalf on 27.12.15.
 */
@RestController
@CrossOrigin
public class UserRest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @JsonView(UserView.Summary.class)
    @ResponseBody
    public List<User> getUsers() {
        LOGGER.debug("getUsers()");
        return userService.getUsers();
    }

}
