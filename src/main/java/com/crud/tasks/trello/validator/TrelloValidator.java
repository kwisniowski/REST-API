package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloValidator {

    private static Logger LOGGER = LoggerFactory.getLogger(TrelloValidator.class);

    public void validateCard (TrelloCard trelloCard) {
        if (trelloCard.getName().contains("test")) {
            LOGGER.info("Someone is testing my aplication");
        }
        else {
            LOGGER.info("No testing in my application");
        }
    }

    public List<TrelloBoard> validateTrelloBoards(List<TrelloBoard> trelloBoards) {
        LOGGER.info("Starting to filter boards");
        List<TrelloBoard> filteredTrelloBoards = trelloBoards.stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(Collectors.toList());
        LOGGER.info("Filtering ended. Current board count: "+filteredTrelloBoards.size());
        return filteredTrelloBoards;
    }
}
