package com.project.Main.Models.CommonState.LoadState;

public abstract class LoadFailState extends LoadState {

    public abstract boolean isSQLException();

    @Override
    public boolean isSuccess() {
        return false;
    }

}
