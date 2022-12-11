package com.project.Main.Models.BookIssueReturn.Validation.State;

public class UserHasIssuedOneBookState extends IssueReturnValidationState {

    public UserHasIssuedOneBookState() {
        this.message = "USER HAS ALREADY ISSUED ONE BOOK.";
    }

    @Override
    public boolean hasUserIssuedAnyBook() {
        return true;
    }

}
