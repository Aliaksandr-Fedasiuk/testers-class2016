package com.epam.brest.courses.testers.domain;

import com.epam.brest.courses.testers.view.UserView;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by xalf on 25.12.15.
 */
public class User {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public enum Role { ROLE_ADMIN, ROLE_MANAGER, ROLE_SUBORDINATE }

    @JsonView(UserView.Summary.class)
    private Integer userId;

    @JsonView(UserView.Summary.class)
    private String name;

    @JsonView(UserView.Summary.class)
    private String login;

    @JsonView(UserView.Summary.class)
    private String password;

    @JsonView(UserView.Summary.class)
    private String passwordConfirm;

    @JsonView(UserView.Summary.class)
    private Double amount = 0d;

    @JsonView(UserView.Summary.class)
    private Integer managerId = 0;

    @JsonView(UserView.Summary.class)
    private final Role role;

    @JsonView(UserView.Summary.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private Date createdDate = new Date();

    @JsonView(UserView.Summary.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private Date updatedDate = new Date();

    public User() {
        role = Role.ROLE_SUBORDINATE;
    }

    public User(Role role) {
        this.role = role;
    }

    public User(Role role, String name, String login, String password) {
        this.role = role;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Role getRole() {
        return role;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(name, user.name) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(amount, user.amount) &&
                Objects.equals(managerId, user.managerId) &&
                role == user.role &&
                Objects.equals(createdDate, user.createdDate) &&
                Objects.equals(updatedDate, user.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, login, password, amount, managerId, role, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User: [");
        sb.append("userId=").append(userId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", managerId=").append(managerId);
        sb.append(", role=").append(role);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(']');
        return sb.toString();
    }
}
