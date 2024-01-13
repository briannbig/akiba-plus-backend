package com.github.briannbig.akiba.api.validator;

import com.github.briannbig.akiba.exceptions.RequestValidationException;

@FunctionalInterface
public interface RequestValidator<T> {
 T validate(T request) throws RequestValidationException;
}
