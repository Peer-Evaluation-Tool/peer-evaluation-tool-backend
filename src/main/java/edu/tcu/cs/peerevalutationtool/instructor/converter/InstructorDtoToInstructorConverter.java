package edu.tcu.cs.peerevalutationtool.instructor.converter;

import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.instructor.dto.InstructorDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InstructorDtoToInstructorConverter implements Converter<InstructorDto, Instructor> {

    @Override
    public Instructor convert(InstructorDto source) {
        Instructor instructor = new Instructor();
        instructor.setId(source.id());
        instructor.setName(source.name());
        return instructor;
    }
}
