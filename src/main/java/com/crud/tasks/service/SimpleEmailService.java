package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService  {
    @Autowired
    private JavaMailSender javaMailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    public void send(final Mail mail) {
        LOGGER.info("Starting creating email");
        try {
             SimpleMailMessage mailMessage = createMailMessage(mail);
             javaMailSender.send(mailMessage);
             LOGGER.info("Mail has been sent");
        }
        catch (MailException e) {
            LOGGER.error("Faild to send email: "+e.getMessage());
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getReciverMail());
        mailMessage.setSubject(mail.getSubject());
        if (mail.getToCc()!=null) {
            mailMessage.setCc(mail.getToCc());
        }
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
