package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class PeerEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id")
    private Student evaluator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluatee_id")
    private Student evaluatee;

    @NotNull(message = "Quality of work rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must not exceed 10")
    private Integer qualityOfWork;

    @NotBlank(message = "Public comments cannot be empty")
    private String publicComments;

    private String privateComments;

    public PeerEvaluation() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Student evaluator) {
        this.evaluator = evaluator;
    }

    public Student getEvaluatee() {
        return evaluatee;
    }

    public void setEvaluatee(Student evaluatee) {
        this.evaluatee = evaluatee;
    }

    public Integer getQualityOfWork() {
        return qualityOfWork;
    }

    public void setQualityOfWork(Integer qualityOfWork) {
        this.qualityOfWork = qualityOfWork;
    }

    public String getPublicComments() {
        return publicComments;
    }

    public void setPublicComments(String publicComments) {
        this.publicComments = publicComments;
    }

    public String getPrivateComments() {
        return privateComments;
    }

    public void setPrivateComments(String privateComments) {
        this.privateComments = privateComments;
    }
}
