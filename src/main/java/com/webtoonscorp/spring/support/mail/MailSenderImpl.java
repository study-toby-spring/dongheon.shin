package com.webtoonscorp.spring.support.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class MailSenderImpl implements MailSender {

    @Value("${mail.smtp.auth}")
    private boolean auth;

    @Value("${mail.smtp.starttls.enable}")
    private boolean tls;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private String port;

    private Properties properties;

    @PostConstruct
    public void initialize() {

        properties = new Properties();

        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", tls);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
    }

    private Authenticator authenticator = new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("<아이디>", "<암호>");
        }
    };

    public void send(SimpleMailMessage message) throws MailException {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setJavaMailProperties(properties);
        mailSender.setSession(Session.getDefaultInstance(properties, authenticator));

        mailSender.send(message);
    }

    public void send(SimpleMailMessage... messages) throws MailException {

        for (SimpleMailMessage message : messages) {
            send(message);
        }
    }
}
