package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.repository.TaskRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailCreatorService {

    @Autowired
    AdminConfig adminConfig;
    @Autowired
    CompanyConfig companyConfig;
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello account");
        functionality.add("Allows sending tasks to trello");

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
        context.setVariable("show_button",false);
        context.setVariable("is_friend",true);
        context.setVariable("admin_config",adminConfig);
        context.setVariable("application_functionality",functionality);
        return templateEngine.process("mail/created-trello-card-mail",context);
    }

    public String buildScheduledTaskCountMail(String message) {
        long size = taskRepository.count();
        String taskCount=(size==1)?"task":"tasks";
        boolean isLargeTaskCount = (size>5);

        List <Task> tempTask = taskRepository.findAll();
        List <String> taskNames = tempTask.stream()
                .map(task -> task.getTitle())
                .collect(Collectors.toList());

        Context context = new Context();
        context.setVariable("admin_config",adminConfig);
        context.setVariable("button","Check tasks");
        context.setVariable("tasks_count",size);
        context.setVariable("tasksOrTask", taskCount);
        context.setVariable("isLargeTaskCount",isLargeTaskCount);
        context.setVariable("goodByeMessage","Have a nice day");
        context.setVariable("manyTasks",isLargeTaskCount);
        context.setVariable("tasks_url","http://localhost:65000/tasks_frontend/");
        context.setVariable("show_button",true);
        context.setVariable("taskNames",taskNames);
        return templateEngine.process("mail/scheduled-mail",context);

    }

}
