package com.project.Main.Models.BookIssueReturn.Validation.State;

public class SQLExceptionLoadBookState extends IssueReturnValidationState {

    public SQLExceptionLoadBookState() {
        this.message = "DATABASE ERROR WHILE LOADING BOOK.";
    }

}
