package com.hike.kafkaconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 *
 * @author siddharthasingh
 */

public class UserCount implements ResultSetMapper<UserCount> {

    @Valid
    @NotNull
    @JsonProperty("count")
    private Long count;

    public UserCount() {

    }

    @Override
    public UserCount map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        Long count = rs.getLong("user_count");
        UserCount userCount = new UserCount();
        userCount.count = count;
        return userCount;
    }

}
