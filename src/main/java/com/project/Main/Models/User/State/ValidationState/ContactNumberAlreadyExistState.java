package com.project.Main.Models.User.State.ValidationState;

public class ContactNumberAlreadyExistState extends FailState {

    public ContactNumberAlreadyExistState() {
        this.message = "CONTACT NUMBER ALREADY EXIST";
    }

    @Override
    public boolean isSQLException() {
        return false;
    }

}
