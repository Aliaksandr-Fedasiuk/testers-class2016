package com.epam.brest.courses.testers.dao;

import com.epam.brest.courses.testers.domain.Action;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * Created by xalf on 29.12.15.
 */
@Repository
public class ActionDaoImpl implements ActionDao {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String REQUEST_ID = "requestId";
    public static final String TYPE = "type";
    public static final String POINTS = "points";
    public static final String CREATED_DATE = "createdDate";

    @Value("${action.select}")
    private String actionSelectSql;

    @Value("${action.insertAction}")
    private String insertActionSql;

    @Value("${action.deleteActionByRequestId}")
    private String actionDeleteActionByRequestIdSql;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ActionDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Action> getActions(Integer requestId) {
        LOGGER.debug("getActions({})", requestId);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("requestId", requestId);

        List<Map<String, Object>> res = namedParameterJdbcTemplate.queryForList(actionSelectSql, params);
        return rowMapper(res);
    }

    @Override
    public Integer addAction(Action action) {
        LOGGER.debug("addAction({})", action);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(REQUEST_ID, action.getRequestId());
        params.addValue(TYPE, action.getType().toString());
        params.addValue(POINTS, action.getType().getPoints());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertActionSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void deleteRequestActions(Integer requestId) {
        LOGGER.debug("deleteRequestActions({})", requestId);
        //TODO
    }

    private List<Action> rowMapper(List<Map<String, Object>> values) {
        List<Action> actions = new ArrayList<Action>();
        for (Map row : values) {
            Action action = new Action(Action.ActionType.valueOf(String.valueOf(row.get(TYPE))));
            action.setRequestId(parseInt(String.valueOf(row.get(REQUEST_ID))));
            action.setCreatedDate(UserDaoImpl.parseTimestamp((Timestamp) row.get(CREATED_DATE)));
            actions.add(action);
        }
        return actions;
    }

}
