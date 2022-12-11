package com.project.Main.Database.State;

public class SuccessState extends DatabaseState {

    public SuccessState() {
        this.message = "";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

}
