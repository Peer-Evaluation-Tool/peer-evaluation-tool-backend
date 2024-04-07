package edu.tcu.cs.peerevalutationtool.section;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Section findById(String sectionId){
        return this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException(sectionId));
    }

    public Section findByYear(String sectionYear){
        return this.sectionRepository.findByYear(sectionYear)
                .orElseThrow(() -> new SectionNotFoundByYearException(sectionYear));
    }

    public Section findByIdAndYear(String sectionId, String sectionYear) {
        return this.sectionRepository.findByIdAndYear(sectionId, sectionYear)
                .orElseThrow(() -> new SectionNotFoundByIdAndYearException(sectionId, sectionYear));
    }

    public List<Section> findAll(){
        return this.sectionRepository.findAll();
    }

    public List<Section> findAllByYear(String sectionName){
        return this.sectionRepository.findAllByYear(sectionName);
    }
}
