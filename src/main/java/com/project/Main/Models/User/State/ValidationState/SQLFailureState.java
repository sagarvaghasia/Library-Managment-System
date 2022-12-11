package com.project.Main.Models.User.State.ValidationState;

public class SQLFailureState extends FailState {

    public SQLFailureState() {
        this.message = "DATABASE ERROR";
    }

    @Override
    public boolean isSQLException() {
        return true;
    }

}
