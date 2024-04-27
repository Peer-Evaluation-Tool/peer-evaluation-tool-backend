package edu.tcu.cs.peerevalutationtool.team;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
    @Mock
    TeamRepository teamRepository;
    @InjectMocks
    TeamService teamService;
    List<Team> teams;

    @BeforeEach
    void setUp() {
        this.teams = new ArrayList<>();
        Team team1 = new Team();
        team1.setId("Team 1");
        team1.setAcademicYear("2023-24");
        teams.add(team1);

        Team team2 = new Team();
        team2.setId("Team 2");
        team2.setAcademicYear("2023-24");
        teams.add(team2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //Given
        Team team = new Team();
        team.setId("Test Team");

        Instructor instructor = new Instructor();
        instructor.setId(1);
        instructor.setName("Test Instructor");
        team.setInstructor(instructor);

        Admin admin = new Admin();
        admin.setId(1);
        admin.setName("Test Admin");
        team.setOverseer(admin);

        given(teamRepository.findById("Test Team")).willReturn(Optional.of(team));
        //When
        Team returnedTeam = teamService.findById("Test Team");
        //Then
        assertThat(returnedTeam.getId()).isEqualTo(team.getId());
        assertThat(returnedTeam.getInstructor().getId()).isEqualTo(instructor.getId());
    }

    @Test
    void testFindByIdFailure() {
        //Given
        given(teamRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());
        //When
        Throwable thrown = catchThrowable(() -> {
            Team returnedTeam = teamService.findById("Test Team");
        });
        //Then
        assertThat(thrown).isInstanceOf(TeamNotFoundException.class).hasMessage("Could not find team with name Test Team :(");
    }

    @Test
    void testFindAllSuccess() {
        //Given
        given(teamRepository.findAll()).willReturn(teams);
        //When
        List<Team> returnedTeams = teamService.findAll();
        //Then
        assertThat(returnedTeams.size()).isEqualTo(teams.size());
        assertThat(returnedTeams.get(0).getId()).isEqualTo(teams.get(0).getId());
        assertThat(returnedTeams.get(1).getId()).isEqualTo(teams.get(1).getId());
    }

    @Test
    void testFindAllByYearSuccess(){
        //Given
        given(teamRepository.findAllByAcademicYear("2023-2024")).willReturn(this.teams);
        //When
        List<Team> actualTeams = teamService.findByAcademicYear("2023-2024");

        // Then
        assertThat(actualTeams.size()).isEqualTo(this.teams.size());
        verify(teamRepository, times(1)).findAllByAcademicYear("2023-2024");
    }
}