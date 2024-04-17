package edu.tcu.cs.peerevalutationtool.student;

import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.repository.StudentRepository;
import edu.tcu.cs.peerevalutationtool.student.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService service;

    @MockBean
    private StudentRepository repository;

    @Test
    public void testRegisterStudent() {
        // Mock setup
        Student student = new Student();
        student.setEmail("test@example.com");

        // Modify this to reflect actual service logic
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(Student.class))).thenReturn(student);

        // Service call
        Student savedStudent = service.registerStudent(student);

        // Assertions
        assertNotNull(savedStudent);
        verify(repository).findByEmail(anyString()); // Verify this method as used in your service
        verify(repository).save(any(Student.class));
    }

}
