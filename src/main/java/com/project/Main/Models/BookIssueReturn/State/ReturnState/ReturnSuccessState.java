package com.project.Main.Models.BookIssueReturn.State.ReturnState;

public class ReturnSuccessState extends ReturnState {

    public ReturnSuccessState() {
        this.message = "BOOK RETURNED SUCCESSFULLY.";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

}
