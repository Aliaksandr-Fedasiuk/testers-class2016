package com.epam.brest.courses.testers.rest;

import com.epam.brest.courses.testers.UserNotFoundException;
import com.epam.brest.courses.testers.domain.Request;
import com.epam.brest.courses.testers.domain.User;
import com.epam.brest.courses.testers.service.RequestService;
import com.epam.brest.courses.testers.view.RequestView;
import com.epam.brest.courses.testers.view.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        return requestService.getRequest(id).get(0);
    }

    @RequestMapping(value = "/request/add", method = RequestMethod.POST)
    @PreAuthorize("isFullyAuthenticated()")
    @JsonView(RequestView.Summary.class)
    @ResponseBody
    public ResponseEntity<String> addRequest(@RequestBody String jsonRequest) {
        LOGGER.debug("RequestRest.addRequest()");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Request request = mapper.readValue(jsonRequest, Request.class);
            if ((request.getDescription() == null) || (request.getDescription().length() == 0)) {
                return new ResponseEntity<String>("Request description should not be empty.", httpHeaders, HttpStatus.BAD_REQUEST);
            }
            try {
                requestService.addRequest(request);
            } catch (IllegalArgumentException ex) {
                return new ResponseEntity<String>(ex.getMessage(), httpHeaders, HttpStatus.BAD_REQUEST);
            }
        } catch (IOException ex) {
            LOGGER.error("RequestRest.addRequest()\n" + ex.fillInStackTrace());
            return new ResponseEntity<String>(ex.getMessage(), httpHeaders, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/request/put", method = RequestMethod.PUT)
    @PreAuthorize("isFullyAuthenticated()")
    @ResponseBody
    public ResponseEntity<String> editRequest(@RequestBody String jsonRequest) throws UserNotFoundException {
        LOGGER.debug("UserRest.editRequest()");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Request request = mapper.readValue(jsonRequest, Request.class);
            requestService.updateRequest(request);
            return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);
        } catch (IOException ex) {
            LOGGER.debug("UserRest.editRequest()\n" + ex.fillInStackTrace());
            return new ResponseEntity<String>(ex.getMessage(), httpHeaders, HttpStatus.BAD_REQUEST);
        }
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
