package taskapp.service;

import org.springframework.stereotype.Service;
import taskapp.entity.Status;
import taskapp.repository.StatusRepository;

import java.util.List;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public Status create(Status s) {
        return statusRepository.save(s);
    }

    public Status update(Long id, Status s) {
        Status existing = statusRepository.findById(id).orElseThrow(() -> new RuntimeException("Status not found"));
        existing.setName(s.getName());
        return statusRepository.save(existing);
    }

    public void delete(Long id) {
        statusRepository.deleteById(id);
    }
}
