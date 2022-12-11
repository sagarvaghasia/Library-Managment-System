package com.project.Main.Models.ForgotPassword.Factories;

import com.project.Main.Models.EmailSender.IForgotPasswordEmailSender;
import org.springframework.mail.javamail.JavaMailSender;

public interface IForgotPasswordEmailSenderFactory {

    public IForgotPasswordEmailSender createForgotPasswordEmailSender(JavaMailSender javaMailSender);
}
