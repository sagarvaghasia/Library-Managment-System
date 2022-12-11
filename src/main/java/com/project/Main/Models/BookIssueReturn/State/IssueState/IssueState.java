package com.project.Main.Models.BookIssueReturn.State.IssueState;

import lombok.Getter;

public class IssueState {

    @Getter protected String message;

    public IssueState() {
        message = "";
    }

    public boolean isSuccess() {
        return false;
    }

}
