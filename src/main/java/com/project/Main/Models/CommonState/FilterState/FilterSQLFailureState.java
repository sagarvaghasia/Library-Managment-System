package com.project.Main.Models.CommonState.FilterState;

public class FilterSQLFailureState extends FilterFailState {

    public FilterSQLFailureState() {
        this.message = "DATABASE ERROR";
    }

    @Override
    public boolean isSQLException() {
        return true;
    }

}
