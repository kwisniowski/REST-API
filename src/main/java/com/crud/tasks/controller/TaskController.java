package com.crud.tasks.controller;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class TaskController {

    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper mapper;

    @RequestMapping(method= RequestMethod.GET,value="/tasks")
    public List<TaskDto> getTasks(){
        return mapper.mapTopTaskListDto(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET,value = "/tasks/{taskId}")
    public TaskDto getTask(@PathVariable("taskId") Long taskId) throws TaskNotFoundException {
        return mapper.mapToTaskDto(service.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE,value="/tasks/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) throws TaskNotFoundException {
        service.deleteTask(service.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value="/tasks")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return mapper.mapToTaskDto(service.saveTask(mapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto){
        service.saveTask(mapper.mapToTask(taskDto));
    }
}
