package edu.tcu.cs.peerevalutationtool.task;

import edu.tcu.cs.peerevalutationtool.team.Team;
import edu.tcu.cs.peerevalutationtool.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Method to add a new task
    @Transactional
    public Task addTask(Task task) {
        // Here you might add business logic before saving the task
        return taskRepository.save(task);
    }

    // Method to update an existing task
    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Task with id " + id + " not found."));

        // Update task fields here
        task.setCategory(updatedTask.getCategory());
        task.setPlannedTask(updatedTask.getPlannedTask());
        task.setDescription(updatedTask.getDescription());
        task.setPlannedHours(updatedTask.getPlannedHours());
        task.setActualHours(updatedTask.getActualHours());
        task.setStatus(updatedTask.getStatus());

        return taskRepository.save(task);
    }

    // Method to delete a task
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task with id " + id + " not found.");
        }
        taskRepository.deleteById(id);
    }

    public List<Task> findAllByStudentId(Long studentId){
        return this.taskRepository.findAllByStudentId(studentId);
    }

    public List<Task> findAllByWeek(String taskWeek) {
        return this.taskRepository.findAllByWeek(taskWeek);
    }

    public List<Task> findAllByWeekAndStudentTeamId(String taskWeek, String taskTeamId){
        return this.taskRepository.findAllByWeekAndStudentTeamId(taskWeek, taskTeamId);
    }
}
