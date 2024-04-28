package edu.tcu.cs.peerevalutationtool.task;

import edu.tcu.cs.peerevalutationtool.team.Team;
import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    List<Task> tasks;
    private Student testStudent;
    private Task task1;
    private Task task2;
    private Team testTeam;


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
    void testFindAllTasksByStudentIdSuccess() throws Exception{
        // Given
        given(this.taskService.findAllByStudentId(1L)).willReturn(this.tasks);

        // When and then
        this.mockMvc.perform(get("/tasks/AllByStudentId/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All by Student Id Success"))
                .andExpect(jsonPath("$.data").value(Matchers.hasSize(2)));
    }

    @Test
    void testFindAllByWeekAndStudentTeamId() throws Exception{
        // Given
        given(this.taskService.findAllByWeekAndStudentTeamId("02-12-2024", this.testStudent.getTeam().getId())).willReturn(this.tasks);

        // When and then
        this.mockMvc.perform(get("/tasks/AllByWeekAndStudentTeam/02-12-2024+testTeam").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All by Week and Student Team Id Success"))
                .andExpect(jsonPath("$.data").value(Matchers.hasSize(2)));
    }
}
