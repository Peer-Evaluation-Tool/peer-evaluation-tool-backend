package edu.tcu.cs.peerevalutationtool.peerEvaluation.DTO;

public class PeerEvaluationReportDTO {
    private String studentName;
    private Double qualityOfWorkAverage;
    private String publicComments;
    private Integer overallGrade;
    private String week;

    // Constructors
    public PeerEvaluationReportDTO() {
    }

    public PeerEvaluationReportDTO(String studentName, Double qualityOfWorkAverage, String publicComments, Integer overallGrade, String week) {
        this.studentName = studentName;
        this.qualityOfWorkAverage = qualityOfWorkAverage;
        this.publicComments = publicComments;
        this.overallGrade = overallGrade;
        this.week = week;
    }

    // Getters
    public String getStudentName() {
        return studentName;
    }

    public Double getQualityOfWorkAverage() {
        return qualityOfWorkAverage;
    }

    public String getPublicComments() {
        return publicComments;
    }

    public Integer getOverallGrade() {
        return overallGrade;
    }

    // Setters
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setQualityOfWorkAverage(Double qualityOfWorkAverage) {
        this.qualityOfWorkAverage = qualityOfWorkAverage;
    }

    public void setPublicComments(String publicComments) {
        this.publicComments = publicComments;
    }

    public void setOverallGrade(Integer overallGrade) {
        this.overallGrade = overallGrade;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
