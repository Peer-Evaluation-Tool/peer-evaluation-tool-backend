package edu.tcu.cs.peerevalutationtool.section.converter;

import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SectionDtoToSectionConverter implements Converter<SectionDto, Section> {

    @Override
    public Section convert(SectionDto source) {
        Section section = new Section();
        section.setId(source.id());
        section.setYear(source.year());
        section.setFirstDate(source.firstDate());
        section.setLastDate(source.lastDate());
        return section;
    }
}
