package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.repository.PeerEvaluationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PeerEvaluationServiceTest {

    @Mock
    private PeerEvaluationRepository peerEvaluationRepository;

    @InjectMocks
    private PeerEvaluationService peerEvaluationService;

    private PeerEvaluation evaluation;
    private Student evaluator;
    private Student evaluatee;

    ArrayList<PeerEvaluation> evaluations;


    @BeforeEach
    public void setup() {
        evaluator = new Student();
        evaluator.setId(1L);
        evaluator.setFirstName("Carlos");
        evaluator.setLastName("Prudhomme");
        evaluatee = new Student();
        evaluatee.setId(2L);
        evaluatee.setFirstName("Eriife");
        evaluatee.setLastName("A");

        evaluation = new PeerEvaluation();
        evaluation.setId(1L);
        evaluation.setEvaluator(evaluator);
        evaluation.setEvaluatee(evaluatee);
        evaluation.setQualityOfWork(9);
        evaluation.setPublicComments("Good job!");
        evaluation.setPrivateComments("Need improvement on documentation.");
        evaluation.setWeek("02-12-2024");

        PeerEvaluation evaluation2 = new PeerEvaluation();
        evaluation2.setId(2L);
        evaluation2.setEvaluator(evaluator);
        evaluation2.setEvaluatee(evaluator);
        evaluation2.setQualityOfWork(10);
        evaluation2.setPublicComments("");
        evaluation2.setPrivateComments("");
        evaluation2.setWeek("02-12-2024");

        PeerEvaluation evaluation3 = new PeerEvaluation();
        evaluation3.setId(3L);
        evaluation3.setEvaluator(evaluator);
        evaluation3.setEvaluatee(evaluator);
        evaluation3.setQualityOfWork(10);
        evaluation3.setPublicComments("");
        evaluation3.setPrivateComments("");
        evaluation3.setWeek("02-12-2024");

        PeerEvaluation evaluation4 = new PeerEvaluation();
        evaluation4.setId(4L);
        evaluation4.setEvaluator(evaluator);
        evaluation4.setEvaluatee(evaluator);
        evaluation4.setQualityOfWork(10);
        evaluation4.setPublicComments("");
        evaluation4.setPrivateComments("");
        evaluation4.setWeek("02-12-2024");

        evaluations = new ArrayList<>();
        evaluations.add(evaluation);
        evaluations.add(evaluation2);
        evaluations.add(evaluation3);
        evaluations.add(evaluation4);


    }

    @Test
    public void testSubmitEvaluation() {
        when(peerEvaluationRepository.save(any(PeerEvaluation.class))).thenReturn(evaluation);

        PeerEvaluation savedEvaluation = peerEvaluationService.submitEvaluation(evaluation);

        verify(peerEvaluationRepository).save(any(PeerEvaluation.class));
        assertNotNull(savedEvaluation, "The saved evaluation should not be null.");
        assertEquals(evaluation.getId(), savedEvaluation.getId(), "The IDs should match.");
        assertEquals(evaluation.getPublicComments(), savedEvaluation.getPublicComments(), "The public comments should match.");
    }

    @Test
    public void testFindEvaluationById() {
        when(peerEvaluationRepository.findById(1L)).thenReturn(Optional.of(evaluation));

        PeerEvaluation foundEvaluation = peerEvaluationService.findEvaluationById(1L);

        verify(peerEvaluationRepository).findById(1L);
        assertNotNull(foundEvaluation);
        assertEquals(1L, foundEvaluation.getId());
        assertEquals(9, foundEvaluation.getQualityOfWork());
    }

    @Test
    public void testFindEvaluationById_NotFound() {
        when(peerEvaluationRepository.findById(1L)).thenReturn(Optional.empty());

        EvaluationNotFoundException exception = assertThrows(EvaluationNotFoundException.class, () -> {
            peerEvaluationService.findEvaluationById(1L);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }
    @Test
    public void testFindEvaluationsByEvaluateeId() {
        List<PeerEvaluation> expectedEvaluations = Arrays.asList(evaluation);

        // Setup the peerEvaluationRepository mock to return a predefined list of evaluations when queried for a specific evaluatee
        when(peerEvaluationRepository.findByEvaluateeId(evaluatee.getId())).thenReturn(expectedEvaluations);

        // Call the service method
        List<PeerEvaluation> actualEvaluations = peerEvaluationService.findEvaluationsByEvaluateeId(evaluatee.getId());

        // Verify the interaction with the peerEvaluationRepository
        verify(peerEvaluationRepository).findByEvaluateeId(evaluatee.getId());

        // Assert that the returned evaluations are what we expect
        assertNotNull(actualEvaluations, "The returned list should not be null.");
        assertFalse(actualEvaluations.isEmpty(), "The returned list should not be empty.");
        assertEquals(expectedEvaluations.size(), actualEvaluations.size(), "The size of the returned list should match the expected list.");
        assertEquals(expectedEvaluations.get(0).getId(), actualEvaluations.get(0).getId(), "The IDs of the evaluations should match.");
    }

    @Test
    void testFindAllByWeekSuccess(){
        // Given
        given(peerEvaluationRepository.findAllByWeek("02-12-2024")).willReturn(this.evaluations);

        // When
        List<PeerEvaluation> actualPeerEvaluations = peerEvaluationService.findAllByWeek("02-12-2024");

        // Then
        assertThat(actualPeerEvaluations.size()).isEqualTo(this.evaluations.size());
        verify(peerEvaluationRepository, times(1)).findAllByWeek("02-12-2024");
    }
    @Test
    void testFindAllByStudent(){
        // Given
        given(peerEvaluationRepository.findAllByEvaluateeId(1L)).willReturn(this.evaluations.subList(0,2));

        // When
        List<PeerEvaluation> actualPeerEvaluations = peerEvaluationService.findAllByEvaluateeId(1L);

        // Then
        assertThat(actualPeerEvaluations.size()).isEqualTo(this.evaluations.subList(0,2).size());
        verify(peerEvaluationRepository, times(1)).findAllByEvaluateeId(1L);
    }
}
