package taskapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import taskapp.entity.Priority;



public interface PriorityRepository extends JpaRepository<Priority, Long> {
	
	Optional<Priority> findByName(String name);

}
