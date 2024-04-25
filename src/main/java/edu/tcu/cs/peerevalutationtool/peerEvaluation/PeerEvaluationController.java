package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.peerEvaluation.DTO.PeerEvaluationDto;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.DTO.PeerEvaluationReportDTO;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluationService;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.converter.EvaluationDtoToEvaluationConverter;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.converter.EvaluationToEvaluationDtoConverter;
import edu.tcu.cs.peerevalutationtool.system.Result;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peer-evaluations")
public class PeerEvaluationController {

    private final PeerEvaluationService peerEvaluationService;
    private final EvaluationToEvaluationDtoConverter evaluationToEvaluationDtoConverter;
    private final EvaluationDtoToEvaluationConverter evaluationDtoToEvaluationConverter;

    @Autowired
    public PeerEvaluationController(PeerEvaluationService peerEvaluationService, EvaluationToEvaluationDtoConverter evaluationToEvaluationDtoConverter, EvaluationDtoToEvaluationConverter evaluationDtoToEvaluationConverter) {
        this.peerEvaluationService = peerEvaluationService;
        this.evaluationToEvaluationDtoConverter = evaluationToEvaluationDtoConverter;
        this.evaluationDtoToEvaluationConverter = evaluationDtoToEvaluationConverter;
    }

    @PostMapping
    public ResponseEntity<?> submitPeerEvaluation(@Valid @RequestBody PeerEvaluation evaluation) {
        try {
            PeerEvaluation savedEvaluation = peerEvaluationService.submitEvaluation(evaluation);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvaluation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to submit peer evaluation: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPeerEvaluation(@PathVariable Long id) {
        try {
            PeerEvaluation evaluation = peerEvaluationService.findEvaluationById(id);
            return ResponseEntity.ok(evaluation);
        } catch (EvaluationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while retrieving the peer evaluation: " + e.getMessage());
        }
    }

    /**
     * Endpoint to retrieve all evaluations for a specific student as a report.
     * @param studentId the ID of the student to retrieve evaluations for
     * @return a list of peer evaluations
     */
    @GetMapping("/reports/{studentId}")
    public ResponseEntity<?> getPeerEvaluationsForStudent(@PathVariable Long studentId) {
        try {
            List<PeerEvaluation> evaluations = peerEvaluationService.findEvaluationsByEvaluateeId(studentId);
            if (evaluations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(evaluations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve peer evaluations: " + e.getMessage());
        }
    }

    @GetMapping("/reports/allbyweek/{peerEvaluationWeek}")
    public Result findAllByWeek(@PathVariable String peerEvaluationWeek){
        List<PeerEvaluation> foundEvaluations = this.peerEvaluationService.findAllByWeek(peerEvaluationWeek);
        // Convert foundEvaluations to a list of evaluationDtos
        List<PeerEvaluationDto> evaluationDtos = foundEvaluations.stream()
                .map(foundEvaluation -> this.evaluationToEvaluationDtoConverter.convert(foundEvaluation))
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All by Week Success", evaluationDtos);
    }
}
