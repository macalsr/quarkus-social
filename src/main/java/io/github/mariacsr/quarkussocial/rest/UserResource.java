package io.github.mariacsr.quarkussocial.rest;

import io.github.mariacsr.quarkussocial.domain.model.User;
import io.github.mariacsr.quarkussocial.rest.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {

        User user = User.builder()
                .age(userRequest.age())
                .name(userRequest.name())
                .build();

        user.persist();

        return Response.ok(user).build();
    }

    @GET
    @Transactional
    public Response listAllUsers() {
        PanacheQuery<PanacheEntityBase> query = User.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
      User user = User.findById(id);
      if(user != null) {
          user.delete();
          return Response.ok().build();
      }
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest) {
        User user = User.findById(id);
        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
            user.setName(userRequest.name());
            user.setAge(userRequest.age());
        return Response.ok(user).build();                       
    }
}
