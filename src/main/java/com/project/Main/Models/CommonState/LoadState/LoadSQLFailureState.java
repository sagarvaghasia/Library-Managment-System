package com.project.Main.Models.CommonState.LoadState;

public class LoadSQLFailureState extends LoadFailState {

    public LoadSQLFailureState() {
        this.message = "DATABASE ERROR";
    }

    @Override
    public boolean isSQLException() {
        return true;
    }

}
