package com.hike.kafkaconsumer.resources;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.hike.kafkaconsumer.dto.UserCount;
import com.hike.kafkaconsumer.dto.UserCreation;
import com.hike.rdbms.dao.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author siddharthasingh
 */
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "UserAPI")
public class UserAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAPI.class);

    private final UserDAO userDAO;

    public UserAPI(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     *
     * @return
     */
    @GET
    @ApiOperation(value = "Total users",
        notes = "Gets count of all users",
        response = UserCount.class)
    @Timed(absolute = true, name = "GET_User_Count_Timer")
    @Metered(absolute = true, name = "GET_User_Count_Metered")
    @Path("/all_users_count")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public UserCount getAllUsersCount(@Context final HttpServletRequest request) {
        UserCount usersCount = userDAO.getUsersCount();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request : '{}'", request.toString());
        }
        return usersCount;
    }

    /**
     *
     * @param request
     * @param age
     * @param name
     * @param userCreation
     * @return
     */
    @POST
    @ApiOperation(value = "User creation",
        notes = "This API creates user with age and name : passed in URI as well as in body",
        response = Response.class)
    @Path("/create_user/age/{age}/name/{name}")
    @Timed(absolute = true, name = "POST_Create_User_Timer")
    @Metered(absolute = true, name = "POST_Create_User_Metered")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Context final HttpServletRequest request,
        @PathParam("age") Integer age,
        @PathParam("name") String name,
        @Valid UserCreation userCreation) {
        if (!name.trim().isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Request : '{}'", request.toString());
            }
            userDAO.createUser(userCreation.getAge(), userCreation.getName());
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

}
