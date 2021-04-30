package com.example.demo.presentation.controller;

import com.example.demo.domain.ServiceError;
import com.example.demo.domain.exception.ServiceErrorNotFoundException;
import com.example.demo.domain.repository.ServiceErrorRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
@Api(value = "/error", description = "Service error controller")
public class ServiceErrorController {

    private final ServiceErrorRepository repository;

    public ServiceErrorController(ServiceErrorRepository repository) {
        this.repository = repository;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header"),
    })
    @GetMapping("/last")
    @ApiOperation(value = "Returns last error that was occurred")
    public ServiceError last() throws ServiceErrorNotFoundException {
        return repository.findFirstByOrderByIdDesc().orElseThrow(ServiceErrorNotFoundException::new);
    }
}
