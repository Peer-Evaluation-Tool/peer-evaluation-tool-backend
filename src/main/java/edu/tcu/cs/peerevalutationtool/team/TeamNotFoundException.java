package edu.tcu.cs.peerevalutationtool.team;

public class TeamNotFoundException extends RuntimeException{

    public TeamNotFoundException(String id){
        super("Could not find team with name " + id + " :(");
    }

}
