package com.example.demo.presentation.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExitController {

    @Value("${server.port}")
    private String servicePort;

    @GetMapping("/exit")
    public ResponseEntity<Object> exit() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", String.format("http://localhost:%s/exit-success", servicePort));
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(headers)
                .body("Redirected");
    }

    @GetMapping("/exit-success")
    public ResponseEntity<Object> onExitSuccess() {
        return new ResponseEntity<>("Приложение закрыто", HttpStatus.OK);
    }

}
