package com.example.demo.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "errors")
public class ServiceError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Error message")
    @Column(name = "message")
    private String msg;

    @ApiModelProperty(notes = "Error created at")
    private Timestamp created;

    public ServiceError() {}

    public ServiceError(Long id, String message) {
        this.id = id;
        this.msg = message;
        this.created = new Timestamp(new Date().getTime());
    }

    public static ServiceError emailAlreadyExists() {
        return new ServiceError(0L, "Email already exists");
    }

    public static ServiceError invalidEmail() {
        return new ServiceError(0L, "Invalid email entered");
    }

    public static ServiceError profileNotFoundException() {
        return new ServiceError(0L, "Profile not found");
    }

    public static ServiceError serviceErrorNotFound() {
        return new ServiceError(0L, "There is no any errors in DB yet... Wait... For now you have created one :)");
    }

    public String getMsg() {
        return msg;
    }

    public Timestamp getCreated() {
        return created;
    }
}
