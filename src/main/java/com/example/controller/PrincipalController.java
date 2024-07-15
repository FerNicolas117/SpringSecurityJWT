package com.example.controller;

import com.example.controller.request.CreateUserDTO;
import com.example.model.ERole;
import com.example.model.RoleEntity;
import com.example.model.UserEntity;
import com.example.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello World Not Secured!";
    }

    @GetMapping(path = "/helloSecured")
    public String helloSecured() {
        return "Hello World Secured!";
    }

    /**
     * endpoint encargado de registrar un usuario.
     * Se recibe el usuario por medio de la petición HTTP -> RequestBody (la info va en el cuerpo del request).
     */
    @PostMapping(path = "/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping(path = "/deleteUser")
    public String deleteUser(@RequestParam String id) {
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha eliminado el User antes registrado con el id: ".concat(id);
    }
}
