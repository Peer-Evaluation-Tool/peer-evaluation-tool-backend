package edu.tcu.cs.peerevalutationtool.repository;

import edu.tcu.cs.peerevalutationtool.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStudentId(Long studentId);
    List<Task> findAllByWeek(String taskWeek);
    List<Task> findAllByWeekAndStudentTeamId(String taskWeek, String taskTeamId);
}
