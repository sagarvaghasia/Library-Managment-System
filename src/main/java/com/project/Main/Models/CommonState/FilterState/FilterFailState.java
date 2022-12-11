package com.project.Main.Models.CommonState.FilterState;

public abstract class FilterFailState extends FilterState {

    public abstract boolean isSQLException();

    @Override
    public boolean isSuccess() {
        return false;
    }

}
