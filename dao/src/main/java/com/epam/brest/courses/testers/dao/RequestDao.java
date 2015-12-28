package com.epam.brest.courses.testers.dao;

import com.epam.brest.courses.testers.domain.Request;

import java.util.List;

/**
 * Created by xalf on 29.12.15.
 */
public interface RequestDao {

    List<Request> getRequests(Integer userId);

    Integer addRequest(Request request);

    void updateRequest(Request request);

    void deleteRequest(Integer requestId);

    void deleteUserRequests(Integer userId);

}
