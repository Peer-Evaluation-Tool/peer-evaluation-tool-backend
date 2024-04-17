package edu.tcu.cs.peerevalutationtool.admin;

import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.domain.Team;
import edu.tcu.cs.peerevalutationtool.repository.StudentRepository;
import edu.tcu.cs.peerevalutationtool.repository.TeamRepository;
import edu.tcu.cs.peerevalutationtool.student.dto.StudentDto;
import edu.tcu.cs.peerevalutationtool.system.email.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void testAssignStudentsToTeam() {
        // Arrange
        Team team = new Team();
        team.setId(1L);
        team.setName("Team Alpha");

        Student student1 = new Student();
        student1.setId(1L);
        student1.setEmail("student1@example.com");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setEmail("student2@example.com");

        List<Student> students = Arrays.asList(student1, student2);
        List<Long> studentIds = Arrays.asList(student1.getId(), student2.getId());

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(studentRepository.findAllById(studentIds)).thenReturn(students);

        // Act
        adminService.assignStudentsToTeam(team.getId(), studentIds);

        // Assert
        try {
            verify(emailService, times(2)).sendEmail(anyString(), anyString(), anyString());
        } catch (MessagingException e) {
            fail("No exception should be thrown when verifying interactions with a mock.");
        }
        students.forEach(s -> assertEquals(team, s.getTeam()));
    }
    @Test
    public void testRemoveStudentFromTeam() {
        // Arrange
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setEmail("student@example.com");
        // Properly set the first and last names instead of using setName
        student.setFirstName("John");
        student.setLastName("Doe");
        // Setting a team to the student to simulate that they are part of a team before removal
        Team existingTeam = new Team();
        existingTeam.setId(99L); // Some existing team ID
        student.setTeam(existingTeam);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Act
        adminService.removeStudentFromTeam(studentId);

        // Assert
        // Verify that the student's team has been set to null (i.e., removed from the team)
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());
        assertNull(studentCaptor.getValue().getTeam(), "The student's team should be null after removal.");

        // Verify that a notification email has been sent
        try {
            verify(emailService).sendEmail(eq(student.getEmail()), anyString(), anyString());
        } catch (MessagingException e) {
            fail("No exception should be thrown when verifying interactions with a mock.");
        }
    }

    @Test
    public void testDeleteSeniorDesignTeam() {
        // Arrange
        Long teamId = 1L;
        Team team = new Team();
        team.setId(teamId);
        team.setName("Senior Design Team");

        // Mock repository behavior
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        // Act
        adminService.deleteSeniorDesignTeam(teamId);

        // Assert
        verify(teamRepository, times(1)).delete(team);
    }
    @Test
    public void testUpdateStudentDetails() {
        // Arrange
        Long studentId = 1L;
        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstName("OldFirstName");
        existingStudent.setLastName("OldLastName");
        existingStudent.setEmail("old@example.com");

        StudentDto updateDto = new StudentDto();
        updateDto.setFirstName("NewFirstName");
        updateDto.setLastName("NewLastName");
        updateDto.setEmail("new@example.com");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        // Act
        adminService.updateStudentDetails(studentId, updateDto);

        // Assert
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());
        Student savedStudent = studentCaptor.getValue();

        assertEquals("NewFirstName", savedStudent.getFirstName(), "First name should be updated.");
        assertEquals("NewLastName", savedStudent.getLastName(), "Last name should be updated.");
        assertEquals("new@example.com", savedStudent.getEmail(), "Email should be updated.");

        }
    }


