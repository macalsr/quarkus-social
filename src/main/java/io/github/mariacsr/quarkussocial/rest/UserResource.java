package io.github.mariacsr.quarkussocial.rest;

import io.github.mariacsr.quarkussocial.domain.model.User;
import io.github.mariacsr.quarkussocial.domain.reposity.UserRepository;
import io.github.mariacsr.quarkussocial.rest.dto.CreateUserRequest;
import io.github.mariacsr.quarkussocial.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/users")
public class UserResource {

    private final UserRepository repository;

    private final Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {

        Set<ConstraintViolation<CreateUserRequest>> constraintViolations = validator.validate(userRequest);

        if(!constraintViolations.isEmpty()){
            return ResponseError.createFromValidation(constraintViolations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        User user = User.builder()
                .age(userRequest.age())
                .name(userRequest.name())
                .build();

        repository.persist(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @GET
    @Transactional
    public Response listAllUsers() {
        PanacheQuery<User> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
      User user = repository.findById(id);
      if(user != null) {
          repository.delete(user);
          return Response.ok().build();
      }
      return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest) {
        Set<ConstraintViolation<CreateUserRequest>> constraintViolations = validator.validate(userRequest);
        if(!constraintViolations.isEmpty()){
            return ResponseError.createFromValidation(constraintViolations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        User user = repository.findById(id);
        if(user == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
            user.setName(userRequest.name());
            user.setAge(userRequest.age());
        return Response.ok(user).build();                       
    }
}
