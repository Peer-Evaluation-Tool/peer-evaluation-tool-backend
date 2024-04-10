package edu.tcu.cs.peerevalutationtool.repository;

import edu.tcu.cs.peerevalutationtool.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFirstNameAndLastNameAndSectionNameAndAcademicYearAndTeamName(
            String firstName, String lastName, String sectionName, String academicYear, String teamName);
}
