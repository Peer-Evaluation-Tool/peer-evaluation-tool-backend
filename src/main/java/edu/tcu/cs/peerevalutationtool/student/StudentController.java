package edu.tcu.cs.peerevalutationtool.student;

import edu.tcu.cs.peerevalutationtool.student.dto.StudentDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody Student student) {
        try {
            Student registeredStudent = service.registerStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredStudent);
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.badRequest().body("Registration failed: Email is already in use.");
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudentDetails(@PathVariable Long studentId, @Valid @RequestBody StudentDto studentDto) {
        try {
            Student updatedStudent = service.updateStudentDetails(studentId, studentDto);
            return ResponseEntity.ok(updatedStudent);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.badRequest().body("Update failed: Email is already in use.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while updating the student details: " + e.getMessage());
        }
    }
}
