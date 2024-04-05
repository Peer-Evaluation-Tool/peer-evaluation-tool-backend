package edu.tcu.cs.peerevalutationtool.section;

public class SectionNotFoundByIdAndYearrException extends RuntimeException {
    public SectionNotFoundByIdAndYearrException(String sectionId, String sectionYear) {
        super("Could not find section with Id " + sectionId + " and year " + sectionYear + " :(");
    }
}
