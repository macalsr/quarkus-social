package io.github.mariacsr.quarkussocial.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    private String message;
    private Collection<FieldError> errors;

    public ResponseError(String message, Collection<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public static <T>ResponseError createFromValidation(Set<ConstraintViolation<T>> constraintViolation) {
        List<FieldError> errors = constraintViolation
                .stream()
                .map(cv ->
                        new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .toList();

        String message = "Validation failed";
        return new ResponseError(message, errors);
    }

    public Response withStatusCode(int code) {
        return Response.status(code).entity(this).build();
    }
}
