package com.project.Main.Models.CommonState.LoadState;

import lombok.Getter;

public abstract class LoadState {


    @Getter protected String message;

    public LoadState() {
        message = "";
    }

    public boolean isSQLException() {
        return false;
    }

    public boolean isSuccess() {
        return false;
    }

}
