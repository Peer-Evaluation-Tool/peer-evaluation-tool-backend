package edu.tcu.cs.peerevalutationtool.team;

import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import edu.tcu.cs.peerevalutationtool.system.Result;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import edu.tcu.cs.peerevalutationtool.team.converters.TeamDtoToTeamConverter;
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
    private final TeamDtoToTeamConverter teamDtoToTeamConverter;

    public TeamController(TeamService teamService, TeamToTeamDtoConverter teamToTeamDtoConverter, TeamDtoToTeamConverter teamDtoToTeamConverter) {
        this.teamService = teamService;
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
        this.teamDtoToTeamConverter = teamDtoToTeamConverter;
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

    @GetMapping("/findBySection/{sectionId}")
    public Result findTeamBySectionId(@PathVariable String sectionId) {
        List<Team> foundTeams = this.teamService.findBySectionId(sectionId);
        List<TeamDto> teamDtos = foundTeams.stream()
                .map(foundTeam -> this.teamToTeamDtoConverter.convert(foundTeam))
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All by Section ID Success", teamDtos);
    }

    @GetMapping("/findByInstructor/{instructorId}")
    public Result findTeamByInstructorId(@PathVariable int instructorId) {
        List<Team> foundTeams = this.teamService.findByInstructorId(instructorId);
        List<TeamDto> teamDtos = foundTeams.stream()
                .map(foundTeam -> this.teamToTeamDtoConverter.convert(foundTeam))
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All by Instructor ID Success", teamDtos);
    }

    @PostMapping()
    public Result addTeam(@Valid @RequestBody TeamDto teamDto){
        // Convert sectionDto to section
        Team newTeam = this.teamDtoToTeamConverter.convert(teamDto);
        Team savedTeam = this.teamService.save(newTeam);
        TeamDto savedTeamDto = this.teamToTeamDtoConverter.convert(savedTeam);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedTeamDto);
    }

    @PutMapping("/{teamId}")
    public Result updateSection(@PathVariable String teamId, @Valid @RequestBody TeamDto teamDto){
        Team update = this.teamDtoToTeamConverter.convert(teamDto);
        Team updatedTeam = this.teamService.update(teamId, update);
        TeamDto updatedTeamDto = this.teamToTeamDtoConverter.convert(updatedTeam);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedTeamDto);
    }
}
