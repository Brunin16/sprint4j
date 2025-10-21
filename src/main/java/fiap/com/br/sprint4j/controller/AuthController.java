package fiap.com.br.sprint4j.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fiap.com.br.sprint4j.dto.LoginRequest;
import fiap.com.br.sprint4j.dto.LoginResponse;
import fiap.com.br.sprint4j.dto.UserCreateRequest;
import fiap.com.br.sprint4j.dto.UserDto;
import fiap.com.br.sprint4j.security.JwtTokenService;
import fiap.com.br.sprint4j.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtTokenService jwt;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateRequest req) {
        System.out.println(req);
        return ResponseEntity.ok(userService.register(req));
    }

@PostMapping("/login")
public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
    System.out.println(req);
    var auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.username(), req.password())
    );
    System.out.println(req);

    var userDetails = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
    String username = userDetails.getUsername();

    var domainUser = userService.findByUsernameRaw(username); 

    var token = jwt.generateToken(domainUser.get()); 
    return ResponseEntity.ok(LoginResponse.bearer(token));
}
}
