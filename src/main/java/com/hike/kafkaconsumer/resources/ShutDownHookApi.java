package com.hike.kafkaconsumer.resources;

import com.codahale.metrics.annotation.Timed;
import com.hike.kafkaconsumer.enums.SubjectEnum;
import com.hike.kafkaconsumer.subject.SubjectFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import java.util.Optional;
import javax.ws.rs.*;
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
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "ShutDownHookApi")
public class ShutDownHookApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShutDownHookApi.class);
    /**
     *
     * @param shutdown : Boolean true / false
     * @return
     */
    @GET
    @Timed
    @ApiOperation(value = "Shutdown service and consumers",
        notes = "Shutdown the consumers",
        response = Response.class)
    @Path("/shut")
    @Produces(MediaType.APPLICATION_JSON)
    public Response shutDownConsumers(@QueryParam("shutdown") Optional<Boolean> shutdown) {
        if (shutdown.get() != null) {
            if (Objects.equals(shutdown.get(), Boolean.TRUE)) {
                SubjectFactory.getSubject(SubjectEnum.KAFKA_DW_CONSUMER).changeConsumerState(true);
                System.exit(0);
            }
        }
        return Response.ok().build();
    }
}
