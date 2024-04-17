package edu.tcu.cs.peerevalutationtool.repository;

import edu.tcu.cs.peerevalutationtool.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    /**
     * Finds a student by their email.
     * This method is used to ensure no two students share the same email in the system.
     * @param email the email to search for
     * @return an Optional containing the student if found, or empty if no student is found with that email.
     */
    Optional<Student> findByEmail(String email);

    /**
     * Checks if an email already exists in the database.
     * @param email the email to check
     * @return true if the email exists, otherwise false
     */
    boolean existsByEmail(String email);
}
