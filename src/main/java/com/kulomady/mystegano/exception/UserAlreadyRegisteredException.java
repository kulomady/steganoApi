package com.kulomady.mystegano.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.sasl.AuthenticationException;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyRegisteredException extends AuthenticationException {

    public UserAlreadyRegisteredException(final String msg) {
        super(msg);
    }
}
