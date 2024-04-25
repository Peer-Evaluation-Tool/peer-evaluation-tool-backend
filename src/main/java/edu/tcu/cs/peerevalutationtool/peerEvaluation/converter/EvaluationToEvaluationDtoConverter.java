package edu.tcu.cs.peerevalutationtool.peerEvaluation.converter;

import edu.tcu.cs.peerevalutationtool.peerEvaluation.DTO.PeerEvaluationDto;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.DTO.PeerEvaluationReportDTO;
import edu.tcu.cs.peerevalutationtool.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevalutationtool.student.converter.StudentToAdminDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EvaluationToEvaluationDtoConverter implements Converter<PeerEvaluation, PeerEvaluationDto> {

    private final StudentToAdminDtoConverter studentToAdminDtoConverter;

    public EvaluationToEvaluationDtoConverter(StudentToAdminDtoConverter studentToAdminDtoConverter) {
        this.studentToAdminDtoConverter = studentToAdminDtoConverter;
    }


    @Override
    public PeerEvaluationDto convert(PeerEvaluation source) {
        PeerEvaluationDto peerEvaluationDto = new PeerEvaluationDto(source.getId(),
                                                                    source.getQualityOfWork(),
                                                                    source.getPublicComments(),
                                                                    source.getPrivateComments(),
                                                                    source.getWeek(),
                                                                    source.getEvaluator() != null
                                                                            ? this.studentToAdminDtoConverter.convert(source.getEvaluator())
                                                                            : null,
                                                                    source.getEvaluatee() != null
                                                                            ? this.studentToAdminDtoConverter.convert(source.getEvaluatee())
                                                                            : null);
        return peerEvaluationDto;
    }
}
