package com.project.Main.Models.CommonState.FilterState;

import lombok.Getter;

public abstract class FilterState {

    @Getter protected String message;

    public FilterState() {
        message = "";
    }

    public boolean isSQLException() {
        return false;
    }

    public boolean isSuccess() {
        return false;
    }

}
