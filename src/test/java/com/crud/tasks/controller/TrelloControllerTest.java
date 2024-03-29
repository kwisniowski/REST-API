package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.fasade.TrelloFasade;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {

    @Autowired
    MockMvc mockmvc;
    @MockBean
    private TrelloFasade trelloFasade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(trelloFasade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When
        //Then
        mockmvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception{
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1","Test List",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1","Test Board",trelloLists));

        when(trelloFasade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When
        //Then
        mockmvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Trello Boards
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id",is("1")))
                .andExpect(jsonPath("$[0].name",is("Test Board")))
                //Trello Board Lists
                .andExpect(jsonPath("$[0].lists",hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id",is("1")))
                .andExpect(jsonPath("$[0].lists[0].name",is("Test List")))
                .andExpect(jsonPath("$[0].lists[0].closed",is(false)));

    }

    @Test
    public void shouldCreateTrelloCard() throws Exception {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "TestName",
                "Description",
                "123",
                "center");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Name1",
                "http://test.com",
                null);
        when(trelloFasade.createTrelloCard(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        //When
        //Then
        mockmvc.perform(post("/v1/trello/createTrelloCard")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id",is("1")))
                .andExpect(jsonPath("$.name",is("Name1")))
                .andExpect(jsonPath("$.shortUrl",is("http://test.com")));
    }
}