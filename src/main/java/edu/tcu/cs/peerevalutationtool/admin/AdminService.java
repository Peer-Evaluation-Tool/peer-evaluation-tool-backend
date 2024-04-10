package edu.tcu.cs.peerevalutationtool.admin;

import edu.tcu.cs.peerevalutationtool.domain.Student;
import edu.tcu.cs.peerevalutationtool.domain.Team;
import edu.tcu.cs.peerevalutationtool.repository.StudentRepository;
import edu.tcu.cs.peerevalutationtool.repository.TeamRepository;
import edu.tcu.cs.peerevalutationtool.system.email.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final EmailService emailService;
    private final StudentRepository studentRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public AdminService(EmailService emailService, StudentRepository studentRepository, TeamRepository teamRepository) {
        this.emailService = emailService;
        this.studentRepository = studentRepository;
        this.teamRepository = teamRepository;
    }

    public void inviteStudents(List<String> studentEmails, String adminName, String adminEmail) {
        String subject = "Welcome to The Peer Evaluation Tool - Complete Your Registration";
        studentEmails.forEach(email -> {
            String content = generateEmailContent(email, adminName, adminEmail);
            try {
                emailService.sendEmail(email, subject, content);
            } catch (MessagingException e) {
                e.printStackTrace();
                // Implement more robust error handling here
            }
        });
    }

    public void assignStudentsToTeam(Long teamId, List<Long> studentIds) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("Team not found."));
        List<Student> students = studentRepository.findAllById(studentIds);

        students.forEach(student -> {
            student.setTeam(team);
            studentRepository.save(student);
            sendTeamAssignmentNotification(student);
        });
    }

    public void removeStudentFromTeam(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        student.setTeam(null); // Assuming a null team means the student is not part of any team
        studentRepository.save(student);

        sendTeamRemovalNotification(student);
    }

    public void deleteSeniorDesignTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + teamId));

        // Notify relevant actors before deletion
       // notifyTeamDeletion(team);

        // Delete team and associated data
        teamRepository.delete(team);
    }

   /* private void notifyTeamDeletion(Team team) {
        // Notify students of the deleted team

        List <Student> students = team.getStudents();
        students.forEach(student -> {
            try {
                String subject = "Team Deletion Notification";
                String content = String.format("Hello %s,\n\nThe senior design team '%s' you were part of has been deleted from the system. Please contact the administration for further assistance.\n\nBest regards,\nPeer Evaluation Tool Team", student.getName(), team.getName());
                emailService.sendEmail(student.getEmail(), subject, content);
            } catch (MessagingException e) {
                e.printStackTrace(); // Log or handle this exception appropriately
            }
        });

        // Notify instructors if needed
        // Example: List<Instructor> instructors = team.getInstructors();
    }*/

    private void sendTeamRemovalNotification(Student student) {
        String subject = "Team Removal Notification";
        String content = String.format("Hello %s,\n\nYou have been removed from your team. Please contact the administration for further details.\n\nBest regards,\nPeer Evaluation Tool Team", student.getName());
        try {
            emailService.sendEmail(student.getEmail(), subject, content);
        } catch (MessagingException e) {
            e.printStackTrace(); // Log this exception or handle it according to your error handling policy
        }
    }

    private void sendTeamAssignmentNotification(Student student) {
        String subject = "Team Assignment Notification";
        String content = String.format("Hello %s,\n\nYou have been assigned to team '%s'. Please log in to the platform to view your team details and start collaborating with your teammates.\n\nBest regards,\nThe Peer Evaluation Tool Team", student.getName(), student.getTeam().getName());
        try {
            emailService.sendEmail(student.getEmail(), subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Implement more robust error handling here
        }
    }

    private String generateEmailContent(String studentEmail, String adminName, String adminEmail) {
        String registrationLink = "http://example.com/register?email=" + studentEmail;
        return "Hello,\n\n" + adminName + " has invited you to join The Peer Evaluation Tool. To complete your registration, please use the link below:\n\n" +
                registrationLink + "\n\nIf you have any questions or need assistance, feel free to contact " + adminEmail + " or our team directly.\n\nPlease note: This email is not monitored, so do not reply directly to this message.\n\nBest regards,\nPeer Evaluation Tool Team";
    }
    public List<Student> findStudentsByCriteria(String firstName, String lastName, String sectionName,
                                                String academicYear, String teamName) {
        // Dummy implementation until student information is available
        List<Student> students = new ArrayList<>();
        // Add dummy student data
       // students.add(new Student("John", "Doe", "Section A", "2023", "Team Alpha"));
        //students.add(new Student("Jane", "Smith", "Section B", "2022", "Team Beta"));
        // You can add more dummy students as needed
        return students;
    }}
