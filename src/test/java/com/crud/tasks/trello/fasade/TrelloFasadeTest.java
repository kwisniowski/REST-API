package com.crud.tasks.trello.fasade;

import com.crud.tasks.domain.*;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFasadeTest {

    @InjectMocks
    private TrelloFasade fasade;

    @Mock
    private TrelloService service;

    @Mock
    TrelloMapper mapper;

    @Mock
    TrelloValidator validator;

    @Test
    public void shouldFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1","name1",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("2","name2",trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("3","name3",false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("4","name4",mappedTrelloLists));

        when(service.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(mapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(mapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList<>());
        when(validator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> trelloBoardDtos = fasade.fetchTrelloBoards();

        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(0,trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1","my_list",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1","my_task",trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1","my_list",false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1","my_task",mappedTrelloLists));

        when(service.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(mapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(mapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(validator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = fasade.fetchTrelloBoards();

        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(1,trelloBoardDtos.size());
        trelloBoardDtos.forEach(trelloBoardDto -> {
            Assert.assertEquals("my_task", trelloBoardDto.getName());
            Assert.assertEquals("1",trelloBoardDto.getId());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                Assert.assertEquals("1",trelloListDto.getId());
                Assert.assertEquals("my_list",trelloListDto.getName());
                Assert.assertEquals(false,trelloListDto.isClosed());
            });
        });

    }
}
