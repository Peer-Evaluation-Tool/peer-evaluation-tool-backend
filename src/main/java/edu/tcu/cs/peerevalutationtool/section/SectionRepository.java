package edu.tcu.cs.peerevalutationtool.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> {
    Optional<Section> findByYear(String sectionYear);

    Optional<Section> findByIdAndYear(String sectionId, String sectionYear);

    List<Section> findAllByYear(String sectionYear);
}
