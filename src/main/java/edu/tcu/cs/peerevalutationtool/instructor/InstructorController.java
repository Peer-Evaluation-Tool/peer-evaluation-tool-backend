package edu.tcu.cs.peerevalutationtool.instructor;

import edu.tcu.cs.peerevalutationtool.system.Result;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import edu.tcu.cs.peerevalutationtool.instructor.converter.InstructorDtoToInstructorConverter;
import edu.tcu.cs.peerevalutationtool.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevalutationtool.instructor.dto.InstructorDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    private final InstructorDtoToInstructorConverter instructorDtoToInstructorConverter;

    private final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

    public InstructorController(InstructorService instructorService, InstructorDtoToInstructorConverter instructorDtoToInstructorConverter, InstructorToInstructorDtoConverter instructorToInstructorDtoConverter) {
        this.instructorService = instructorService;
        this.instructorDtoToInstructorConverter = instructorDtoToInstructorConverter;
        this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
    }

    @GetMapping
    public Result findAllInstructors() {
        List<Instructor> foundInstructors = this.instructorService.findAll();

        //convert found instructors to a list of InstructorDtos
        List<InstructorDto> instructorDtos = foundInstructors.stream()
                .map(this.instructorToInstructorDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", instructorDtos);
    }

    @GetMapping("/{instructorId}")
    public Result findInstructorById(@PathVariable Integer instructorId) {
        Instructor foundInstructor = this.instructorService.findById(instructorId);
        InstructorDto instructorDto = this.instructorToInstructorDtoConverter.convert(foundInstructor);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", instructorDto);
    }

    @PostMapping
    public Result addInstructor(@Valid @RequestBody InstructorDto instructorDto) {
        Instructor newInstructor = this.instructorDtoToInstructorConverter.convert(instructorDto);
        Instructor savedInstructor = this.instructorService.save(newInstructor);
        InstructorDto savedInstructorDto = this.instructorToInstructorDtoConverter.convert(savedInstructor);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedInstructorDto);
    }

    @DeleteMapping("/{instructorId}")
    public Result deleteInstructor(@PathVariable Integer instructorId) {
        this.instructorService.delete(instructorId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

    //reactivate and deactivate instructor classes needed

    @PutMapping("/{instructorId}/sections/{sectionId}")
    public Result assignSection(@PathVariable Integer instructorId, @PathVariable String sectionId) {
        this.instructorService.assignSection(instructorId, sectionId);
        return new Result(true, StatusCode.SUCCESS, "Section Assignment Success");
    }
}
