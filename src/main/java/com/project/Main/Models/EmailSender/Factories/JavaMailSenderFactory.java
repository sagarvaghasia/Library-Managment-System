package com.project.Main.Models.EmailSender.Factories;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class JavaMailSenderFactory implements IJavaMailSenderFactory {

    private static IJavaMailSenderFactory javaMailSenderFactory = null;

    private JavaMailSenderFactory() {
    }

    public static IJavaMailSenderFactory instance() {
        if (javaMailSenderFactory == null) {
            javaMailSenderFactory = new JavaMailSenderFactory();
        }
        return javaMailSenderFactory;
    }


    @Override
    public JavaMailSender createJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        String senderEmailId = "vaghasiasagar1998@gmail.com";

        javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(465);

        javaMailSender.setUsername(senderEmailId);
        javaMailSender.setPassword("frbfgzjlkbguuehx");

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return javaMailSender;
    }
}
