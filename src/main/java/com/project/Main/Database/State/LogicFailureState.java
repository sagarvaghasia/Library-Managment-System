package com.project.Main.Database.State;

public class LogicFailureState extends FailState {

    public LogicFailureState() {
        this.message = "LOGIC FAILURE";
    }

    @Override
    public boolean isSQLException() {
        return false;
    }

}
