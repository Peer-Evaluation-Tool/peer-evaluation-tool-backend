package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.repository.PeerEvaluationRepository;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeerEvaluationService {

    private static final Logger log = LoggerFactory.getLogger(PeerEvaluationService.class);
    private final PeerEvaluationRepository repository;

    @Autowired
    public PeerEvaluationService(PeerEvaluationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PeerEvaluation submitEvaluation(PeerEvaluation evaluation) {
        log.info("Submitting peer evaluation for evaluator {} on evaluatee {}", evaluation.getEvaluator().getId(), evaluation.getEvaluatee().getId());
        PeerEvaluation savedEvaluation = repository.save(evaluation);
        log.info("Submitted peer evaluation with ID: {}", savedEvaluation.getId());
        return savedEvaluation;
    }

    @Transactional(readOnly = true)
    public PeerEvaluation findEvaluationById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EvaluationNotFoundException("Peer evaluation with ID " + id + " not found."));
    }

    /**
     * Retrieves peer evaluations where the given student is the evaluatee.
     * @param studentId the ID of the student whose report is to be generated
     * @return a list of PeerEvaluation
     */
    @Transactional(readOnly = true)
    public List<PeerEvaluation> findEvaluationsByEvaluateeId(Long studentId) {
        return repository.findByEvaluateeId(studentId);  // Assume this method is implemented in the repository
    }
}

class EvaluationNotFoundException extends RuntimeException {
    public EvaluationNotFoundException(String message) {
        super(message);
    }
}