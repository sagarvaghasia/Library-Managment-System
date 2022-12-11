package com.project.Main.Database.State;

import lombok.Getter;

public abstract class DatabaseState {

    @Getter protected String message;

    public DatabaseState() {
        message = "";
    }

    public boolean isSuccess() {
        return false;
    }

    public boolean isSQLException() {
        return false;
    }

}
