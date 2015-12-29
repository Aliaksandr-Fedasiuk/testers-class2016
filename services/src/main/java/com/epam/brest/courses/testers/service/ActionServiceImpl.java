package com.epam.brest.courses.testers.service;

import com.epam.brest.courses.testers.dao.ActionDao;
import com.epam.brest.courses.testers.domain.Action;
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
public class ActionServiceImpl implements ActionService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ActionDao actionDao;

    @Override
    public List<Action> getActions(Integer requestId) {
        LOGGER.debug("getActions({})", requestId);
        return actionDao.getActions(requestId);
    }

    @Override
    public Integer addAction(Action action) {
        LOGGER.debug("addAction({})", action);
        return actionDao.addAction(action);
    }

    @Override
    public void deleteRequestActions(Integer requestId) {
        LOGGER.debug("deleteRequestActions({})", requestId);
        actionDao.deleteRequestActions(requestId);
    }
}
