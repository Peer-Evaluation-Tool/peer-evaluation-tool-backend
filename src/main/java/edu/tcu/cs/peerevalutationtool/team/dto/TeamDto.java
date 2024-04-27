package edu.tcu.cs.peerevalutationtool.team.dto;

import edu.tcu.cs.peerevalutationtool.admin.dto.AdminDto;
import edu.tcu.cs.peerevalutationtool.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import edu.tcu.cs.peerevalutationtool.student.Student;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TeamDto(List<Student> students,
                      @NotEmpty(message = "Name is required") String id,
                      AdminDto admin,
                      InstructorDto instructorDto,
                      SectionDto sectionDto,
                      String academicYear) {
}
