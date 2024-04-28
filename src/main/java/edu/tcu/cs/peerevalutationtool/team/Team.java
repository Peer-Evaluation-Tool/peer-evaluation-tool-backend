package edu.tcu.cs.peerevalutationtool.team;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.student.Student;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team implements Serializable {
    @Id
    private String id; //Team name is id.
    @ManyToOne
    private Admin overseer;
    @ManyToOne
    private Instructor instructor;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "team")
    private List<Student> students = new ArrayList<>();
    @ManyToOne
    private Section section;
    private String academicYear;
    public Team() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Admin getOverseer() {
        return overseer;
    }

    public void setOverseer(Admin overseer) {
        this.overseer = overseer;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Section getSection() {return section;}

    public void setSection(Section section) {this.section = section;}

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
}
