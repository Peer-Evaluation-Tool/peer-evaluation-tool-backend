package edu.tcu.cs.peerevalutationtool.section;

public class SectionNotFoundByYearException extends RuntimeException{

    public SectionNotFoundByYearException(String year){
        super("Could not find section with year " + year + " :(");
    }

}
