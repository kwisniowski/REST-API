package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloClient client;

    @RequestMapping(method = RequestMethod.GET,value = "getTrelloBoards")
    public void getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = client.getTrelloBoards();
        trelloBoards.forEach(trelloBoardDto -> {
            System.out.println();
            System.out.println(trelloBoardDto.getId() + "   "+trelloBoardDto.getName());
            System.out.println("This board contains lists:");
            trelloBoardDto.getLists().forEach(list -> {
                System.out.println(list.getId()+ " "+list.getName()+" "+list.isClosed());
            });
            });
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        CreatedTrelloCard newTrelloCard = client.createNewCard(trelloCardDto);
        System.out.println(newTrelloCard.getBadges().getAttatchemnts().getTrello().getBoard());
        System.out.println(newTrelloCard.getBadges().getAttatchemnts().getTrello().getCard());
        return newTrelloCard;
    }


}
