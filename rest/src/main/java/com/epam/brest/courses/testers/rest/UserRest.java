package com.epam.brest.courses.testers.rest;

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
    private AuthenticationService authenticationService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @JsonView(UserView.Summary.class)
    @ResponseBody
    public List<User> getUsers() {
        LOGGER.debug("*** UserRest.getUsers()");
        return userService.getUsers();
    }

    @RequestMapping(value = "/login", produces = "text/plain")
    public String login() {
        LOGGER.debug("*** UserRest.login()");
        return "There is nothing special about login here, just use Authorization: Basic, or provide secure token.\n" +
                "For testing purposes you can use headers X-Username and X-Password instead of HTTP Basic Access Authentication.\n" +
                "THIS APPLIES TO ANY REQUEST protected by Spring Security (see filter-mapping).\n\n" +
                "Realize, please, that Authorization request (or the one with testing X-headers) must be POST, otherwise they are ignored.";
    }

    //@RolesAllowed("ADMIN")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/admin")
    public String admin() {
        LOGGER.debug("*** UserRest.admin()");
        return "Cool, you're admin!";
    }

    @RequestMapping("/secure/service1")
    public String service1() {
        LOGGER.debug("*** UserRest.service1()");
        return "Any authorized user should have access.";
    }

    @RequestMapping("/secure/mytokens")
    public Collection<TokenInfo> myTokens() {
        LOGGER.debug("*** UserRest.myTokens()");
        UserDetails currentUser = authenticationService.currentUser();
        return tokenManager.getUserTokens(currentUser);
    }

    //@Secured("ADMIN")
    @PreAuthorize("@userService.canAccessUser(principal, #username)")
    @RequestMapping("/secure/special")
    public String special() {
        LOGGER.debug("*** UserRest.special");
        return "MANAGER users should have access.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/secure/users")
    public Map<String, UserDetails> users() {
        LOGGER.debug("*** UserRest.users");
        return tokenManager.getValidUsers();
    }

}
