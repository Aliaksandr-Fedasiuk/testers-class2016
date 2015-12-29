package com.epam.brest.courses.testers.service;

import com.epam.brest.courses.testers.domain.Action;

import java.util.List;

/**
 * Created by xalf on 29.12.15.
 */
public interface ActionService {

    List<Action> getActions(Integer requestId);

    Integer addAction(Action action);

    void deleteRequestActions(Integer requestId);

}
