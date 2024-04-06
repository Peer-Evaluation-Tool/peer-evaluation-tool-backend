package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.admin.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SectionServiceTest {

    @Mock
    SectionRepository sectionRepository;

    @InjectMocks
    SectionService sectionService;

    List<Section> sections;

    @BeforeEach
    void setUp() {
        Section sec1 = new Section();
        sec1.setId("Section 2022-2023");
        sec1.setYear("2023-2024");

        Section sec2 = new Section();
        sec2.setId("Section 2023-2024");
        sec2.setYear("2023-2024");

        this.sections = new ArrayList<>();
        this.sections.add(sec1);
        this.sections.add(sec2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByNameSuccess() {
        // Given. Arrange inputs and targets. Define the behavior of Mock object sectionRepository.
        /*
		    “name”: “Senior Section 2023-2024”,
		    “year”: “2023-2024”,
         */
        Section sec = new Section();
        sec.setId("Section 2023-2024");
        sec.setYear("2023-2024");

        Admin adm = new Admin();
        adm.setId(1);
        adm.setName("Bingyang Wei");

        sec.setOverseer(adm);

        given(sectionRepository.findById("Section 2023-2024")).willReturn(Optional.of(sec)); // Defines the behavior of the mock object.

        // When. Act on the target behavior. When steps should cover the method to be tested.
        Section returnedSection = sectionService.findById("Section 2023-2024");

        // Then. Assert expected outcomes.
        assertThat(returnedSection.getId()).isEqualTo(sec.getId());
        assertThat(returnedSection.getYear()).isEqualTo(sec.getYear());
        verify(sectionRepository, times(1)).findById("Section 2023-2024");

    }

    @Test
    void testFindByNameNotFound(){
        // Given.
        given(sectionRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When.
        Throwable thrown = catchThrowable(()->{
            Section returnedSection = sectionService.findById("Section 2023-2024");
        });

        // Then.
        assertThat(thrown)
                .isInstanceOf(SectionNotFoundException.class)
                .hasMessage("Could not find section with name Section 2023-2024 :(");
        verify(sectionRepository, times(1)).findById("Section 2023-2024");

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void testFindByYearSuccess() {
        // Given. Arrange inputs and targets. Define the behavior of Mock object sectionRepository.
        /*
		    “name”: “Senior Section 2023-2024”,
		    “year”: “2023-2024”,
         */
        Section sec = new Section();
        sec.setId("Section 2023-2024");
        sec.setYear("2023-2024");

        Admin adm = new Admin();
        adm.setId(1);
        adm.setName("Bingyang Wei");

        sec.setOverseer(adm);

        given(sectionRepository.findByYearr("2023-2024")).willReturn(Optional.of(sec)); // Defines the behavior of the mock object.

        // When. Act on the target behavior. When steps should cover the method to be tested.
        Section returnedSection = sectionService.findByYearr("2023-2024");

        // Then. Assert expected outcomes.
        assertThat(returnedSection.getId()).isEqualTo(sec.getId());
        assertThat(returnedSection.getYear()).isEqualTo(sec.getYear());
        verify(sectionRepository, times(1)).findByYearr("2023-2024");

    }

    @Test
    void testFindByYearNotFound(){
        // Given.
        given(sectionRepository.findByYearr(Mockito.any(String.class))).willReturn(Optional.empty());

        // When.
        Throwable thrown = catchThrowable(()->{
            Section returnedSection = sectionService.findByYearr("2023-2024");
        });

        // Then.
        assertThat(thrown)
                .isInstanceOf(SectionNotFoundByYearrException.class)
                .hasMessage("Could not find section with year 2023-2024 :(");
        verify(sectionRepository, times(1)).findByYearr("2023-2024");
    }

    @Test
    void testFindAllSuccess(){
        // Given
        given(sectionRepository.findAll()).willReturn(this.sections);

        // When
        List<Section> actualSections = sectionService.findAll();

        // Then
        assertThat(actualSections.size()).isEqualTo(this.sections.size());
        verify(sectionRepository, times(1)).findAll();
    }

    @Test
    void testFindAllByYearrSuccess(){
        // Given
        given(sectionRepository.findAllByYearr("2023-2024")).willReturn(this.sections);

        // When
        List<Section> actualSections = sectionService.findAllByYearr("2023-2024");

        // Then
        assertThat(actualSections.size()).isEqualTo(this.sections.size());
        verify(sectionRepository, times(1)).findAllByYearr("2023-2024");
    }
}