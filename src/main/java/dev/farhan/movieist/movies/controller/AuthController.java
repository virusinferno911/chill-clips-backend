package dev.farhan.movieist.controller;

import dev.farhan.movieist.model.User;
import dev.farhan.movieist.repository.UserRepository;
import dev.farhan.movieist.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo,
                          PasswordEncoder encoder,
                          JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder  = encoder;
        this.jwtUtil  = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");

        if (email == null || password == null || password.length() < 6) {
            return ResponseEntity.badRequest()
                .body(Map.of("error",
                    "Email and password (min 6 characters) are required."));
        }

        if (userRepo.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                .body(Map.of("error",
                    "An account with this email already exists."));
        }

        User user = new User(email, encoder.encode(password));
        userRepo.save(user);

        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of(
            "token",   token,
            "email",   email,
            "message", "Account created successfully."
        ));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");

        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty() ||
            !encoder.matches(password, userOpt.get().getPassword())) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "Invalid email or password."));
        }

        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of(
            "token",   token,
            "email",   email,
            "message", "Signed in successfully."
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(
            @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "No token provided."));
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "Invalid or expired token."));
        }
        return ResponseEntity.ok(
            Map.of("email", jwtUtil.extractEmail(token)));
    }
}