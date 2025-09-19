package taskapp.service;


import org.springframework.stereotype.Service;
import taskapp.entity.Priority;
import taskapp.repository.PriorityRepository;

import java.util.List;

@Service
public class PriorityService {
    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public List<Priority> getAll() {
        return priorityRepository.findAll();
    }

    public Priority create(Priority p) {
        return priorityRepository.save(p);
    }

    public Priority update(Long id, Priority p) {
        Priority existing = priorityRepository.findById(id).orElseThrow(() -> new RuntimeException("Priority not found"));
        existing.setName(p.getName());
        return priorityRepository.save(existing);
    }

    public void delete(Long id) {
        priorityRepository.deleteById(id);
    }
}
