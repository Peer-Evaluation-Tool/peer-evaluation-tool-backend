package edu.tcu.cs.peerevalutationtool.section.dto;

import edu.tcu.cs.peerevalutationtool.admin.dto.AdminDto;
import jakarta.validation.constraints.NotEmpty;

public record SectionDto(String id,
                         @NotEmpty(message = "year is required.")
                         String year,
                         @NotEmpty(message = "firstDate is required.")
                         String firstDate,
                         @NotEmpty(message = "lastDate is required.")
                         String lastDate,
                         AdminDto overseer) {
}
