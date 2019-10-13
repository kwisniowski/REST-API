package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
        when(trelloConfig.getTrelloUserName()).thenReturn("kacperwisniowski");
    }
    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_name","test_id",new ArrayList<>());
        URI url = new URI("http://test.com/members/kacperwisniowski/boards?key=test&token=test&lists=all&fields=name,id");
        System.out.println(url);
        when(restTemplate.getForObject(url,TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> testList = trelloClient.getTrelloBoards();

        //Then
        Assert.assertEquals(1, testList.size());
        Assert.assertEquals("test_id",testList.get(0).getId());
        Assert.assertEquals("test_name",testList.get(0).getName());
        Assert.assertEquals(0,testList.get(0).getLists().size());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto newTrelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "test_id",
                "top");
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");
        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                "Test task",
                "http://test.com",
                null);
        when(restTemplate.postForObject(uri,null,CreatedTrelloCard.class)).thenReturn(createdTrelloCard);
        //When
        CreatedTrelloCard testCard = trelloClient.createNewCard(newTrelloCardDto);
        //Then
        assertEquals("1",testCard.getId());
        assertEquals("Test task", testCard.getName());
        assertEquals("http://test.com", testCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        //Given
        URI uri = new URI("http://test.com/members/kacperwisniowski/boards?key=test&token=test&lists=all&fields=name,id");
      //  when(restTemplate.getForObject(uri,TrelloCardDto[].class)).thenReturn(null);
        //When
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
        //Then
        assertEquals(0,trelloBoards.size());
    }
}