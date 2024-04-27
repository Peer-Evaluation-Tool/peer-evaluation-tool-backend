package edu.tcu.cs.peerevalutationtool.repository;

import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevalutationtool.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeerEvaluationRepository extends JpaRepository<PeerEvaluation, Long> {
    /**
     * Retrieves a list of peer evaluations where the given student ID is the evaluatee.
     * @param evaluateeId the ID of the student whose evaluations are being queried
     * @return a list of PeerEvaluation instances
     */
    List<PeerEvaluation> findByEvaluateeId(Long evaluateeId);

    List<PeerEvaluation> findAllByWeek(String peerEvaluationWeek);

    List<PeerEvaluation> findAllByEvaluateeId(Long id);
}
