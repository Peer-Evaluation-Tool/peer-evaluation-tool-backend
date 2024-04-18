package edu.tcu.cs.peerevalutationtool.instructor.converter;

import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.instructor.dto.InstructorDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InstructorToInstructorDtoConverter implements Converter<Instructor, InstructorDto> {

    @Override
    public InstructorDto convert(Instructor source) {
        InstructorDto instructorDto = new InstructorDto(source.getId(),
                                                        source.getName(),
                                                        source.getNumberOfSections());
        return instructorDto;
    }
}
