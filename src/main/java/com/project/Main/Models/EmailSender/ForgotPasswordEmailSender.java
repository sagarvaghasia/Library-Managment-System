package com.project.Main.Models.EmailSender;

import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class ForgotPasswordEmailSender implements IForgotPasswordEmailSender {

    @Getter @Setter private static String senderEmailId;
    @Getter @Setter private static String subject;
    @Getter @Setter private static JavaMailSender javaMailSender;
    @Getter @Setter private String content;

    public ForgotPasswordEmailSender(JavaMailSender mailSender) {
        subject = "Link to reset password";
        javaMailSender = mailSender;
    }

    @Override
    public EMAIL_STATUS send(String link, String receiverEmail) {

        this.content = "link to reset password is \n" + link + "\n you can click on the link.";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmailId);
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(this.content);
        javaMailSender.send(simpleMailMessage);

        return EMAIL_STATUS.SUCCESS;
    }
}
