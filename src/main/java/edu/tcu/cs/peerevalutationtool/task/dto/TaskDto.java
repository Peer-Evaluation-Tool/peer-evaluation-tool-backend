package edu.tcu.cs.peerevalutationtool.task.dto;

import edu.tcu.cs.peerevalutationtool.domain.Team;
import edu.tcu.cs.peerevalutationtool.student.dto.StudentDto;

public record TaskDto(Long id,
                      String category,
                      String plannedTask,
                      String description,
                      Double plannedHours,
                      Double actualHours,
                      String status,
                      StudentDto student,
                      String week,
                      Team team) {
}
