package edu.tcu.cs.peerevalutationtool.repository;

import edu.tcu.cs.peerevalutationtool.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // Existing simple CRUD operations provided by JpaRepository are sufficient for deletion

    // Example custom method to find teams with no associated students (if needed for some business logic)
    @Query("SELECT t FROM Team t WHERE t.id NOT IN (SELECT s.team.id FROM Student s)")
    List<Team> findTeamsWithoutStudents();

    // Add any other custom methods that might be helpful for managing teams
    // For instance, finding teams by certain attributes or conditions related to UC-14 requirements
}
