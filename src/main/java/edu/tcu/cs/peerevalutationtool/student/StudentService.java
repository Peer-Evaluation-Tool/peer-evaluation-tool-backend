package edu.tcu.cs.peerevalutationtool.student;

import edu.tcu.cs.peerevalutationtool.repository.StudentRepository;
import edu.tcu.cs.peerevalutationtool.student.dto.StudentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Registers a new student, ensuring that the email is not already used.
     * @param student the student to register
     * @return the registered student with generated ID
     * @throws EmailAlreadyInUseException if email is already in use
     */
    @Transactional
    public Student registerStudent(Student student) {
        log.info("Attempting to register student with email: {}", student.getEmail());
        if (emailExists(student.getEmail())) {
            log.error("Registration failed: Email already in use {}", student.getEmail());
            throw new EmailAlreadyInUseException("There is already an account with that email address: " + student.getEmail());
        }

        // Temporarily skip password encoding
        // student.setPassword(hashPassword(student.getPassword()));

        Student savedStudent = studentRepository.save(student);
        log.info("Registered student with ID: {}", savedStudent.getId());
        return savedStudent;
    }

    /**
     * Updates student details for an existing student.
     * @param studentId the ID of the student to update
     * @param studentDto the DTO containing updated student details
     * @return the updated student entity
     * @throws StudentNotFoundException if the student cannot be found
     */
    @Transactional
    public Student updateStudentDetails(Long studentId, StudentDto studentDto) {
        log.info("Updating student details for student ID: {}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + studentId + " not found."));

        // Update student fields without handling password encryption
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        // Skip password hashing for now
        // if (studentDto.getPassword() != null && !studentDto.getPassword().isEmpty()) {
        //     student.setPassword(hashPassword(studentDto.getPassword()));
        // }

        return studentRepository.save(student);
    }

    /**
     * Checks if an email already exists in the database.
     * @param email the email to check
     * @return true if the email exists, otherwise false
     */
    public boolean emailExists(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }

    // Skip defining the hashPassword method
}

class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}

class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
