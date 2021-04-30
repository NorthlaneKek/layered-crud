package com.example.demo.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class EmailAlreadyExistsException extends Throwable {
}
