package edu.tcu.cs.peerevalutationtool.task;

import edu.tcu.cs.peerevalutationtool.team.Team;
import edu.tcu.cs.peerevalutationtool.repository.TaskRepository;
import edu.tcu.cs.peerevalutationtool.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @MockBean
    private TaskRepository taskRepository;
    private Student testStudent;
    private Team testTeam;
    private Task task1;
    private Task task2;
    ArrayList<Task> tasks;


    @BeforeEach
    void setUp() {
        testTeam = new Team();
        testTeam.setId("testTeam");

        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setFirstName("Carlos");
        testStudent.setLastName("Prudhomme");
        testStudent.setTeam(testTeam);

        testTeam.addStudent(testStudent);

        task1 = new Task();
        task1.setId(1L);
        task1.setStudent(testStudent);
        task1.setPlannedTask("UML");
        task1.setDescription("Do UML");
        task1.setCategory("UML");
        task1.setStatus("Done");
        task1.setPlannedHours(1.0);
        task1.setActualHours(1.0);
        task1.setWeek("02-12-2024");

        task2 = new Task();
        task2.setId(2L);
        task2.setStudent(testStudent);
        task2.setPlannedTask("Code");
        task2.setDescription("Do Code");
        task2.setCategory("Code");
        task2.setStatus("In Progress");
        task2.setPlannedHours(3.0);
        task2.setActualHours(4.0);
        task2.setWeek("02-12-2024");

        tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

    }

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

    @Test
    public void testFindAllByStudentSuccess(){
        // Given
        given(taskRepository.findAllByStudentId(1L)).willReturn(this.tasks);

        // When
        List<Task> actualTasks = taskService.findAllByStudentId(1L);

        // Then
        assertThat(actualTasks.size()).isEqualTo(this.tasks.size());
        verify(taskRepository, times(1)).findAllByStudentId(1L);
    }

    @Test
    void testFindAllByWeekSuccess(){
        // Given
        given(taskRepository.findAllByWeek("02-12-2024")).willReturn(this.tasks);

        // When
        List<Task> actualTasks = taskService.findAllByWeek("02-12-2024");

        // Then
        assertThat(actualTasks.size()).isEqualTo(this.tasks.size());
        verify(taskRepository, times(1)).findAllByWeek("02-12-2024");
    }

    @Test
    void testFindAllByWeekAndTeamSuccess(){
        // Given
        given(taskRepository.findAllByWeekAndStudentTeamId("02-12-2024", this.testStudent.getTeam().getId())).willReturn(this.tasks);

        // When
        List<Task> actualTasks = taskService.findAllByWeekAndStudentTeamId("02-12-2024", this.testStudent.getTeam().getId());

        // Then
        assertThat(actualTasks.size()).isEqualTo(this.tasks.size());
        verify(taskRepository, times(1)).findAllByWeekAndStudentTeamId("02-12-2024", this.testStudent.getTeam().getId());
    }

}
