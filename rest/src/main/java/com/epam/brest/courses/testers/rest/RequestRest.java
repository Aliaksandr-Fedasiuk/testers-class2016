package com.epam.brest.courses.testers.rest;

import com.epam.brest.courses.testers.domain.Request;
import com.epam.brest.courses.testers.domain.User;
import com.epam.brest.courses.testers.service.RequestService;
import com.epam.brest.courses.testers.view.RequestView;
import com.epam.brest.courses.testers.view.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xalf on 29.12.15.
 */
@RestController
public class RequestRest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private RequestService requestService;

    @RequestMapping(value = "/requests/{userId}", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    @JsonView(RequestView.Summary.class)
    @ResponseBody
    public List<Request> getRequests(@PathVariable Integer userId) {
        LOGGER.debug("RequestRest.getRequests({})", userId);
        return requestService.getRequests(userId);
    }

    @RequestMapping(value = "/request/{id}", method = RequestMethod.GET)
    @PreAuthorize("isFullyAuthenticated()")
    @JsonView(RequestView.Summary.class)
    @ResponseBody
    public Request getRequest(@PathVariable Integer id) {
        LOGGER.debug("RequestRest.getRequest({})", id);
        return requestService.getRequests(id).get(0);
    }

    @RequestMapping(value = "/request/delete/{requestId}", method = RequestMethod.GET)
    @JsonView(RequestView.Summary.class)
    @ResponseBody
    public void deleteRequest(@PathVariable Integer requestId) {
        LOGGER.debug("deleteRequest({})", requestId);
        requestService.deleteRequest(requestId);
    }

    @RequestMapping(value = "/requests/delete/{userId}", method = RequestMethod.GET)
    @JsonView(RequestView.Summary.class)
    @ResponseBody
    public void deleteRequests(@PathVariable Integer userId) {
        LOGGER.debug("deleteRequests({})", userId);
        requestService.deleteUserRequests(userId);
    }

}
