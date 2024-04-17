package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.repository.PeerEvaluationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeerEvaluationServiceTest {

    @Mock
    private PeerEvaluationRepository repository;

    @InjectMocks
    private PeerEvaluationService service;

    private PeerEvaluation evaluation;
    private Student evaluator;
    private Student evaluatee;

    @BeforeEach
    public void setup() {
        evaluator = new Student();
        evaluator.setId(1L);
        evaluatee = new Student();
        evaluatee.setId(2L);

        evaluation = new PeerEvaluation();
        evaluation.setId(1L);
        evaluation.setEvaluator(evaluator);
        evaluation.setEvaluatee(evaluatee);
        evaluation.setQualityOfWork(9);
        evaluation.setPublicComments("Good job!");
        evaluation.setPrivateComments("Need improvement on documentation.");
    }

    @Test
    public void testSubmitEvaluation() {
        when(repository.save(any(PeerEvaluation.class))).thenReturn(evaluation);

        PeerEvaluation savedEvaluation = service.submitEvaluation(evaluation);

        verify(repository).save(any(PeerEvaluation.class));
        assertNotNull(savedEvaluation, "The saved evaluation should not be null.");
        assertEquals(evaluation.getId(), savedEvaluation.getId(), "The IDs should match.");
        assertEquals(evaluation.getPublicComments(), savedEvaluation.getPublicComments(), "The public comments should match.");
    }

    @Test
    public void testFindEvaluationById() {
        when(repository.findById(1L)).thenReturn(Optional.of(evaluation));

        PeerEvaluation foundEvaluation = service.findEvaluationById(1L);

        verify(repository).findById(1L);
        assertNotNull(foundEvaluation);
        assertEquals(1L, foundEvaluation.getId());
        assertEquals(9, foundEvaluation.getQualityOfWork());
    }

    @Test
    public void testFindEvaluationById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EvaluationNotFoundException exception = assertThrows(EvaluationNotFoundException.class, () -> {
            service.findEvaluationById(1L);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }
    @Test
    public void testFindEvaluationsByEvaluateeId() {
        List<PeerEvaluation> expectedEvaluations = Arrays.asList(evaluation);

        // Setup the repository mock to return a predefined list of evaluations when queried for a specific evaluatee
        when(repository.findByEvaluateeId(evaluatee.getId())).thenReturn(expectedEvaluations);

        // Call the service method
        List<PeerEvaluation> actualEvaluations = service.findEvaluationsByEvaluateeId(evaluatee.getId());

        // Verify the interaction with the repository
        verify(repository).findByEvaluateeId(evaluatee.getId());

        // Assert that the returned evaluations are what we expect
        assertNotNull(actualEvaluations, "The returned list should not be null.");
        assertFalse(actualEvaluations.isEmpty(), "The returned list should not be empty.");
        assertEquals(expectedEvaluations.size(), actualEvaluations.size(), "The size of the returned list should match the expected list.");
        assertEquals(expectedEvaluations.get(0).getId(), actualEvaluations.get(0).getId(), "The IDs of the evaluations should match.");
    }
}
