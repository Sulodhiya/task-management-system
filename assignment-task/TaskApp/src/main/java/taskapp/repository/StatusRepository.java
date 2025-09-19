package taskapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import taskapp.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
	
	Optional<Status> findByName(String name);

}
