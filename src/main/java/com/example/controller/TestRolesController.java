package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRolesController {

    @GetMapping(path = "/accessAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accessAdmin() {
        return "Hola, has accedido con ROLE de ADMIN";
    }

    @GetMapping(path = "/accessUser")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String accessUser() {
        return "Hola, has accedido con ROLE de USER";
    }

    @GetMapping(path = "/accessInvited")
    @PreAuthorize("hasRole('INVITED')")
    public String accessInvited() {
        return "Hola, has accedido con ROLE de INVITED";
    }
}
