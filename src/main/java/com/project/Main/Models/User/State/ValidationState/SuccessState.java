package com.project.Main.Models.User.State.ValidationState;

public class SuccessState extends UserValidationState {

    public SuccessState() {
        this.message = "OPERATION SUCCESSFUL.";
    }

    public boolean isSuccess() {
        return true;
    }

}
