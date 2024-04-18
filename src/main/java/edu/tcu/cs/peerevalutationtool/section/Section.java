package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
public class Section implements Serializable {

    @Id
    private String id; // The section name is the ID!!!

//    private List<String> activeWeeks;

    private String year;

    @ManyToOne
    private Admin overseer;

//    @ManyToOne
    private Instructor owner;

    public Section() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public ArrayList<String> getActiveWeeks() {
//        return activeWeeks;
//    }
//
//    public void setActiveWeeks(ArrayList<String> activeWeeks) {
//        this.activeWeeks = activeWeeks;
//    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Admin getOverseer() {
        return overseer;
    }

    public void setOverseer(Admin overseer) {
        this.overseer = overseer;
    }

    public Instructor getOwner() { return owner; }
    public void setOwner(Instructor owner) { this.owner = owner; }

}
