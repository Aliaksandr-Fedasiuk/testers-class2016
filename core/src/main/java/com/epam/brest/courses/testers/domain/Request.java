package com.epam.brest.courses.testers.domain;

import com.epam.brest.courses.testers.view.RequestView;
import com.epam.brest.courses.testers.view.UserView;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

/**
 * Created by xalf on 25.12.15.
 */
public class Request {

    public enum Status { OPEN, IN_PROGRESS, CANCEL, CLOSED }

    @JsonView(RequestView.Summary.class)
    private Integer requestId;

    @JsonView(RequestView.Summary.class)
    private Integer userId;

    @JsonView(RequestView.Summary.class)
    private Status status = Status.OPEN;

    @JsonView(RequestView.Summary.class)
    private String description;

    @JsonView(UserView.Summary.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private Date createdDate = new Date();

    @JsonView(UserView.Summary.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private Date updatedDate = new Date();

    public Request() {
    }

    public Request(Integer userId, Status status) {
        this.userId = userId;
        this.status = status;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Request: [");
        sb.append("requestId=").append(requestId);
        sb.append(", userId=").append(userId);
        sb.append(", status=").append(status);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(']');
        return sb.toString();
    }
}
