package taskapp.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import taskapp.dto.UserDTO;
import taskapp.entity.User;
import taskapp.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> list = userService.getAllUsers().stream()
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> create(@RequestBody User user, @RequestParam(required = false) List<String> roles) {
        User created = userService.createUser(user, roles == null ? List.of("USER") : roles);
        UserDTO dto = new UserDTO(created.getId(), created.getName(), created.getEmail());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User user, @RequestParam(required = false) List<String> roles) {
        User updated = userService.updateUser(id, user, roles);
        UserDTO dto = new UserDTO(updated.getId(), updated.getName(), updated.getEmail());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
