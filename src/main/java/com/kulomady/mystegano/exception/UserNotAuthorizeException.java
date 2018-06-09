package com.kulomady.mystegano.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.sasl.AuthenticationException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizeException extends AuthenticationException {

    public UserNotAuthorizeException(final String msg) {
        super(msg);
    }
}
