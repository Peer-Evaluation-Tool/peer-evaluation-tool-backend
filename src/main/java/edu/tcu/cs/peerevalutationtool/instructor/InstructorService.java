package edu.tcu.cs.peerevalutationtool.instructor;

import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.section.SectionRepository;
import edu.tcu.cs.peerevalutationtool.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InstructorService {

    private final InstructorRepository instructorRepository;

    private final SectionRepository sectionRepository;

//    @Autowired
    public InstructorService(InstructorRepository instructorRepository, SectionRepository sectionRepository) {
        this.instructorRepository = instructorRepository;
        this.sectionRepository = sectionRepository;
    }

    public List<Instructor> findAll() {
        return this.instructorRepository.findAll();
    }

    public Instructor findById(Integer instructorId) {
        return this.instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
    }

    public Instructor save(Instructor newInstructor) {
        return this.instructorRepository.save(newInstructor);
    }

    public void delete(Integer instructorId) {
        Instructor instructorToBeDeleted = this.instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
        //before deactivation, unassign the instructor's assigned sections/teams
        instructorToBeDeleted.removeAllSections();
        this.instructorRepository.deleteById(instructorId);
    }

    public void assignSection(Integer instructorId, String sectionId) {
        //find this section by Id from DB.
        Section sectionToBeAssigned = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ObjectNotFoundException("section", sectionId));

        //find instructor by Id from DB
        Instructor instructor = this.instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));

        //section assignment
        //check if section is already assigned to an instructor
        if (sectionToBeAssigned.getOwner() != null) {
            sectionToBeAssigned.getOwner().removeSection(sectionToBeAssigned);
        }
        instructor.addSection(sectionToBeAssigned);
    }
}
