package com.project.Main.Models.User.State.LoginState;

public class LoginSuccessState extends LoginState {

    public LoginSuccessState() {
        this.message = "LOGIN SUCCESS";
    }

    public boolean isSuccess() {
        return true;
    }
    
}
