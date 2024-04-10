package edu.tcu.cs.peerevalutationtool.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    // You can add more fields representing the team's properties here

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    // Constructors
    public Team() {
    }

    // If you have fields other than 'id' and 'students', consider adding a constructor for them

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    // Helper methods can be added here, e.g., to add a student to a team
    public void addStudent(Student student) {
        this.students.add(student);
        student.setTeam(this);
    }
}
