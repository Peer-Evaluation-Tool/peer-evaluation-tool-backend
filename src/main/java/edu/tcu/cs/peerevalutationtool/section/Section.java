package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Section implements Serializable {

    @Id
    private String id; // The section name is the ID!!!

//    private ArrayList<String> activeWeeks =  new ArrayList<>();
    ;

    @Column(name = "`year`")
    private String year;

    @ManyToOne
    private Admin overseer;

//    private Rubric rubric

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

}
