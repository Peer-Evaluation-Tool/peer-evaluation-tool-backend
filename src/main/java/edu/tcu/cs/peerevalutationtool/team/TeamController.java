package edu.tcu.cs.peerevalutationtool.team;

import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import edu.tcu.cs.peerevalutationtool.system.Result;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import edu.tcu.cs.peerevalutationtool.team.converters.TeamToTeamDtoConverter;
import edu.tcu.cs.peerevalutationtool.team.dto.TeamDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {
    //Injecting  Service into Controller using constructor
    private final TeamService teamService;
    private final TeamToTeamDtoConverter teamToTeamDtoConverter;

    public TeamController(TeamService teamService, TeamToTeamDtoConverter teamToTeamDtoConverter) {
        this.teamService = teamService;
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
    }

    @GetMapping("/{teamId}")
    public Result findTeamById(@PathVariable String teamId) {
        Team foundTeam = this.teamService.findById(teamId);
        TeamDto teamDto = this.teamToTeamDtoConverter.convert(foundTeam);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", teamDto);
    }

    @GetMapping("")
    public Result findAllTeams(){
        List<Team> foundTeams = this.teamService.findAll();
        List<TeamDto> teamDtos = foundTeams.stream().map(teamToTeamDtoConverter::convert).toList();
        return new Result(true, StatusCode.SUCCESS, "Find All Success", teamDtos);
    }

    @GetMapping("/findByYear/{academicYear}")
    public Result findTeamByAcademicYear(@PathVariable String academicYear) {
        List<Team> foundTeams = this.teamService.findByAcademicYear(academicYear);
        List<TeamDto> teamDtos = foundTeams.stream()
                .map(foundTeam -> this.teamToTeamDtoConverter.convert(foundTeam))
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All by Year Success", teamDtos);
    }



    @PostMapping
    public Result addTeam(@Valid @RequestBody TeamDto teamDto){
        return null;
    }
}
