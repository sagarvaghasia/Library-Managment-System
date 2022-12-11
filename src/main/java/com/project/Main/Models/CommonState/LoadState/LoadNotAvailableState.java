package com.project.Main.Models.CommonState.LoadState;

public class LoadNotAvailableState extends LoadFailState {

    public LoadNotAvailableState() {
        this.message = "NOT AVAILABLE";
    }

    @Override
    public boolean isSQLException() {
        return false;
    }

}
