package com.project.Main.MockFactories;

import com.project.Main.Models.EmailSender.Factories.IJavaMailSenderFactory;
import com.project.Main.MockRepositories.MockEmailSender;
import org.springframework.mail.javamail.JavaMailSender;

public class MockJavaMailSenderFactory implements IJavaMailSenderFactory {

    private static IJavaMailSenderFactory javaMailSenderFactory;

    private MockJavaMailSenderFactory() {
    }

    public static IJavaMailSenderFactory instance() {
        if (javaMailSenderFactory == null) {
            javaMailSenderFactory = new MockJavaMailSenderFactory();
        }
        return javaMailSenderFactory;
    }

    @Override
    public JavaMailSender createJavaMailSender() {
        return new MockEmailSender();
    }
}
