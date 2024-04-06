package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import edu.tcu.cs.peerevalutationtool.system.Result;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/api/v1/sections/yr/{sectionYear}")
    public Result findSectionByYearr(@PathVariable String sectionYear){
        Section foundSection = this.sectionService.findByYearr(sectionYear);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", sectionDto);
    }

    @GetMapping("/api/v1/sections/secyr/{sectionId}+{sectionYear}")
    public Result findSectionByIdAndYearr(@PathVariable String sectionId, @PathVariable String sectionYear){
        Section foundSection = this.sectionService.findByIdAndYearr(sectionId, sectionYear);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", sectionDto);
    }

    @GetMapping("/api/v1/sections")
    public Result findAllSections(){
        List<Section> foundSections = this.sectionService.findAll();
        // Convert foundSections to a list of sectionDtos
        List<SectionDto> sectionDtos = foundSections.stream()
                .map(foundSection -> this.sectionToSectionDtoConverter.convert(foundSection))
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", sectionDtos);
    }

    @GetMapping("/api/v1/sections/allbyyear/{sectionName}")
    public Result findAllByYearr(@PathVariable String sectionName){
        List<Section> foundSections = this.sectionService.findAllByYearr(sectionName);
        // Convert foundSections to a list of sectionDtos
        List<SectionDto> sectionDtos = foundSections.stream()
                .map(foundSection -> this.sectionToSectionDtoConverter.convert(foundSection))
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All By Year Success", sectionDtos);
    }

}
