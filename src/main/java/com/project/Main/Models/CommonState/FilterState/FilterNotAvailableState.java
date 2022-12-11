package com.project.Main.Models.CommonState.FilterState;

public class FilterNotAvailableState extends FilterFailState {

    public FilterNotAvailableState() {
        this.message = "NOT AVAILABLE";
    }

    @Override
    public boolean isSQLException() {
        return false;
    }

}
