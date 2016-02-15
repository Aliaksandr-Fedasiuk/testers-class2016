package com.epam.brest.courses.testers.domain;

import com.epam.brest.courses.testers.view.ActionView;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

/**
 * Created by xalf on 25.12.15.
 */
public class Action {

    @JsonView(ActionView.Summary.class)
    private Integer actionId;

    @JsonView(ActionView.Summary.class)
    private Integer requestId;

    @JsonView(ActionView.Summary.class)
    private final ActionType type;

    @JsonView(ActionView.Summary.class)
    private final Double points;

    @JsonView(ActionView.Summary.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private Date createdDate = new Date();

    public Action(ActionType type) {
        this.type = type;
        this.points = type.points;
    }

    public Action(ActionType type, Integer requestId) {
        this.type = type;
        this.points = type.points;
        this.requestId = requestId;
    }


    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public ActionType getType() {
        return type;
    }

    public Double getPoints() {
        return points;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Action: [");
        sb.append("actionId=").append(actionId);
        sb.append(", requestId=").append(requestId);
        sb.append(", type=").append(type);
        sb.append(", points=").append(points);
        sb.append(", createdDate=").append(createdDate);
        sb.append(']');
        return sb.toString();
    }

    public enum ActionType {

        NEW_REQ(0),
        IMPORT_REPORT(2),
        CANCEL_REQ(-2),
        IMPORT_IN_PROGRESS_REQ(0.5),
        DELETE_IN_PROGRESS_REQ(-0.5),
        IMPORT_CLOSED_REQ(1),
        DELETE_CLOSED_REQ(-1);

        private double points;

        ActionType(double points) {
            this.points = points;
        }

        public double getPoints() {
            return points;
        }
    }

}
