package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
public class TrelloCard {
    private String name;
    private String description;
    private String listId;
    private String pos;
}
