package com.crud.tasks.trello.fasade;

import com.crud.tasks.domain.*;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloFasade {

    private static Logger LOGGER = LoggerFactory.getLogger(TrelloFasade.class);

    @Autowired
    private TrelloService service;
    @Autowired
    private TrelloMapper mapper;
    @Autowired
    private TrelloValidator validator;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = mapper.mapToBoards(service.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards = validator.validateTrelloBoards(trelloBoards);
        return mapper.mapToBoardsDto(filteredBoards);
    }

    public CreatedTrelloCardDto createTrelloCard (final TrelloCardDto trelloCardDto) {
        TrelloCard trelloCard = mapper.mapToCard(trelloCardDto);
        return service.createdTrelloCard(mapper.mapToCardDto(trelloCard));
    }

}
