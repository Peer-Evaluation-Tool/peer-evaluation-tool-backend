package edu.tcu.cs.peerevalutationtool.team;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TeamService teamService;

    @Autowired
    ObjectMapper objectMapper;

    List<Team> teams;
    Admin admin;
    Instructor instructor;

    @BeforeEach
    void setUp() {
        this.teams = new ArrayList<>();
        this.admin = new Admin();
        this.instructor = new Instructor();

        admin.setId(1);
        admin.setName("admin");

        Team team1 = new Team();
        team1.setId("Team 1");
        team1.setOverseer(admin);
        team1.setInstructor(instructor);
        team1.setAcademicYear("2023-24");
        teams.add(team1);

        Team team2 = new Team();
        team2.setId("Team 2");
        team2.setOverseer(admin);
        team2.setAcademicYear("2020-21");
        teams.add(team2);

        Team team3 = new Team();
        team3.setId("Team 3");
        team3.setOverseer(admin);
        team3.setAcademicYear("2023-24");
        teams.add(team3);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindTeamByIdSuccess() throws Exception {
        //Given
        given(this.teamService.findById("Team 1")).willReturn(this.teams.get(0));
        //When and Then
        this.mockMvc.perform(get("/api/v1/teams/Team 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("Team 1"));
    }

    @Test
    void testFindTeamByIdFailure() throws Exception {
        //Given
        given(this.teamService.findById("Team 1")).willThrow(new TeamNotFoundException("Team 1"));
        //When and Then
        this.mockMvc.perform(get("/api/v1/teams/Team 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find team with name Team 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAllTeamsSuccess() throws Exception {
        //Given
        given(this.teamService.findAll()).willReturn(this.teams);
        //When and Then
        this.mockMvc.perform(get("/api/v1/teams").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data[0].id").value("Team 1"))
                .andExpect(jsonPath("$.data[1].id").value("Team 2"))
                .andExpect(jsonPath("$.data[2].id").value("Team 3"));

    }

    @Test
    void testFindTeamByAcademicYearSuccess() throws Exception {
        //Given
        List<Team> selectedTeams = teams.stream()
                .filter(team -> "2023-24".equals(team.getAcademicYear()))
                .collect(Collectors.toList());
        given(this.teamService.findByAcademicYear("2023-24")).willReturn(selectedTeams);
        //When and Then
        this.mockMvc.perform(get("/api/v1/teams/findByYear/2023-24").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All by Year Success"))
                .andExpect(jsonPath("$.data[0].id").value("Team 1"))
                .andExpect(jsonPath("$.data[1].id").value("Team 3"));
    }

}