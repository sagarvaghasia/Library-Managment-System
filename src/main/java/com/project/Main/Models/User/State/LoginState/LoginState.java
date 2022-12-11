package com.project.Main.Models.User.State.LoginState;

import lombok.Getter;
import lombok.Setter;

public class LoginState {

    @Getter @Setter protected String message;

    public boolean isSuccess() {
        return false;
    }

    public boolean isSQLException() {
        return false;
    }

}
