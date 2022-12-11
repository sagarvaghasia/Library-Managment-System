package com.project.Main.Models.User.State.ValidationState;

import lombok.Getter;

public class UserValidationState {

    @Getter protected String message;

    public UserValidationState() {
        message = "";
    }

    public boolean isSuccess() {
        return false;
    }

    public boolean isSQLException() {
        return false;
    }

}
