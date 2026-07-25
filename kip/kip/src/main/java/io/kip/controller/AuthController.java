package io.kip.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kip.model.AuthRequest;
import io.kip.service.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request){
        return authService.register(request.getEmail(), request.getPassword(), request.getFullName());
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request){
        return authService.login(request.getEmail(), request.getPassword());
    }

    @GetMapping("/test")
    public String test() {
        return "Auth controller is working";
}
  
}
