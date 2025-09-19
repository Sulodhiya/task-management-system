package taskapp.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskapp.dto.AuthRequest;
import taskapp.dto.AuthResponse;
import taskapp.dto.RegisterRequest;
import taskapp.entity.User;
import taskapp.service.AuthService;
import taskapp.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).body("Unauthenticated");
        String email = authentication.getName();
        User u = userRepository.findByEmail(email).orElse(null);
        if (u == null) return ResponseEntity.status(404).body("User not found");
        return ResponseEntity.ok(u);
    }
}
