package com.epam.brest.courses.testers.domain;

import java.util.Date;

/**
 * Created by xalf on 25.12.15.
 */
public class Action {

    private Integer actionId;

    private Integer userId;

    private final ActionName name;

    private Date createdDate = new Date();

    public Action(ActionName name) {
        this.name = name;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public ActionName getName() {
        return name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    enum ActionName {

        IMPORT_REPORT(2),
        CANCEL_REQ(-2),
        IMPORT_IN_PROGRESS_REQ(0.5),
        DELETE_IN_PROGRESS_REQ(-0.5),
        IMPORT_CLOSED_REQ(1),
        DELETE_CLOSED_REQ(-1);

        private double points;

        ActionName(double points) {
            this.points = points;
        }
    }

}
