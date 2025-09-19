package taskapp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import taskapp.entity.Priority;
import taskapp.service.PriorityService;

import java.util.List;

@RestController
@RequestMapping("/api/priorities")
public class PriorityController {
    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping
    public ResponseEntity<List<Priority>> getAll() {
        return ResponseEntity.ok(priorityService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Priority> create(@RequestBody Priority p) {
        return ResponseEntity.ok(priorityService.create(p));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Priority> update(@PathVariable Long id, @RequestBody Priority p) {
        return ResponseEntity.ok(priorityService.update(id, p));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        priorityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
