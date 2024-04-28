package edu.tcu.cs.peerevalutationtool.task.converter;

import edu.tcu.cs.peerevalutationtool.student.converter.StudentToAdminDtoConverter;
import edu.tcu.cs.peerevalutationtool.task.Task;
import edu.tcu.cs.peerevalutationtool.task.dto.TaskDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDtoConverter implements Converter<Task, TaskDto> {

    private final StudentToAdminDtoConverter studentToAdminDtoConverter;

    public TaskToTaskDtoConverter(StudentToAdminDtoConverter studentToAdminDtoConverter) {
        this.studentToAdminDtoConverter = studentToAdminDtoConverter;
    }

    @Override
    public TaskDto convert(Task source) {
        TaskDto taskDto = new TaskDto(source.getId(),
                source.getCategory(),
                source.getPlannedTask(),
                source.getDescription(),
                source.getPlannedHours(),
                source.getActualHours(),
                source.getStatus(),
                source.getStudent() != null
                        ? this.studentToAdminDtoConverter.convert(source.getStudent())
                        : null,
                source.getWeek(),
                source.getStudent().getTeam());

        return taskDto;
    }
}
