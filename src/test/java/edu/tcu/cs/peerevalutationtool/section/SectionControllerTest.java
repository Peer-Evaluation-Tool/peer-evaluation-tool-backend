package edu.tcu.cs.peerevalutationtool.section;

import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class SectionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SectionService sectionService;

    List<Section> sections;

    @BeforeEach
    void setUp() {
        this.sections = new ArrayList<>();

        Section sec1 = new Section();
        sec1.setId("Section 2017-2018");
        sec1.setYear("2017-2018");
        this.sections.add(sec1);

        Section sec2 = new Section();
        sec2.setId("Section 2018-2019");
        sec2.setYear("2018-2019");
        this.sections.add(sec2);

        Section sec3 = new Section();
        sec3.setId("Section 2019-2020");
        sec3.setYear("2019-2020");
        this.sections.add(sec3);

        Section sec4 = new Section();
        sec4.setId("Section 2020-2021");
        sec4.setYear("2020-2021");
        this.sections.add(sec4);

        Section sec5 = new Section();
        sec5.setId("Section 2021-2022");
        sec5.setYear("2021-2022");
        this.sections.add(sec5);

        Section sec6 = new Section();
        sec6.setId("Section 2022-2023");
        sec6.setYear("2022-2023");
        this.sections.add(sec6);

        Section sec7 = new Section();
        sec7.setId("Section 2023-2024");
        sec7.setYear("2023-2024");
        this.sections.add(sec7);

        Section sec8 = new Section();
        sec8.setId("Section 2023-2024-2");
        sec8.setYear("2023-2024");
        this.sections.add(sec8);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindSectionByIdSuccess() throws Exception {
        // Given
        given(this.sectionService.findById("Section 2023-2024")).willReturn(this.sections.get(6));

        // When and then
        this.mockMvc.perform(get("/api/v1/sections/Section 2023-2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("Section 2023-2024"));
    }

    @Test
    void testFindSectionByIdNotFound() throws Exception {
        // Given
        given(this.sectionService.findById("Section 2023-2024")).willThrow(new SectionNotFoundException("Section 2023-2024"));

        // When and then
        this.mockMvc.perform(get("/api/v1/sections/Section 2023-2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find section with name Section 2023-2024 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void testFindSectionByYearrSuccess() throws Exception {
        // Given
        given(this.sectionService.findByYearr("2023-2024")).willReturn(this.sections.get(6));

        // When and then
        this.mockMvc.perform(get("/api/v1/sections/yr/2023-2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.yearr").value("2023-2024"));
    }

    @Test
    void testFindSectionByYearrNotFound() throws Exception {
        // Given
        given(this.sectionService.findByYearr("2023-2024")).willThrow(new SectionNotFoundByYearrException("2023-2024"));

        // When and then
        this.mockMvc.perform(get("/api/v1/sections/yr/2023-2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find section with year 2023-2024 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void testFindSectionByIdAndYearrSuccess() throws Exception {
        // Given
        given(this.sectionService.findByIdAndYearr("Section 2023-2024", "2023-2024")).willReturn(this.sections.get(6));

        // When and then
        this.mockMvc.perform(get("/api/v1/sections/secyr/Section 2023-2024+2023-2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("Section 2023-2024"))
                .andExpect(jsonPath("$.data.yearr").value("2023-2024"));
    }

    @Test
    void testFindSectionByIdAndYearrNotFound() throws Exception {
        // Given
        given(this.sectionService.findByIdAndYearr("Section 2023-2024","2023-2024")).willThrow(new SectionNotFoundByIdAndYearrException("Section 2023-2024","2023-2024"));

        // When and then
        this.mockMvc.perform(get("/api/v1/sections/secyr/Section 2023-2024+2023-2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find section with Id Section 2023-2024 and year 2023-2024 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void testFindAllSectionsSuccess() throws Exception {
        // Given
        given(this.sectionService.findAll()).willReturn(this.sections);

        // When and Then
        this.mockMvc.perform(get("/api/v1/sections").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data").value(Matchers.hasSize(this.sections.size())))
                .andExpect(jsonPath("$.data[0].id").value("Section 2017-2018"))
                .andExpect(jsonPath("$.data[0].yearr").value("2017-2018"))
                .andExpect(jsonPath("$.data[1].id").value("Section 2018-2019"))
                .andExpect(jsonPath("$.data[1].yearr").value("2018-2019"));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void testFindAllSectionsByYearrSuccess() throws Exception {
        // Given
        given(this.sectionService.findAllByYearr("2023-2024")).willReturn(this.sections.subList(6,8));

        // When and Then
        this.mockMvc.perform(get("/api/v1/sections/allbyyear/2023-2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All By Year Success"))
                .andExpect(jsonPath("$.data").value(Matchers.hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value("Section 2023-2024"))
                .andExpect(jsonPath("$.data[0].yearr").value("2023-2024"))
                .andExpect(jsonPath("$.data[1].id").value("Section 2023-2024-2"))
                .andExpect(jsonPath("$.data[1].yearr").value("2023-2024"));
    }

}