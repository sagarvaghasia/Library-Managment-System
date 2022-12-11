package com.project.Main.Models.BookIssueReturn.Validation.State;

import lombok.Getter;

public class IssueReturnValidationState {

    @Getter protected String message;

    public IssueReturnValidationState() {
        message = "";
    }

    public boolean hasUserIssuedAnyBook() {
        return false;
    }

}
