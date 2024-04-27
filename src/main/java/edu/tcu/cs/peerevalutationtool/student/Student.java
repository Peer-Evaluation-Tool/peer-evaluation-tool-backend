package edu.tcu.cs.peerevalutationtool.student;

import edu.tcu.cs.peerevalutationtool.team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    private String middleInitial;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @ManyToOne
    @JsonIgnore // Consider this if you face issues with JSON serialization
    private Team team;

    public Student() {
    }

    // Optional: Parameterized constructor for easier testing and initialization
    public Student(String firstName, String middleInitial, String lastName, String email, String password) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.email = email;
        setPassword(password); // This will hash the password when setting it
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    // Placeholder for password hashing function
    private String hashPassword(String password) {
        // Implement hashing logic here
        return password; // Return hashed password
    }
}
