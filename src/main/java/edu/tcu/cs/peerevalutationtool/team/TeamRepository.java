package edu.tcu.cs.peerevalutationtool.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
    List<Team> findAllByAcademicYear(String academicYear);

    // Add any other custom methods that might be helpful for managing teams
    // For instance, finding teams by certain attributes or conditions related to UC-14 requirements
}
