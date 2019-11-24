package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TrelloConfig trelloConfig;

    public List<TrelloBoardDto> getTrelloBoards() {
        System.out.println(getURL());
        try {
            Optional<TrelloBoardDto[]> boardDtos = Optional.ofNullable(restTemplate.getForObject(getURL(), TrelloBoardDto[].class));
            return boardDtos
                    .map(Arrays::asList)
                    .orElse(new ArrayList<>());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    private URI getURL() {
        return  UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint()+"/members/"+trelloConfig.getTrelloUserName()+"/boards")
                .queryParam("key",trelloConfig.getTrelloAppKey())
                .queryParam("token",trelloConfig.getTrelloAppToken())
                .queryParam("lists","all")
                .queryParam("fields","name,id")
                .build().encode().toUri();
    }

    public CreatedTrelloCardDto createNewCard (TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint()+"/cards")
                .queryParam("key",trelloConfig.getTrelloAppKey())
                .queryParam("token",trelloConfig.getTrelloAppToken())
                .queryParam("name",trelloCardDto.getName())
                .queryParam("desc",trelloCardDto.getDescription())
                .queryParam("pos",trelloCardDto.getPos())
                .queryParam("idList",trelloCardDto.getListId())
                .build().encode().toUri();
        System.out.println("klient  " + url);
        return restTemplate.postForObject(url,null, CreatedTrelloCardDto.class);
    }
}