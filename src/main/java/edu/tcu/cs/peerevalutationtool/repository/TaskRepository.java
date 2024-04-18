package edu.tcu.cs.peerevalutationtool.repository;

import edu.tcu.cs.peerevalutationtool.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // You can define custom query methods here if necessary
}
