package com.project.Main.Models.EmailSender;

public interface IForgotPasswordEmailSender {

    public enum EMAIL_STATUS{
        SUCCESS,
        ERROR
    }

    public EMAIL_STATUS send(String link, String receiverEmail);
}
