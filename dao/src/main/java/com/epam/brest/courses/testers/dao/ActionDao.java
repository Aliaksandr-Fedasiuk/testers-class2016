package com.epam.brest.courses.testers.dao;

import com.epam.brest.courses.testers.domain.Action;

import java.util.List;

/**
 * Created by xalf on 29.12.15.
 */
public interface ActionDao {

    List<Action> getActions(Integer requestId);

    Integer addAction(Action action);

    void deleteRequestActions(Integer requestId);

}
