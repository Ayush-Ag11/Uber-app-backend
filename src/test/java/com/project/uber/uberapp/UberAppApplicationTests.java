package com.project.uber.uberapp;

import com.project.uber.uberapp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class UberAppApplicationTests {

    @Autowired
    private EmailSenderService emailSenderService;

    @Test
    void contextLoads() {
        emailSenderService.sendEmail(
                "papew62616@asaud.com",
                "This is testing email",
                "Body of my email"
        );
    }

    @Test
    void sendMultipleEmails() {
        String[] emails = {
                "papew62616@asaud.com",
                "ayushagnihotri22@gmail.com",
                "agniayush10@gmail.com"
        };
        emailSenderService.sendEmail(
                emails,
                "This is testing email",
                "Body of my email"
        );
        assertDoesNotThrow(() -> emailSenderService.sendEmail(
                emails,
                "This is testing email",
                "Body of my email"
        ));
    }
}
