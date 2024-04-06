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

    public Section findByYearr(String sectionYear){
        return this.sectionRepository.findByYearr(sectionYear)
                .orElseThrow(() -> new SectionNotFoundByYearrException(sectionYear));
    }

    public Section findByIdAndYearr(String sectionId, String sectionYear) {
        return this.sectionRepository.findByIdAndYearr(sectionId, sectionYear)
                .orElseThrow(() -> new SectionNotFoundByIdAndYearrException(sectionId, sectionYear));
    }

    public List<Section> findAll(){
        return this.sectionRepository.findAll();
    }

    public List<Section> findAllByYearr(String sectionName){
        return this.sectionRepository.findAllByYearr(sectionName);
    }
}
