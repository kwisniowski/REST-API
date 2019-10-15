package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
    public void shouldSendEmail() {
        //Given
        Mail mail = new Mail("kwisniowski@cxsa.pl","wk@gmail.com","Test mail","Test message");
        //When
        simpleEmailService.send(mail);
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender,Mockito.times(1)).send(captor.capture());
        SimpleMailMessage sentMail = captor.getValue();
        //Then
        assertEquals("Test mail", sentMail.getSubject());
        assertEquals("wk@gmail.com",sentMail.getCc()[0]);
    }

}