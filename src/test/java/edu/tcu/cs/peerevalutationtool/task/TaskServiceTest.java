package edu.tcu.cs.peerevalutationtool.task;

import edu.tcu.cs.peerevalutationtool.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    // Example test methods will be added here

    @Test
    public void testAddNewTask() {
        Task newTask = new Task();
        newTask.setDescription("Work on project report");
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        Task savedTask = taskService.addTask(newTask);

        assertNotNull(savedTask);
        assertEquals("Work on project report", savedTask.getDescription());
        verify(taskRepository).save(newTask);
    }
    @Test
    public void testEditTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setDescription("Initial Task");

        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        Task savedTask = taskService.updateTask(1L, updatedTask);

        assertNotNull(savedTask);
        assertEquals("Updated Task", savedTask.getDescription());
        verify(taskRepository).save(existingTask);
    }
    @Test
    public void testDeleteTask() {
        Long taskId = 1L;
        doNothing().when(taskRepository).deleteById(taskId);
        when(taskRepository.existsById(taskId)).thenReturn(true);

        taskService.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);
    }


}
