package edu.tcu.cs.peerevalutationtool.team.converters;

import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import edu.tcu.cs.peerevalutationtool.team.Team;
import edu.tcu.cs.peerevalutationtool.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamDtoToTeamConverter implements Converter<TeamDto, Team> {
    @Override
    public Team convert(TeamDto source) {
        Team team = new Team();
        team.setId(source.id());
        team.setAcademicYear(source.academicYear());
        return team;
    }
}
