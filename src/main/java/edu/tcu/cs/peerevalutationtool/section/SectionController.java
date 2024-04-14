package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevalutationtool.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevalutationtool.section.dto.SectionDto;
import edu.tcu.cs.peerevalutationtool.system.Result;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sections")
public class SectionController {

    private final SectionService sectionService;

    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;

    public SectionController(SectionService sectionService, SectionToSectionDtoConverter sectionToSectionDtoConverter, SectionDtoToSectionConverter sectionDtoToSectionConverter) {
        this.sectionService = sectionService;
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
    }

    // Find a section with a certain ID
    @GetMapping("/{sectionId}")
    public Result findSectionById(@PathVariable String sectionId){
        Section foundSection = this.sectionService.findById(sectionId);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", sectionDto);
    }

    // Return all sections
    @GetMapping()
    public Result findAllSections(){
        List<Section> foundSections = this.sectionService.findAll();
        // Convert foundSections to a list of sectionDtos
        List<SectionDto> sectionDtos = foundSections.stream()
                .map(foundSection -> this.sectionToSectionDtoConverter.convert(foundSection))
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", sectionDtos);
    }

    // Return all sections with a given year
    @GetMapping("/allbyyear/{sectionName}")
    public Result findAllByYear(@PathVariable String sectionName){
        List<Section> foundSections = this.sectionService.findAllByYear(sectionName);
        // Convert foundSections to a list of sectionDtos
        List<SectionDto> sectionDtos = foundSections.stream()
                .map(foundSection -> this.sectionToSectionDtoConverter.convert(foundSection))
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All By Year Success", sectionDtos);
    }

    // Add a section
    @PostMapping()
    public Result addSection(@Valid @RequestBody SectionDto sectionDto){
        // Convert sectionDto to section
        Section newSection = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section savedSection = this.sectionService.save(newSection);
        SectionDto savedSectionDto = this.sectionToSectionDtoConverter.convert(savedSection);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedSectionDto);
    }

}
