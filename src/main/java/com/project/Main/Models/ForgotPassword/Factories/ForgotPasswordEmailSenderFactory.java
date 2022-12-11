package com.project.Main.Models.ForgotPassword.Factories;

import com.project.Main.Models.EmailSender.ForgotPasswordEmailSender;
import com.project.Main.Models.EmailSender.IForgotPasswordEmailSender;
import org.springframework.mail.javamail.JavaMailSender;

public class ForgotPasswordEmailSenderFactory implements IForgotPasswordEmailSenderFactory {

    private static IForgotPasswordEmailSenderFactory forgotPasswordEmailSenderFactory;

    private ForgotPasswordEmailSenderFactory() {
    }

    public static IForgotPasswordEmailSenderFactory instance() {
        if (forgotPasswordEmailSenderFactory == null) {
            forgotPasswordEmailSenderFactory = new ForgotPasswordEmailSenderFactory();
        }
        return forgotPasswordEmailSenderFactory;
    }

    @Override
    public IForgotPasswordEmailSender createForgotPasswordEmailSender(JavaMailSender javaMailSender) {
        return new ForgotPasswordEmailSender(javaMailSender);
    }
}
