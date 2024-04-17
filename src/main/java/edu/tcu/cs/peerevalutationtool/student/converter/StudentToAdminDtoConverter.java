package edu.tcu.cs.peerevalutationtool.student.converter;

import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.student.dto.StudentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentToAdminDtoConverter implements Converter<Student, StudentDto> {

    @Override
    public StudentDto convert(Student source) {
        StudentDto dto = new StudentDto();
        dto.setId(source.getId());
        dto.setFirstName(source.getFirstName());
        dto.setLastName(source.getLastName());
        dto.setEmail(source.getEmail());
        // Assume password shouldn't be directly mapped
        return dto;
    }
}

