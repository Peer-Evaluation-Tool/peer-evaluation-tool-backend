package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.List;

@Entity
public class Section implements Serializable {

    @Id
    private String id;

    private String name;

//    private List<String> activeWeeks;

    private String year;

    @ManyToOne
    private Admin overseer;

    public Section() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
