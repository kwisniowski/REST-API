package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloAppToken;
    @Value("${trello.app.username}")
    private String trelloUserName;

    @Autowired
    RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        Optional<TrelloBoardDto[]> boardDtos = Optional.ofNullable(restTemplate.getForObject(getURL(), TrelloBoardDto[].class));
        return boardDtos
                .map(Arrays::asList)
                .orElse(new ArrayList<>());
            }

    private URI getURL() {
        return  UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint+"/members/"+trelloUserName+"/boards")
                .queryParam("key",trelloAppKey)
                .queryParam("token",trelloAppToken)
                .queryParam("fields","name,id")
                .build().encode().toUri();
    }
}