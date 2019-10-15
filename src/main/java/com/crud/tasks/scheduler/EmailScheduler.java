package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    @Autowired
    SimpleEmailService emailService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    AdminConfig adminConfig;
    private static String SUBJECT = "Task: Once a day email";

    @Scheduled(fixedDelay = 30000)
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String taskCount=(size==1)?"task":"tasks";
        emailService.send(new Mail(
                adminConfig.getAdminMail(),
                null,
                SUBJECT,
                "Currently there are "+size+" "+taskCount+" in database"));
    }
}
