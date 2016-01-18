package com.epam.brest.courses.testers.rest;

import com.epam.brest.courses.testers.security.AuthenticationService;
import com.epam.brest.courses.testers.security.TokenInfo;
import com.epam.brest.courses.testers.security.TokenManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

/**
 * Created by xalf on 19.01.16.
 */
@RestController
public class AuthRest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(value = "/login", produces = "text/plain", method = RequestMethod.POST)
    public void login() {
        LOGGER.debug("AuthRest.login()");
    }

    @Secured("ADMIN")
    @RequestMapping("/admin")
    public String admin() {
        LOGGER.debug("AuthRest.admin()");
        return "Cool, you're admin!";
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping("/secure/service1")
    public String service1() {
        LOGGER.debug("AuthRest.service1()");
        return "Any authorized user should have access.";
    }

    @RequestMapping("/secure/mytokens")
    public Collection<TokenInfo> myTokens() {
        LOGGER.debug("AuthRest.myTokens()");
        UserDetails currentUser = authenticationService.currentUser();
        return tokenManager.getUserTokens(currentUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/secure/special")
    public String special() {
        LOGGER.debug("AuthRest.special");
        return "MANAGER users should have access.";
    }

    @RequestMapping("/secure/users")
    public Map<String, UserDetails> users() {
        LOGGER.debug("AuthRest.users");
        return tokenManager.getValidUsers();
    }

}
