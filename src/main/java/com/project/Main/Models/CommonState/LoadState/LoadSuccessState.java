package com.project.Main.Models.CommonState.LoadState;

public class LoadSuccessState extends LoadState {

    public LoadSuccessState() {
        this.message = "";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

}
