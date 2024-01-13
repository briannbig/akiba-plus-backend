package com.github.briannbig.akiba.api.validator;

import com.github.briannbig.akiba.api.request.UserCreateRequest;
import com.github.briannbig.akiba.exceptions.RequestValidationException;

import static com.github.briannbig.akiba.util.StringUtil.isEmptyString;


public class UserCreateRequestValidator implements RequestValidator<UserCreateRequest> {
    @Override
    public UserCreateRequest validate(UserCreateRequest request) throws RequestValidationException {
        if (isEmptyString(request.email())) {
            throw new RequestValidationException("Email cannot be empty");
        }
        if (isEmptyString(request.firstName())) {
            throw new RequestValidationException("First name cannot be empty");
        }
        if (isEmptyString(request.lastName())) {
            throw new RequestValidationException("Last name cannot be empty");
        }
        if (isEmptyString(request.nationalID())) {
            throw new RequestValidationException("National ID Cannot be empty");
        }
        if (isEmptyString(request.telephone())) {
            throw new RequestValidationException("Telephone number cannot be used");
        }
        if (isEmptyString(request.password())) {
            throw new RequestValidationException("Password cannot be empty");
        }
        if (isEmptyString(request.passwordConfirm())) {
            throw new RequestValidationException("Password reconfirmations should not be empty");
        }
        if (!request.password().equals(request.passwordConfirm())) {
            throw new RequestValidationException("Passwords does not match");
        }
        return request;
    }
}
