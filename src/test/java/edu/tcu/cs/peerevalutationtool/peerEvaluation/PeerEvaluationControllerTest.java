package edu.tcu.cs.peerevalutationtool.peerEvaluation;

import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    private PeerEvaluationService service;

    @Test
    public void testSubmitEvaluation() throws Exception {
        PeerEvaluation evaluation = new PeerEvaluation();
        given(service.submitEvaluation(any(PeerEvaluation.class))).willReturn(evaluation);

        mockMvc.perform(post("/peer-evaluations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"evaluatorId\":1,\"evaluateeId\":2,\"qualityOfWork\":10,\"publicComments\":\"Great work!\",\"privateComments\":\"Needs improvement on punctuality.\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetEvaluation() throws Exception {
        PeerEvaluation evaluation = new PeerEvaluation();
        given(service.findEvaluationById(1L)).willReturn(evaluation);

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

        given(service.findEvaluationsByEvaluateeId(studentId)).willReturn(evaluations);

        mockMvc.perform(get("/peer-evaluations/reports/" + studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].qualityOfWork").value(8))
                .andExpect(jsonPath("$[0].publicComments").value("Good effort!"))
                .andExpect(jsonPath("$[1].qualityOfWork").value(9))
                .andExpect(jsonPath("$[1].publicComments").value("Excellent teamwork!"));
    }


}
