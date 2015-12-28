package com.epam.brest.courses.testers.service;

import com.epam.brest.courses.testers.dao.RequestDao;
import com.epam.brest.courses.testers.domain.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xalf on 29.12.15.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private RequestDao requestDao;

    @Override
    public List<Request> getRequests(Integer userId) {
        LOGGER.debug("getRequests({})", userId);
        return requestDao.getRequests(userId);
    }

}
