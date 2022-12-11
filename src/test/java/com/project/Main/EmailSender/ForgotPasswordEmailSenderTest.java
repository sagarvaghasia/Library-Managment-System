package com.project.Main.EmailSender;

import com.project.Main.Models.ForgotPassword.Factories.ForgotPasswordEmailSenderFactory;
import com.project.Main.Models.ForgotPassword.Factories.IForgotPasswordEmailSenderFactory;
import com.project.Main.Models.EmailSender.Factories.IJavaMailSenderFactory;
import com.project.Main.MockFactories.MockJavaMailSenderFactory;
import com.project.Main.Models.EmailSender.IForgotPasswordEmailSender;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ForgotPasswordEmailSenderTest {

    private static IForgotPasswordEmailSenderFactory forgotPasswordEmailSenderFactory = ForgotPasswordEmailSenderFactory.instance();

    private static IJavaMailSenderFactory javaMailSenderFactory = MockJavaMailSenderFactory.instance();
    private static JavaMailSender javaMailSender = javaMailSenderFactory.createJavaMailSender();

    private static IForgotPasswordEmailSender forgotPasswordEmailSender = forgotPasswordEmailSenderFactory
            .createForgotPasswordEmailSender(javaMailSender);

    @Test
    public void forgotPasswordSendEmailValidTest() {
        IForgotPasswordEmailSender.EMAIL_STATUS status = forgotPasswordEmailSender.send("localhost:8080", "khush@gmail.com");
        assertEquals(IForgotPasswordEmailSender.EMAIL_STATUS.SUCCESS, status);
    }
}
