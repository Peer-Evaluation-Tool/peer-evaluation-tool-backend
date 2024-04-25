package edu.tcu.cs.peerevalutationtool.peerEvaluation.DTO;

import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.student.dto.StudentDto;

public record PeerEvaluationDto(Long id,
                                Integer qualityOfWork,
                                String publicComments,
                                String privateComments,
                                String week,
                                StudentDto evaluator,
                                StudentDto evaluatee) {
}
