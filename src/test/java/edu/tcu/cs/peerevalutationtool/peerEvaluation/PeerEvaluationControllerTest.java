package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.peerEvaluation.DTO.PeerEvaluationDto;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.converter.EvaluationToEvaluationDtoConverter;
import edu.tcu.cs.peerevalutationtool.student.Student;
import edu.tcu.cs.peerevalutationtool.student.converter.StudentToAdminDtoConverter;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(PeerEvaluationController.class)
public class PeerEvaluationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeerEvaluationService peerEvaluationService;

    List<PeerEvaluation> evaluations;
    private EvaluationToEvaluationDtoConverter evaluationToEvaluationDtoConverter;
    private StudentToAdminDtoConverter studentToAdminDtoConverter;
    private Student evaluator;
    private Student evaluatee;

    @BeforeEach
    void setUp() {
        studentToAdminDtoConverter = new StudentToAdminDtoConverter();
        evaluationToEvaluationDtoConverter = new EvaluationToEvaluationDtoConverter(studentToAdminDtoConverter);

        evaluator = new Student();
        evaluator.setId(1L);
        evaluator.setFirstName("Carlos");
        evaluator.setLastName("Prudhomme");
        evaluatee = new Student();
        evaluatee.setId(2L);
        evaluatee.setFirstName("Eriife");
        evaluatee.setLastName("A");

        PeerEvaluation evaluation = new PeerEvaluation();
        evaluation.setId(1L);
        evaluation.setEvaluator(evaluator);
        evaluation.setEvaluatee(evaluatee);
        evaluation.setQualityOfWork(9);
        evaluation.setPublicComments("Good job!");
        evaluation.setPrivateComments("Need improvement on documentation.");
        evaluation.setWeek("02-12-2024");

        PeerEvaluation evaluation2 = new PeerEvaluation();
        evaluation2.setId(2L);
        evaluation2.setEvaluator(evaluator);
        evaluation2.setEvaluatee(evaluator);
        evaluation2.setQualityOfWork(10);
        evaluation2.setPublicComments("");
        evaluation2.setPrivateComments("");
        evaluation2.setWeek("02-12-2024");

        PeerEvaluation evaluation3 = new PeerEvaluation();
        evaluation3.setId(3L);
        evaluation3.setEvaluator(evaluator);
        evaluation3.setEvaluatee(evaluator);
        evaluation3.setQualityOfWork(10);
        evaluation3.setPublicComments("");
        evaluation3.setPrivateComments("");
        evaluation3.setWeek("02-12-2024");

        PeerEvaluation evaluation4 = new PeerEvaluation();
        evaluation4.setId(4L);
        evaluation4.setEvaluator(evaluatee);
        evaluation4.setEvaluatee(evaluatee);
        evaluation4.setQualityOfWork(10);
        evaluation4.setPublicComments("");
        evaluation4.setPrivateComments("");
        evaluation4.setWeek("02-12-2024");

        evaluations = new ArrayList<>();
        evaluations.add(evaluation);
        evaluations.add(evaluation2);
        evaluations.add(evaluation3);
        evaluations.add(evaluation4);
    }

    @Test
    public void testSubmitEvaluation() throws Exception {
        PeerEvaluation evaluation = new PeerEvaluation();
        given(peerEvaluationService.submitEvaluation(any(PeerEvaluation.class))).willReturn(evaluation);

        mockMvc.perform(post("/peer-evaluations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"evaluatorId\":1,\"evaluateeId\":2,\"qualityOfWork\":10,\"publicComments\":\"Great work!\",\"privateComments\":\"Needs improvement on punctuality.\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetEvaluation() throws Exception {
        PeerEvaluation evaluation = new PeerEvaluation();
        given(peerEvaluationService.findEvaluationById(1L)).willReturn(evaluation);

        mockMvc.perform(get("/peer-evaluations/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPeerEvaluationReport() throws Exception {
        Long studentId = 1L; // Example student ID
        PeerEvaluation evaluation1 = new PeerEvaluation();
        evaluation1.setQualityOfWork(8);
        evaluation1.setPublicComments("Good effort!");
        PeerEvaluation evaluation2 = new PeerEvaluation();
        evaluation2.setQualityOfWork(9);
        evaluation2.setPublicComments("Excellent teamwork!");

        List<PeerEvaluation> evaluations = Arrays.asList(evaluation1, evaluation2);

        given(peerEvaluationService.findEvaluationsByEvaluateeId(studentId)).willReturn(evaluations);

        mockMvc.perform(get("/peer-evaluations/reports/" + studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].qualityOfWork").value(8))
                .andExpect(jsonPath("$[0].publicComments").value("Good effort!"))
                .andExpect(jsonPath("$[1].qualityOfWork").value(9))
                .andExpect(jsonPath("$[1].publicComments").value("Excellent teamwork!"));
    }

    @Test
    void testFindAllPeerEvaluationsByWeekSuccess() throws Exception {
        // Given
        given(this.peerEvaluationService.findAllByWeek("02-12-2024")).willReturn(this.evaluations);

        // When and then
        this.mockMvc.perform(get("/peer-evaluations/reports/allbyweek/02-12-2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All by Week Success"))
                .andExpect(jsonPath("$.data").value(Matchers.hasSize(4)))
                .andExpect(jsonPath("$.data[0].id").value(2))
                .andExpect(jsonPath("$.data[1].id").value(3))
                .andExpect(jsonPath("$.data[2].id").value(1))
                .andExpect(jsonPath("$.data[3].id").value(4))
                .andExpect(jsonPath("$.data[0].publicComments").value(""))
                .andExpect(jsonPath("$.data[1].publicComments").value(""))
                .andExpect(jsonPath("$.data[2].publicComments").value("Good job!"))
                .andExpect(jsonPath("$.data[3].publicComments").value(""))
                .andExpect(jsonPath("$.data[0].privateComments").value(""))
                .andExpect(jsonPath("$.data[1].privateComments").value(""))
                .andExpect(jsonPath("$.data[2].privateComments").value("Need improvement on documentation."))
                .andExpect(jsonPath("$.data[3].privateComments").value(""))
                .andExpect(jsonPath("$.data[0].week").value("02-12-2024"))
                .andExpect(jsonPath("$.data[1].week").value("02-12-2024"))
                .andExpect(jsonPath("$.data[2].week").value("02-12-2024"))
                .andExpect(jsonPath("$.data[3].week").value("02-12-2024"));
    }


    @Test
    void testFindAllPeerEvaluationsByEvaluateeIdSuccess() throws Exception{
        // Given
        given(this.peerEvaluationService.findAllByEvaluateeId(1L)).willReturn(this.evaluations.subList(1,3));

        // When and then
        this.mockMvc.perform(get("/peer-evaluations/reports/AllByEvaluateeId/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All by Evaluatee Id Success"))
                .andExpect(jsonPath("$.data").value(Matchers.hasSize(2)))
                .andExpect(jsonPath("$.data[0].publicComments").value(""));
    }
}
