package edu.tcu.cs.peerevalutationtool.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> {
    Optional<Section> findByYearr(String sectionYear);
}
