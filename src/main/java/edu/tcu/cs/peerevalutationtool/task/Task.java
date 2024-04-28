package edu.tcu.cs.peerevalutationtool.task;

import edu.tcu.cs.peerevalutationtool.domain.Team;
import edu.tcu.cs.peerevalutationtool.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotBlank(message = "Planned task description cannot be empty")
    private String plannedTask;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Planned hours must be provided")
    private Double plannedHours;

    private Double actualHours; // No @NotNull since actual hours can be null if the task hasn't started yet

    @NotBlank(message = "Status cannot be empty")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Student_id") /////////////////////?????/////////
    private Student student;

    private String week;

    // Constructors
    public Task() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlannedTask() {
        return plannedTask;
    }

    public void setPlannedTask(String plannedTask) {
        this.plannedTask = plannedTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(Double plannedHours) {
        this.plannedHours = plannedHours;
    }

    public Double getActualHours() {
        return actualHours;
    }

    public void setActualHours(Double actualHours) {
        this.actualHours = actualHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    // Additional logic or helper methods can be added as needed
}

