package com.project.Main.Models.BookIssueReturn.State.IssueState;

public class IssueSuccessState extends IssueState {

    public IssueSuccessState() {
        this.message = "BOOK ISSUED SUCCESSFULLY.";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

}
