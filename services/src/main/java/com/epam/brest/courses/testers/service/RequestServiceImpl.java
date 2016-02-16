package com.epam.brest.courses.testers.service;

import com.epam.brest.courses.testers.dao.ActionDao;
import com.epam.brest.courses.testers.dao.RequestDao;
import com.epam.brest.courses.testers.domain.Action;
import com.epam.brest.courses.testers.domain.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.brest.courses.testers.domain.Action.ActionType.NEW_REQ;

/**
 * Created by xalf on 29.12.15.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private ActionDao actionDao;

    @Override
    public List<Request> getRequests(Integer userId) {
        LOGGER.debug("RequestService.getRequests({})", userId);
        return requestDao.getRequests(userId);
    }

    @Override
    public List<Request> getRequest(Integer requestId) {
        LOGGER.debug("RequestService.getRequest({})", requestId);
        return requestDao.getRequest(requestId);
    }

    @Override
    public Integer addRequest(Request request) {
        LOGGER.debug("RequestService.addRequest()", request);
        Integer requestId = requestDao.addRequest(request);
        actionDao.addAction(new Action(NEW_REQ, requestId));
        return requestId;
    }

    @Override
    public void updateRequest(Request request) {
        LOGGER.debug("RequestService.updateRequest()", request);
        requestDao.updateRequest(request);
    }

    @Override
    public void deleteRequest(Integer requestId) {
        LOGGER.debug("RequestService.deleteRequest({})", requestId);
        requestDao.deleteRequest(requestId);
    }

    @Override
    public void deleteUserRequests(Integer userId) {
        LOGGER.debug("RequestService.deleteUserRequests({})", userId);
        requestDao.deleteUserRequests(userId);
    }
}
