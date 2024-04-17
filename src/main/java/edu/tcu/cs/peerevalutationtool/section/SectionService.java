package edu.tcu.cs.peerevalutationtool.section;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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
//                    oldSection.setActiveWeeks(update.getActiveWeeks());
                    return this.sectionRepository.save(oldSection);
                })
                .orElseThrow(() -> new SectionNotFoundException(sectionId));
    }

    // Def: Remove the weeks corresponding to indices from activeWeeks for a given section
    // Assuming front-end has already generated the weeks between startDate and endDate
    // the admin will choose which weeks she does not want to be active.
    // These weeks will be deducted from the activeWeeks of the section
//    public Section updateActiveWeeks(String sectionId, HashSet<Integer> inActiveWeeks){
//        Section section = this.sectionRepository.findById(sectionId).orElseThrow(() -> new SectionNotFoundException(sectionId));
//        section.populateActiveWeeks();
//        section.dropActiveWeeks(inActiveWeeks);
//        sectionRepository.save(section);
//
//        return section;
//    }
}
