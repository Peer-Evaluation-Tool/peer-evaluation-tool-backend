package edu.tcu.cs.peerevalutationtool.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/inviteStudents")
    public ResponseEntity<?> inviteStudents(@RequestBody List<String> studentEmails) {
        // For simplicity, the admin name and email are hardcoded. Consider retrieving them from the admin's authentication context.
        String adminName = "Admin Name";
        String adminEmail = "admin@example.com";
        adminService.inviteStudents(studentEmails, adminName, adminEmail);
        return ResponseEntity.ok("Invitations sent successfully.");
    }

    // Endpoint to assign students to a team
    @PostMapping("/assignStudentsToTeam")
    public ResponseEntity<?> assignStudentsToTeam(@RequestParam Long teamId, @RequestBody List<Long> studentIds) {
        try {
            adminService.assignStudentsToTeam(teamId, studentIds);
            return ResponseEntity.ok().body("Students have been successfully assigned to the team.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to remove a student from a team
    @DeleteMapping("/removeStudentFromTeam/{studentId}")
    public ResponseEntity<?> removeStudentFromTeam(@PathVariable Long studentId) {
        try {
            adminService.removeStudentFromTeam(studentId);
            return ResponseEntity.ok("Student removed from the team successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }

    // Endpoint to delete a senior design team
    @DeleteMapping("/deleteSeniorDesignTeam/{teamId}")
    public ResponseEntity<?> deleteSeniorDesignTeam(@PathVariable Long teamId) {
        try {
            adminService.deleteSeniorDesignTeam(teamId);
            return ResponseEntity.ok("Senior design team deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }
}
