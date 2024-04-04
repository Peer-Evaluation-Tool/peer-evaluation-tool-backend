package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import edu.tcu.cs.peerevalutationtool.system.Result;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SectionController {

    private final SectionService sectionService;

    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    public SectionController(SectionService sectionService, SectionToSectionDtoConverter sectionToSectionDtoConverter) {
        this.sectionService = sectionService;
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
    }

    @GetMapping("/api/v1/sections/{sectionId}")
    public Result findSectionById(@PathVariable String sectionId){
        Section foundSection = this.sectionService.findById(sectionId);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", sectionDto);
    }

}
