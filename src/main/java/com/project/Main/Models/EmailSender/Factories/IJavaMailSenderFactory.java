package com.project.Main.Models.EmailSender.Factories;

import org.springframework.mail.javamail.JavaMailSender;

public interface IJavaMailSenderFactory {

    public JavaMailSender createJavaMailSender();

}
