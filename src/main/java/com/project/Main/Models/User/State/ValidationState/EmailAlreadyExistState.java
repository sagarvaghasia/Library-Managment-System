package com.project.Main.Models.User.State.ValidationState;

public class EmailAlreadyExistState extends FailState {

    public EmailAlreadyExistState() {
        this.message = "EMAIL ALREADY EXIST";
    }

    @Override
    public boolean isSQLException() {
        return false;
    }

}
