package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peer-evaluations")
public class PeerEvaluationController {

    private final PeerEvaluationService service;

    @Autowired
    public PeerEvaluationController(PeerEvaluationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> submitPeerEvaluation(@Valid @RequestBody PeerEvaluation evaluation) {
        try {
            PeerEvaluation savedEvaluation = service.submitEvaluation(evaluation);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvaluation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to submit peer evaluation: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPeerEvaluation(@PathVariable Long id) {
        try {
            PeerEvaluation evaluation = service.findEvaluationById(id);
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
            List<PeerEvaluation> evaluations = service.findEvaluationsByEvaluateeId(studentId);
            if (evaluations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(evaluations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve peer evaluations: " + e.getMessage());
        }
    }
}
