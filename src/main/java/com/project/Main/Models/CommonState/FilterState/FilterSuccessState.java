package com.project.Main.Models.CommonState.FilterState;

public class FilterSuccessState extends FilterState {

    public FilterSuccessState() {
        this.message = "";
    }

    @Override public boolean isSuccess() {
        return true;
    }

}
