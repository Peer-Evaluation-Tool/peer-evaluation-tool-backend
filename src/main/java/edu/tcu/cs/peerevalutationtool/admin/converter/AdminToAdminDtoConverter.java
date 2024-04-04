package edu.tcu.cs.peerevalutationtool.admin.converter;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.admin.dto.AdminDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AdminToAdminDtoConverter implements Converter<Admin, AdminDto> {
    @Override
    public AdminDto convert(Admin source) {
        AdminDto adminDto = new AdminDto(source.getId(),
                                            source.getName(),
                                            source.getNumberOfSections());
        return adminDto;
    }
}
