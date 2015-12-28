package com.epam.brest.courses.testers.dao;

import com.epam.brest.courses.testers.domain.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.brest.courses.testers.domain.Request.*;
import static java.lang.Integer.parseInt;

/**
 * Created by xalf on 29.12.15.
 */
@Repository
public class RequestDaoImpl implements RequestDao {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String USER_ID = "userId";
    public static final String STATUS = "status";
    public static final String REQUEST_ID = "requestId";
    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATED_DATE = "updatedDate";

    @Value("${request.select}")
    private String requestSelectSql;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public RequestDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Request> getRequests(Integer userId) {
        LOGGER.debug("getRequests({})", userId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        List<Map<String, Object>> res = namedParameterJdbcTemplate.queryForList(requestSelectSql, params);
        return rowMapper(res);
    }

    @Override
    public Integer addRequest(Request request) {
        LOGGER.debug("addRequest({})", request);
        return null;
    }

    @Override
    public void updateRequest(Request request) {
        LOGGER.debug("updateRequest({})", request);
    }

    @Override
    public void deleteRequest(Integer requestId) {
        LOGGER.debug("deleteRequest({})", requestId);
    }

    private List<Request> rowMapper(List<Map<String, Object>> values) {
        List<Request> requests = new ArrayList<Request>();
        for (Map row : values) {
            Request request = new Request(parseInt(String.valueOf(row.get(USER_ID))),
                    Status.valueOf(String.valueOf(row.get(STATUS))));
            request.setRequestId(parseInt(String.valueOf(row.get(REQUEST_ID))));
            request.setUpdatedDate(UserDaoImpl.parseTimestamp((Timestamp) row.get(CREATED_DATE)));
            request.setUpdatedDate(UserDaoImpl.parseTimestamp((Timestamp) row.get(UPDATED_DATE)));
            requests.add(request);
        }
        return requests;
    }

}
