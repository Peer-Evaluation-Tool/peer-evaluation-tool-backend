package edu.tcu.cs.peerevalutationtool.team;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.section.Section;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
    @Mock
    TeamRepository teamRepository;
    @InjectMocks
    TeamService teamService;
    List<Team> teams, sameSectionTeams, sameInstructorTeams, sameAcademicYearTeams;
    Section section;
    Instructor instructor;

    @BeforeEach
    void setUp() {
        this.section = new Section();
        section.setId("Section 2023-2024");
        section.setFirstDate("Aug 2023");
        section.setLastDate("May 2024");

        this.instructor = new Instructor();
        instructor.setId(1);
        instructor.setName("Bingyang Wei");

        this.teams = new ArrayList<>();
        this.sameAcademicYearTeams = new ArrayList<>();
        this.sameSectionTeams = new ArrayList<>();
        this.sameInstructorTeams = new ArrayList<>();

        Team team1 = new Team();
        team1.setId("Team 1");
        team1.setAcademicYear("2023-24");
        team1.setSection(section);
        teams.add(team1);
        sameAcademicYearTeams.add(team1);
        sameSectionTeams.add(team1);

        Team team2 = new Team();
        team2.setId("Team 2");
        team2.setAcademicYear("2020-21");
        team2.setSection(section);
        team2.setInstructor(instructor);
        teams.add(team2);
        sameSectionTeams.add(team2);
        sameInstructorTeams.add(team2);

        Team team3 = new Team();
        team3.setId("Team 3");
        team3.setAcademicYear("2019-20");
        team3.setInstructor(instructor);
        teams.add(team3);
        sameInstructorTeams.add(team3);

        Team team4 = new Team();
        team4.setId("Team 4");
        team4.setAcademicYear("2023-24");
        teams.add(team4);
        sameAcademicYearTeams.add(team4);



        team1.setSection(section);
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
        verify(teamRepository, times(1)).findById("Test Team");
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
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void testFindAllByYearSuccess(){
        //Given
        given(teamRepository.findAllByAcademicYear("2023-2024")).willReturn(this.sameAcademicYearTeams);
        //When
        List<Team> actualTeams = teamService.findByAcademicYear("2023-2024");

        // Then
        assertThat(actualTeams.size()).isEqualTo(this.sameAcademicYearTeams.size());
        assertThat(actualTeams.get(0).getId()).isEqualTo(sameAcademicYearTeams.get(0).getId());
        assertThat(actualTeams.get(1).getId()).isEqualTo(sameAcademicYearTeams.get(1).getId());
        verify(teamRepository, times(1)).findAllByAcademicYear("2023-2024");
    }

    @Test
    void testFindAllBySectionSuccess(){
        //Given
        given(teamRepository.findTeamBySection_Id("Section 2023-24")).willReturn(sameSectionTeams);
        //When
        List<Team> actualTeams = teamService.findBySectionId("Section 2023-24");
        //Then
        assertThat(actualTeams.size()).isEqualTo(2);
        assertThat(actualTeams.get(0).getId()).isEqualTo(teams.get(0).getId());
        verify(teamRepository, times(1)).findTeamBySection_Id("Section 2023-24");
    }

    @Test
    void testFindAllByInstructorSuccess(){
        //Given
        given(teamRepository.findTeamByInstructor_Id(1)).willReturn(sameInstructorTeams);
        //When
        List<Team> actualTeams = teamService.findByInstructorId(1);
        //Then
        assertThat(actualTeams.size()).isEqualTo(2);
        assertThat(actualTeams.get(0).getId()).isEqualTo(sameInstructorTeams.get(0).getId());
        assertThat(actualTeams.get(1).getId()).isEqualTo(sameInstructorTeams.get(1).getId());
        verify(teamRepository, times(1)).findTeamByInstructor_Id(1);
    }

    @Test
    void testSaveSuccess(){
        // Given
        Team newTeam = new Team();
        newTeam.setId("Test Team");
        newTeam.setAcademicYear("2023-24");
        newTeam.setSection(section);

        given(teamRepository.save(newTeam)).willReturn(newTeam);

        // When
        Team savedTeam = teamService.save(newTeam);

        // Then
        assertThat(savedTeam.getId()).isEqualTo("Test Team");
        assertThat(savedTeam.getAcademicYear()).isEqualTo("2023-24");
        verify(teamRepository, times(1)).save(newTeam);
    }

    @Test
    void testUpdateSuccess(){
        // Given
        Team oldTeam = new Team();
        oldTeam.setId("Test Team");
        oldTeam.setAcademicYear("2024-2025");
        oldTeam.setInstructor(null);
        oldTeam.setSection(null);

        Team update = new Team();
        update.setId("Test Team");
        update.setAcademicYear("2024-2025");
        update.setInstructor(instructor);
        update.setSection(section);

        given(teamRepository.findById("Test Team")).willReturn(Optional.of(oldTeam));
        given(teamRepository.save(oldTeam)).willReturn(oldTeam);

        // When
        Team updatedTeam = teamService.update("Test Team", update);

        // Then
        assertThat(updatedTeam.getId()).isEqualTo(update.getId());
        assertThat(updatedTeam.getAcademicYear()).isEqualTo(update.getAcademicYear());
        assertThat(updatedTeam.getSection()).isEqualTo(update.getSection());
        verify(teamRepository, VerificationModeFactory.times(1)).findById("Test Team");
        verify(teamRepository, VerificationModeFactory.times(1)).save(oldTeam);
    }

}