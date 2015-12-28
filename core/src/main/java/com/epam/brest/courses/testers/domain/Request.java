package com.epam.brest.courses.testers.domain;

/**
 * Created by xalf on 25.12.15.
 */
public class Request {

    enum Status { OPEN, IN_PROGRESS, CANCEL, CLOSED }

    private Integer requestId;

    private Integer userId;

    private Status status;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
