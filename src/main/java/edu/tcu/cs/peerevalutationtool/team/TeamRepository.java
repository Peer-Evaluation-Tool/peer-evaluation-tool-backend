package edu.tcu.cs.peerevalutationtool.team;

import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.section.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
    List<Team> findAllByAcademicYear(String academicYear);
    List<Team> findTeamBySection_Id(String sectionId);
    List<Team> findTeamByInstructor_Id(int instructorId);
}
