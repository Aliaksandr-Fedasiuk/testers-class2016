package com.epam.brest.courses.testers.rest;

import com.epam.brest.courses.testers.domain.Action;
import com.epam.brest.courses.testers.service.ActionService;
import com.epam.brest.courses.testers.view.ActionView;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xalf on 29.12.15.
 */
@RestController
@CrossOrigin
public class ActionRest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ActionService actionService;

    @RequestMapping(value = "/actions/{requestId}", method = RequestMethod.GET)
    @JsonView(ActionView.Summary.class)
    @ResponseBody
    public List<Action> getActions(@PathVariable Integer requestId) {
        LOGGER.debug("getActions({})", requestId);
        return actionService.getActions(requestId);
    }

}
