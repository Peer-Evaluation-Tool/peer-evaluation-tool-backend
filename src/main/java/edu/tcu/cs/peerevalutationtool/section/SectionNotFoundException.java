package edu.tcu.cs.peerevalutationtool.section;

public class SectionNotFoundException extends RuntimeException{

    public SectionNotFoundException(String id){
        super("Could not find section with name " + id + " :(");
    }

}
