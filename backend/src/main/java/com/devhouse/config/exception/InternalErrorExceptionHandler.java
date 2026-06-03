package com.devhouse.config.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces
@Singleton
@Requires(classes = {Exception.class, ExceptionHandler.class})
public class InternalErrorExceptionHandler implements ExceptionHandler<Exception, HttpResponse<Object>> {
    Logger log = LoggerFactory.getLogger(InternalErrorExceptionHandler.class);
    @Override
    public HttpResponse<Object> handle(HttpRequest request, Exception exception) {
        log.error(exception.getMessage(), exception);
        return HttpResponse.serverError().body("Internal Server Error");
    }
}
