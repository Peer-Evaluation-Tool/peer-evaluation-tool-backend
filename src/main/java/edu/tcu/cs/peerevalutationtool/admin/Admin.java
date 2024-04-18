package edu.tcu.cs.peerevalutationtool.admin;

import edu.tcu.cs.peerevalutationtool.section.Section;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin implements Serializable {

    @Id
    private Integer id;

    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "overseer")
    private List<Section> sections = new ArrayList<>();

    public Admin() {
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

    public Integer getNumberOfSections(){
        return this.sections.size();
    }

    public void addSection(Section section) {

        section.setOverseer(this);
        this.sections.add(section);

    }
}
