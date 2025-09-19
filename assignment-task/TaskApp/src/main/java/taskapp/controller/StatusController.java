package taskapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import taskapp.entity.Status;
import taskapp.service.StatusService;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAll() {
        return ResponseEntity.ok(statusService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Status> create(@RequestBody Status s) {
        return ResponseEntity.ok(statusService.create(s));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Status> update(@PathVariable Long id, @RequestBody Status s) {
        return ResponseEntity.ok(statusService.update(id, s));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        statusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
