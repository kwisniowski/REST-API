package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.Assert.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {
    @InjectMocks
    private SimpleEmailService simpleEmailService;
    @Mock
    private JavaMailSender javaMailSender;
    @Test
    public void shouldSendEmial() {
        //Given',
        Mail mail = new Mail("kwisniowski@cxsa.pl",null,"Test mail","Test message");
        SimpleMailMessage message = simpleEmailService.createMailMessage(mail);
        /*message.setText(mail.getMessage());
        message.setSubject(mail.getSubject());
        message.setTo(mail.getReciverMail());
        if (mail.getToCc()!=null) {
            message.setCc(mail.getToCc());
        };*/
        //When
        simpleEmailService.send(mail);
        //Then
        verify(javaMailSender, Mockito.times(1)).send(message);
    }

}