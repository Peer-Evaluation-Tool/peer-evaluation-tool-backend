package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.repository.PeerEvaluationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PeerEvaluationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PeerEvaluationRepository repository;

    @Test
    public void testSaveAndFindById() {
        Student evaluator = new Student();
        evaluator.setEmail("evaluator@example.com");
        evaluator.setFirstName("John");
        evaluator.setLastName("Doe");
        evaluator.setPassword("securePassword123");  // Set a password to meet validation requirements
        entityManager.persist(evaluator);

        Student evaluatee = new Student();
        evaluatee.setEmail("evaluatee@example.com");
        evaluatee.setFirstName("Jane");
        evaluatee.setLastName("Doe");
        evaluatee.setPassword("securePassword123");  // Set a password to meet validation requirements
        entityManager.persist(evaluatee);

        PeerEvaluation evaluation = new PeerEvaluation();
        evaluation.setEvaluator(evaluator);
        evaluation.setEvaluatee(evaluatee);
        evaluation.setQualityOfWork(10);  // Assuming the scale is from 1 to 10
        evaluation.setPublicComments("Great work!");  // Provide some dummy public comments
        evaluation = entityManager.persistAndFlush(evaluation);

        PeerEvaluation foundEvaluation = repository.findById(evaluation.getId()).orElse(null);
        assertNotNull(foundEvaluation);
        assertEquals(evaluator.getEmail(), foundEvaluation.getEvaluator().getEmail());
        assertEquals(10, foundEvaluation.getQualityOfWork());
        assertEquals("Great work!", foundEvaluation.getPublicComments());
    }

    @Test
    public void testFindByEvaluateeId() {
        // Create and persist the evaluator
        Student evaluator = new Student();
        evaluator.setEmail("evaluator@example.com");
        evaluator.setFirstName("John");
        evaluator.setLastName("Doe");
        evaluator.setPassword("securePassword123");
        entityManager.persist(evaluator);

        // Create and persist the evaluatee
        Student evaluatee = new Student();
        evaluatee.setEmail("evaluatee@example.com");
        evaluatee.setFirstName("Jane");
        evaluatee.setLastName("Doe");
        evaluatee.setPassword("securePassword123");
        entityManager.persist(evaluatee);

        // Create and persist another student to ensure our query is specific
        Student otherStudent = new Student();
        otherStudent.setEmail("other@example.com");
        otherStudent.setFirstName("Jim");
        otherStudent.setLastName("Beam");
        otherStudent.setPassword("anotherSecurePassword123");
        entityManager.persist(otherStudent);

        // Create peer evaluations
        PeerEvaluation evaluation1 = new PeerEvaluation();
        evaluation1.setEvaluator(evaluator);
        evaluation1.setEvaluatee(evaluatee);
        evaluation1.setQualityOfWork(9);
        evaluation1.setPublicComments("Excellent work!");
        entityManager.persist(evaluation1);

        PeerEvaluation evaluation2 = new PeerEvaluation();
        evaluation2.setEvaluator(evaluator);
        evaluation2.setEvaluatee(evaluatee);
        evaluation2.setQualityOfWork(8);
        evaluation2.setPublicComments("Very good!");
        entityManager.persist(evaluation2);

        // Create a third evaluation for the other student to confirm query specificity
        PeerEvaluation otherEvaluation = new PeerEvaluation();
        otherEvaluation.setEvaluator(evaluator);
        otherEvaluation.setEvaluatee(otherStudent);
        otherEvaluation.setQualityOfWork(7);
        otherEvaluation.setPublicComments("Good effort!");
        entityManager.persist(otherEvaluation);

        entityManager.flush();

        // Test findByEvaluateeId
        List<PeerEvaluation> evaluations = repository.findByEvaluateeId(evaluatee.getId());
        assertNotNull(evaluations);
        assertEquals(2, evaluations.size()); // Check that only two evaluations are found for the specified evaluatee
        assertTrue(evaluations.stream().allMatch(e -> e.getEvaluatee().getId().equals(evaluatee.getId())));

        // Clean up the persisted data if necessary
        entityManager.clear();
    }
}