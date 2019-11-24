package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.fasade.TrelloFasade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping ("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloFasade fasade;

    @RequestMapping(method = RequestMethod.GET,value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return fasade.fetchTrelloBoards();
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return fasade.createTrelloCard(trelloCardDto);
    }

}
