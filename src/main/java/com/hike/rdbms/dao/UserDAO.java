package com.hike.rdbms.dao;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.hike.kafkaconsumer.dto.UserCount;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 *
 * @author Siddhartha Singh
 */
public interface UserDAO {

    @Metered
    @Timed
    @Mapper(UserCount.class)
    @SqlQuery("Select count(*) as user_count from user")
    UserCount getUsersCount();

    @Metered
    @Timed
    @SqlUpdate("Insert into user(age, name) values(:age, :name)")
    void createUser(@Bind("age") int age, @Bind("name") String name);
}
