package com.company.myweb.service.interfaces;

import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendTextEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException;
}
