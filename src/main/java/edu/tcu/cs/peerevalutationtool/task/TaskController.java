package edu.tcu.cs.peerevalutationtool.task;

import edu.tcu.cs.peerevalutationtool.task.TaskService;
import edu.tcu.cs.peerevalutationtool.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks") // This specifies the base path for all endpoints in this controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Endpoint to add a new task
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task newTask = taskService.addTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    // Endpoint to update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    // Endpoint to delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Additional endpoints can be defined here...
}
