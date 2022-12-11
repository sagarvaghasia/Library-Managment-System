package com.project.Main.Database.State;

public class SQLFailureState extends FailState {

    public SQLFailureState() {
        this.message = "DATABASE ERROR.";
    }

    @Override
    public boolean isSQLException() {
        return true;
    }

}
