package com.project.Main.Models.User.State.LoginState;

public class LoginSQLExceptionState extends LoginState {

    public LoginSQLExceptionState() {
        this.message = "DATABASE ERROR";
    }

    public boolean isSQLException() {
        return true;
    }

}
