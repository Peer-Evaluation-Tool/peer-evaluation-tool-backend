package edu.tcu.cs.peerevalutationtool.task;

import edu.tcu.cs.peerevalutationtool.team.Team;
import edu.tcu.cs.peerevalutationtool.system.Result;
import edu.tcu.cs.peerevalutationtool.system.StatusCode;
import edu.tcu.cs.peerevalutationtool.task.TaskService;
import edu.tcu.cs.peerevalutationtool.task.Task;
import edu.tcu.cs.peerevalutationtool.task.converter.TaskToTaskDtoConverter;
import edu.tcu.cs.peerevalutationtool.task.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks") // This specifies the base path for all endpoints in this controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    private final TaskToTaskDtoConverter taskToTaskDtoConverter;

    public TaskController(TaskToTaskDtoConverter taskToTaskDtoConverter) {
        this.taskToTaskDtoConverter = taskToTaskDtoConverter;
    }

    // Endpoint to add a new task
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task newTask = taskService.addTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    // Endpoint to update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    // Endpoint to delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/AllByStudentId/{studentId}")
    public Result findAllByStudentId(@PathVariable long studentId){
        List<Task> foundTasks = this.taskService.findAllByStudentId(studentId);
        List<TaskDto> taskDtos = foundTasks.stream()
                .map(this.taskToTaskDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All by Student Id Success", taskDtos);
    }

    @GetMapping("/AllByWeekAndStudentTeam/{taskWeek}+{taskTeamId}")
    public Result findAllByWeekAndStudentTeamId(@PathVariable String taskWeek, @PathVariable String taskTeamId){
        List<Task> foundTasks = this.taskService.findAllByWeekAndStudentTeamId(taskWeek, taskTeamId);
        List<TaskDto> taskDtos = foundTasks.stream()
                .map(this.taskToTaskDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All by Week and Student Team Id Success", taskDtos);
    }

}
