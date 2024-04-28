package edu.tcu.cs.peerevalutationtool.team.converters;

import edu.tcu.cs.peerevalutationtool.admin.converter.AdminToAdminDtoConverter;
import edu.tcu.cs.peerevalutationtool.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevalutationtool.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevalutationtool.team.Team;
import edu.tcu.cs.peerevalutationtool.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamToTeamDtoConverter implements Converter<Team, TeamDto> {
    private final AdminToAdminDtoConverter adminToAdminDtoConverter;
    private final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;
    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    public TeamToTeamDtoConverter(AdminToAdminDtoConverter adminToAdminDtoConverter, InstructorToInstructorDtoConverter instructorToInstructorDtoConverter, SectionToSectionDtoConverter sectionToSectionDtoConverter) {
        this.adminToAdminDtoConverter = adminToAdminDtoConverter;
        this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
    }

    @Override
    public TeamDto convert(Team source) {
        TeamDto teamDto = new TeamDto(source.getStudents(),
                source.getId(),
                source.getOverseer()!= null
                ? this.adminToAdminDtoConverter.convert(source.getOverseer()) : null,
                source.getInstructor()!= null
                ? this.instructorToInstructorDtoConverter.convert(source.getInstructor()) : null,
                source.getSection()!= null ? this.sectionToSectionDtoConverter.convert(source.getSection()) : null,
                source.getAcademicYear());
        return teamDto;
    }
}
