package com.project.Main.Models.CommonState.DeleteState;

public class DeleteSQLFailureState extends DeleteFailureState {

    public DeleteSQLFailureState() {
        super.message = "SQL EXCEPTION ";
    }

    @Override
    public boolean isSQLException() {
        return true;
    }

}
