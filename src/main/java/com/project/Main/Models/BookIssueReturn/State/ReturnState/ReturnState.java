package com.project.Main.Models.BookIssueReturn.State.ReturnState;

import lombok.Getter;

public class ReturnState {

    @Getter protected String message;

    public ReturnState() {
        message = "";
    }

    public boolean isSuccess() {
        return false;
    }

}
