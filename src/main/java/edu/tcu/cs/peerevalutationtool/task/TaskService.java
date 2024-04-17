package edu.tcu.cs.peerevalutationtool.task;

import edu.tcu.cs.peerevalutationtool.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

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

    // Additional business logic methods as needed...
}
