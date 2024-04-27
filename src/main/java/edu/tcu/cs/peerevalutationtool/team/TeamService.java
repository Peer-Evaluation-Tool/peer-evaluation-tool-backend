package edu.tcu.cs.peerevalutationtool.team;

import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.instructor.InstructorRepository;
import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.section.SectionNotFoundException;
import edu.tcu.cs.peerevalutationtool.section.SectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TeamService {
    //Injecting Repository into Service using Constructor
    private final TeamRepository teamRepository;
    private final SectionRepository sectionRepository;
    //private final InstructorRepository instructorRepository;

    public TeamService(TeamRepository teamRepository, SectionRepository sectionRepository) {
        this.teamRepository = teamRepository;
        this.sectionRepository = sectionRepository;
        //this.instructorRepository = instructorRepository;
    }

    public Team findById(String teamId){
        return this.teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
    }

    public List<Team> findAll(){
        return this.teamRepository.findAll();
    }

    public List<Team> findByAcademicYear(String academicYear){
        return this.teamRepository.findAllByAcademicYear(academicYear);
//        List<Team> teams = this.teamRepository.findAllByAcademicYear(academicYear);
//        if (teams.isEmpty()) {
//            // Return a special object indicating that no teams were found for the given year
//            return Collections.singletonList(new TeamNotFoundByYearException(academicYear));
//        } else {
//            return teams;
//        }
    }

    public List<Team> findBySectionId(String sectionId){
        return teamRepository.findTeamBySection_Id(sectionId);
    }

    public List<Team> findByInstructorId(int instructorId){
        return teamRepository.findTeamByInstructor_Id(instructorId);
    }

    public Team save(Team newTeam){
        return this.teamRepository.save(newTeam);
    }

    public Team update(String teamId, Team update){
        return this.teamRepository.findById(teamId)
                .map(oldTeam -> {
                    oldTeam.setAcademicYear(update.getAcademicYear());
                    oldTeam.setSection(update.getSection());
                    oldTeam.setInstructor(update.getInstructor());
                    return this.teamRepository.save(oldTeam);
                })
                .orElseThrow(() -> new TeamNotFoundException(teamId));
    }
}
