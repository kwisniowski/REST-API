package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class EmailScheduler {
    @Autowired
    SimpleEmailService emailService;
    @Autowired
    AdminConfig adminConfig;

    private static String SUBJECT = "Scheduled tasks count information";

    @Scheduled(cron = "0 0 * * * *")
    public void sendInformationEmail() {

        emailService.send(new Mail(
                adminConfig.getAdminMail(),
                null,
                SUBJECT,
                ""));
    }
}