package edu.tcu.cs.peerevalutationtool.section;

public class SectionNotFoundByIdAndYearException extends RuntimeException {
    public SectionNotFoundByIdAndYearException(String sectionId, String sectionYear) {
        super("Could not find section with Id " + sectionId + " and year " + sectionYear + " :(");
    }
}
