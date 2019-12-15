package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    AdminConfig adminConfig;
    @Autowired
    CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","http://localhost:65000/tasks_frontend/");
        context.setVariable("button","Visit website");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("goodByeMessage","Have a nice day");
        context.setVariable("comName",companyConfig.getCompanyName());
        context.setVariable("comEmail",companyConfig.getCompanyEmail());
        context.setVariable("comPhone",companyConfig.getCompanyPhone());
        context.setVariable("comGoal",companyConfig.getCompanyGoal());
        return templateEngine.process("mail/created-trello-card-mail",context);
    }

}
