package edu.tcu.cs.peerevalutationtool.section;

public class SectionNotFoundByYearrException extends RuntimeException{

    public SectionNotFoundByYearrException(String year){
        super("Could not find section with year " + year + " :(");
    }

}
