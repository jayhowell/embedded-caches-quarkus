package org.acme;

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api")
public class ScoreResource {

    @Inject
    ScoreService scoreService;

    @POST
    public Response create(@Valid Score item) {
        scoreService.save(item);
        return Response.status(Status.CREATED).entity(item).build();
    }

    @GET
    @Path("/{id}")
    public Object getOne(@PathParam("id") String id) {
        Object entity = scoreService.findById(id);
        if (entity == null) {
            throw new WebApplicationException("ScoreCard with id of " + id + " does not exist.", Status.NOT_FOUND);
        }
        return entity;
    }

    @GET
    public List<Score> getAll() {
        return scoreService.getAll();
    }

    @GET
    @Path("/lambda")
    public List<Score> getLambda() {
        
        return scoreService.getLambdaList();
    }

    @PATCH
    @Path("/{id}")
    public Response update(@Valid Score card, @PathParam("id") Long id) {
        scoreService.save(card);
        return Response.status(Status.CREATED).entity(card).build();

    }

    @OPTIONS
    public Response opt() {
        return Response.ok().build();
    }

    @GET
    @Path("/hello")
    public String hello() {
        return "hello";
    }

    @DELETE
    @Transactional
    public Response delete() {
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Long id) {
        return Response.noContent().build();
    }

}