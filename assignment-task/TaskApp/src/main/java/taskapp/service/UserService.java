package taskapp.service;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskapp.entity.Role;
import taskapp.entity.User;
import taskapp.repository.RoleRepository;
import taskapp.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user, List<String> roleNames) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        Set<Role> roles = new HashSet<>();
        for (String rn : roleNames) {
            Role r = roleRepository.findByName(rn).orElseThrow(() -> new RuntimeException("Role not found: " + rn));
            roles.add(r);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updated, List<String> roleNames) {
        User existing = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        if (roleNames != null) {
            Set<Role> roles = roleNames.stream()
                    .map(rn -> roleRepository.findByName(rn).orElseThrow(() -> new RuntimeException("Role not found: " + rn)))
                    .collect(Collectors.toSet());
            existing.setRoles(roles);
        }
        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
