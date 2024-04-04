package edu.tcu.cs.peerevalutationtool.section.converter;

import edu.tcu.cs.peerevalutationtool.admin.converter.AdminToAdminDtoConverter;
import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SectionToSectionDtoConverter implements Converter<Section, SectionDto> {

    private final AdminToAdminDtoConverter adminToAdminDtoConverter;

    public SectionToSectionDtoConverter(AdminToAdminDtoConverter adminToAdminDtoConverter) {
        this.adminToAdminDtoConverter = adminToAdminDtoConverter;
    }

    @Override
    public SectionDto convert(Section source) {
        SectionDto sectionDto = new SectionDto(source.getId(),
                                                source.getYear(),
                                                source.getOverseer() != null
                                                        ? this.adminToAdminDtoConverter.convert(source.getOverseer())
                                                        : null);
        return sectionDto;
    }
}
