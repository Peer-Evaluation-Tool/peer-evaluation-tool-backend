package edu.tcu.cs.peerevalutationtool.instructor;

import edu.tcu.cs.peerevalutationtool.section.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import edu.tcu.cs.peerevalutationtool.section.utils.IdWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

    @Mock
    InstructorRepository instructorRepository;



    @InjectMocks
    InstructorService instructorService;

    List<Instructor> instructors;

    //    @Mock
    //    SectionRepository sectionRepository;

    @BeforeEach
    void setUp() {
        Instructor i1 = new Instructor();
        i1.setId(1);
        i1.setName("Bingyang Wei");

        Instructor i2 = new Instructor();
        i2.setId(2);
        i2.setName("Liran Ma");

        this.instructors = new ArrayList<>();
        this.instructors.add(i1);
        this.instructors.add(i2);

    }

    @Test //Add an instructor
    void testSaveSuccess(){
        // Given
        Instructor newInstructor = new Instructor();
        newInstructor.setId(1);
        newInstructor.setName("Bingyang Wei");

        given(instructorRepository.save(newInstructor)).willReturn(newInstructor);

        // When
        Instructor savedInstructor = instructorService.save(newInstructor);

        // Then
        assertThat(savedInstructor.getId()).isEqualTo(1);
        assertThat(savedInstructor.getName()).isEqualTo("Bingyang Wei");
        verify(instructorRepository, times(1)).save(newInstructor);

    }
}
