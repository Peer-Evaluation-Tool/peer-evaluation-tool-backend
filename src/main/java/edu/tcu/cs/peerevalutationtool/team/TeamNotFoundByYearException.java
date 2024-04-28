package edu.tcu.cs.peerevalutationtool.team;

public class TeamNotFoundByYearException extends RuntimeException {
    public TeamNotFoundByYearException(String year) {
        super("Could not find team with year " + year + " :(");
    }
}
