package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.system.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/api/v1/sections/{sectionId}")
    public Result findSectionById(@PathVariable String sectionId){
        return null;
    }

}
