package com.project.Main.Models.CommonState.DeleteState;

public class DeleteLogicFailureState extends DeleteFailureState {

    public DeleteLogicFailureState() {
        super.message = "CONSTRAINT VIOLATION STATE";
    }

    @Override
    public boolean isSQLException() {
        return false;
    }

}
