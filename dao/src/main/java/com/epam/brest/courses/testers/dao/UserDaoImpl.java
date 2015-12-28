package com.epam.brest.courses.testers.dao;

import com.epam.brest.courses.testers.domain.User;
import com.epam.brest.courses.testers.domain.User.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

/**
 * Created by xalf on 25.12.15.
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public static final String USER_ID = "userId";
    public static final String NAME = "name";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String AMOUNT = "amount";
    public static final String MANAGER_ID = "managerId";
    public static final String ROLE = "role";
    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATED_DATE = "updatedDate";

    @Value("${user.select}")
    private String userSelectSql;

    @Value("${user.selectById}")
    private String userSelectByIdSql;

    @Value("${user.selectByLogin}")
    private String userSelectByLoginSql;

    @Value("${user.countUsers}")
    private String countUserSql;

    @Value("${user.totalUsersCount}")
    private String totalUsersCountSql;

    @Value("${user.insertUser}")
    private String insertUserSql;

    @Value("${user.updateUser}")
    private String updateUserSql;

    @Value("${user.deleteUser}")
    private String deleteUserSql;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getUsers() {
        LOGGER.debug("getUsers()");
        return rowMapper(jdbcTemplate.queryForList(userSelectSql));
    }

    @Override
    public List<User> getUserById(Integer userId) {
        LOGGER.debug("getUserById({})", userId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        res.add(namedParameterJdbcTemplate.queryForMap(userSelectByIdSql, params));
        return rowMapper(res);
    }

    @Override
    public List<User> getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})", login);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(LOGIN, login);

        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        res.add(namedParameterJdbcTemplate.queryForMap(userSelectByLoginSql, params));
        return rowMapper(res);
    }

    @Override
    public Integer getTotalUsersCount() {
        LOGGER.debug("getTotalUsersCount({})");
        return jdbcTemplate.queryForObject(totalUsersCountSql, Integer.class);
    }

    @Override
    public Integer addUser(User user) {
        LOGGER.debug("addUser({})", user);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, user.getName());
        params.addValue(LOGIN, user.getLogin());
        params.addValue(PASSWORD, user.getPassword());
        params.addValue(AMOUNT, user.getAmount());
        params.addValue(MANAGER_ID, user.getManagerId());
        params.addValue(ROLE, user.getRole().toString());

        LocalDateTime ldt = LocalDateTime.ofInstant(user.getUpdatedDate().toInstant(), ZoneId.systemDefault());
        params.addValue(UPDATED_DATE, ldt.format(formatter));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertUserSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser({})", user.getLogin());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(USER_ID, user.getUserId());
        params.addValue(NAME, user.getName());
        params.addValue(PASSWORD, user.getPassword());
        params.addValue(AMOUNT, user.getAmount());
        params.addValue(MANAGER_ID, user.getManagerId());
        params.addValue(ROLE, user.getRole().toString());

        LocalDateTime ldt = LocalDateTime.ofInstant(user.getUpdatedDate().toInstant(), ZoneId.systemDefault());
        params.addValue(UPDATED_DATE, ldt.format(formatter));

        jdbcTemplate.update(updateUserSql, params);
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.debug("deleteUser({})", userId);
    }

    private List<User> rowMapper(List<Map<String, Object>> values) {
        List<User> users = new ArrayList<User>();
        for (Map row : values) {
            User user = new User(Role.valueOf(valueOf(row.get(ROLE))));
            user.setUserId(parseInt(valueOf(row.get(USER_ID))));
            user.setName(valueOf(row.get(NAME)));
            user.setLogin(valueOf(row.get(LOGIN)));
            user.setPassword(valueOf(row.get(PASSWORD)));
            user.setAmount(parseDouble(valueOf(row.get(AMOUNT))));
            user.setManagerId(parseInt(valueOf(row.get(MANAGER_ID))));
            user.setUpdatedDate(parseTimestamp((Timestamp) row.get(CREATED_DATE)));
            user.setUpdatedDate(parseTimestamp((Timestamp) row.get(UPDATED_DATE)));
            users.add(user);
        }
        return users;
    }

    public static Date parseTimestamp(Timestamp time) {
        LocalDateTime ldt = (time).toLocalDateTime().truncatedTo(ChronoUnit.SECONDS);
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

}
