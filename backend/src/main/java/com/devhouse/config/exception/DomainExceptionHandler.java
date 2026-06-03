package com.devhouse.config.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {DomainException.class, ExceptionHandler.class})
public class DomainExceptionHandler  implements ExceptionHandler<DomainException, HttpResponse<Object>> {

    @Override
    public HttpResponse<Object> handle(HttpRequest request, DomainException exception) {
        return HttpResponse.unprocessableEntity().body(exception.getMessage());
    }
}
