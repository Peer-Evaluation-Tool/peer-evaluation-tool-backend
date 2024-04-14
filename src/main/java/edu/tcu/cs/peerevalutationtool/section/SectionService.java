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

    public List<Section> findAll(){
        return this.sectionRepository.findAll();
    }

    public List<Section> findAllByYear(String sectionName){
        return this.sectionRepository.findAllByYear(sectionName);
    }

    public Section save(Section newSection){
        return this.sectionRepository.save(newSection);
    }

    public Section update(String sectionId, Section update){
        return this.sectionRepository.findById(sectionId)
                .map(oldSection -> {
                    oldSection.setYear(update.getYear());
                    oldSection.setFirstDate(update.getFirstDate());
                    oldSection.setLastDate(update.getLastDate());
                    return this.sectionRepository.save(oldSection);
                })
                .orElseThrow(() -> new SectionNotFoundException(sectionId));
    }
}
