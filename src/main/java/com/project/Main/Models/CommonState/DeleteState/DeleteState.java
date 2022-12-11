package com.project.Main.Models.CommonState.DeleteState;

import lombok.Getter;
import lombok.Setter;

public class DeleteState {

    @Getter @Setter protected String message;

    public boolean isSuccess() {
        return false;
    }

    public boolean isSQLException() {
        return false;
    }

}
