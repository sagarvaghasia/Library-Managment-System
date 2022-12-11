package com.project.Main.Models.BookIssueReturn.Validation.State;

public class SQLExceptionLoadUserState extends IssueReturnValidationState {

    public SQLExceptionLoadUserState() {
        this.message = "DATABASE ERROR WHILE LOADING USER.";
    }

}
