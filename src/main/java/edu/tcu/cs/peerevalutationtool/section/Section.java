package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.instructor.Instructor;
import edu.tcu.cs.peerevalutationtool.team.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class Section implements Serializable {

    @Id
    private String id; // The section name is the ID!!!

    @Column(name = "`year`")
    private String year;

    @ManyToOne
    private Admin overseer;

    @ManyToOne
    private Instructor owner;
    private String firstDate;

    private String lastDate;

    @ElementCollection
    private List<String> activeWeeks =  new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "section")
    private List<Team> teams  =  new ArrayList<>();
//    private ArrayList<LocalDate> activeWeeks =  WeekGenerator.generateWeeks(this.firstDate, this.lastDate);


//    private Rubric rubric


    public Section() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public List<String> getActiveWeeks() {
        return activeWeeks;
    }

    public void setActiveWeeks(ArrayList<String> activeWeeks) {
        this.activeWeeks = activeWeeks;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    //    public ArrayList<LocalDate> getActiveWeeks() {
//        return activeWeeks;
//    }

    // Might have to refactor
//    public void setActiveWeeks(ArrayList<LocalDate> activeWeeks) {
//        this.activeWeeks = activeWeeks;
//    }

//    public void populateActiveWeeks() {
//        this.activeWeeks = WeekGenerator.generateWeeks(this.firstDate, this.lastDate);
//    }

//    public void dropActiveWeeks(HashSet<Integer> indices){
//        ArrayList<LocalDate> newWeeks = new ArrayList<>();
//        for(int i = 0; i < this.activeWeeks.size(); i++){
//            if (!indices.contains(i))
//                newWeeks.add(this.activeWeeks.get(i));
//        }
//        this.activeWeeks = newWeeks;
//    }

    public Admin getOverseer() {
        return overseer;
    }

    public void setOverseer(Admin overseer) {
        this.overseer = overseer;
    }

    public Instructor getOwner() { return owner; }
    public void setOwner(Instructor owner) { this.owner = owner; }

}
