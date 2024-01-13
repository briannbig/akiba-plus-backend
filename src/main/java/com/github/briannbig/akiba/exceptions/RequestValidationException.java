package com.github.briannbig.akiba.exceptions;

import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestValidationException extends ValidationException {

    public RequestValidationException(String message) {
        super(message);
        Logger log = LoggerFactory.getLogger(getClass());
        log.debug(message);
    }
}
