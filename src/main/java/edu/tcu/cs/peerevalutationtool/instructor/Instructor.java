package edu.tcu.cs.peerevalutationtool.instructor;

import edu.tcu.cs.peerevalutationtool.section.Section;
import edu.tcu.cs.peerevalutationtool.team.Team;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Instructor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //automatically generate Id starting from default 1
    private Integer id;

    private String name;

//    private String password;

    //when one instructor is saved, all sections for that instructor is saved as well.
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<Section> sections = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "instructor")
    private List<Team> teams = new ArrayList<>();

    public Instructor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        section.setOwner(this);
        this.sections.add(section);
    }

    public Integer getNumberOfSections() {
        return this.sections.size();
    }

    public void removeAllSections() {
        this.sections.stream().forEach(section -> section.setOwner(null));
        this.sections = null;
    }

    public void removeSection(Section sectionToBeAssigned) {
        //remove section owner
        sectionToBeAssigned.setOwner(null);
        this.sections.remove(sectionToBeAssigned);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {

        team.setInstructor(this);
        this.teams.add(team);

    }
}
