package com.project.uber.uberapp.services;

public interface EmailSenderService {

    void sendEmail(String to, String subject, String body);

    void sendEmail(String[] to, String subject, String body);

}
