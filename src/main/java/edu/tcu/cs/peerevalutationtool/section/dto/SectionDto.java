package edu.tcu.cs.peerevalutationtool.section.dto;

import edu.tcu.cs.peerevalutationtool.admin.dto.AdminDto;

public record SectionDto(String id,
                         String year,
                         AdminDto overseer) {
}
