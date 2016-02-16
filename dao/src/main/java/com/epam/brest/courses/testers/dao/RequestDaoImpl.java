package com.epam.brest.courses.testers.dao;

import com.epam.brest.courses.testers.domain.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public static final String DESCRIPTION = "description";
    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATED_DATE = "updatedDate";

    @Value("${requests.select}")
    private String requestsSelectSql;

    @Value("${request.select}")
    private String requestSelectSql;

    @Value("${request.insertRequest}")
    private String insertRequestSql;

    @Value("${request.updateRequest}")
    private String updateRequestSql;

    @Value("${request.deleteRequestById}")
    private String deleteRequestByIdSql;

    @Value("${request.deleteRequestByUserId}")
    private String deleteRequestByUserIdSql;

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

        List<Map<String, Object>> res = namedParameterJdbcTemplate.queryForList(requestsSelectSql, params);
        return rowMapper(res);
    }

    @Override
    public List<Request> getRequest(Integer requestId) {
        LOGGER.debug("getRequest({})", requestId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("requestId", requestId);

        List<Map<String, Object>> res = namedParameterJdbcTemplate.queryForList(requestSelectSql, params);
        return rowMapper(res);
    }

    @Override
    public Integer addRequest(Request request) {
        LOGGER.debug("addRequest({})", request);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(USER_ID, request.getUserId());
        params.addValue(STATUS, request.getStatus().toString());

        LocalDateTime ldt = LocalDateTime.ofInstant(request.getUpdatedDate().toInstant(), ZoneId.systemDefault());
        params.addValue(UPDATED_DATE, ldt.format(UserDaoImpl.formatter));
        params.addValue(DESCRIPTION, request.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertRequestSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateRequest(Request request) {
        LOGGER.debug("updateRequestSql({})", request);
        LocalDateTime ldt = LocalDateTime.ofInstant(request.getUpdatedDate().toInstant(), ZoneId.systemDefault());

        jdbcTemplate.update(updateRequestSql, request.getUserId(), request.getStatus().toString(),
                request.getDescription(), ldt.format(UserDaoImpl.formatter), request.getRequestId());
    }

    @Override
    public void deleteRequest(Integer requestId) {
        LOGGER.debug("deleteRequest({})", requestId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("requestId", requestId);
        namedParameterJdbcTemplate.execute(deleteRequestByIdSql, params, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                return preparedStatement.executeUpdate();
            }
        });
    }

    @Override
    public void deleteUserRequests(Integer userId) {
        LOGGER.debug("deleteUserRequests({})", userId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        namedParameterJdbcTemplate.execute(deleteRequestByUserIdSql, params, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                return preparedStatement.executeUpdate();
            }
        });
    }

    private List<Request> rowMapper(List<Map<String, Object>> values) {
        List<Request> requests = new ArrayList<Request>();
        for (Map row : values) {
            Request request = new Request(parseInt(String.valueOf(row.get(USER_ID))),
                    Status.valueOf(String.valueOf(row.get(STATUS))));
            request.setRequestId(parseInt(String.valueOf(row.get(REQUEST_ID))));
            request.setDescription(String.valueOf(row.get(DESCRIPTION)));
            request.setCreatedDate(UserDaoImpl.parseTimestamp((Timestamp) row.get(CREATED_DATE)));
            request.setUpdatedDate(UserDaoImpl.parseTimestamp((Timestamp) row.get(UPDATED_DATE)));
            requests.add(request);
        }
        return requests;
    }

}
