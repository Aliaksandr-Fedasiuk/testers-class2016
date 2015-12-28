package com.epam.brest.courses.testers.service;

import com.epam.brest.courses.testers.domain.Request;

import java.util.List;

/**
 * Created by xalf on 29.12.15.
 */
public interface RequestService {

    List<Request> getRequests(Integer userId);

    void deleteRequest(Integer requestId);

    void deleteUserRequests(Integer userId);

}
