package edu.tcu.cs.peerevalutationtool.team;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class TeamService {
    //Injecting Repository into Service using Constructor
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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


    public Team save(Team newTeam) {
        return this.teamRepository.save(newTeam);
    }
}
