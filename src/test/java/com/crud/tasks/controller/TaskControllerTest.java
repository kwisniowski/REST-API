package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockBean
    TaskController taskController;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        List<TaskDto> taskList = Arrays.asList(
                new TaskDto(1L,"Title1","Test Content"),
                new TaskDto(2L,"Title2","Test Content 2"));
        when(taskController.getTasks()).thenReturn(taskList);

        //When
        //Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L,"Title1","Test Content");
        when(taskController.getTask(ArgumentMatchers.anyLong())).thenReturn(taskDto);

        //When
        //Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Title1"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    public void shouldUpdateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L,"Title1","Test Content");
        TaskDto updatedTaskDto = new TaskDto(1L,"Updated title","Updated content");
        when(taskController.updateTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(updatedTaskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When
        //Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.content").value("Updated content"));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L,"Test tile","Test content");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());

        //Then
        verify(taskController,times(1)).createTask(any(TaskDto.class));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        //When
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        verify(taskController,times(1)).deleteTask(anyLong());
    }
}

