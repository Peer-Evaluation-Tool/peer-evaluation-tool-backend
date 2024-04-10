package edu.tcu.cs.peerevalutationtool.system.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    private MimeMessage mimeMessage;

    @BeforeEach
    public void setup() {
        mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    public void testSendEmail() throws Exception {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String content = "Test Content";

        // Act
        emailService.sendEmail(to, subject, content);

        // Assert
        verify(javaMailSender).send(mimeMessage);
        // Additional verification steps as needed
    }
}

