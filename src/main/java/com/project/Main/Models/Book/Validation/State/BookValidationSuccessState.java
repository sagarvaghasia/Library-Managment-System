package com.project.Main.Models.Book.Validation.State;

public class BookValidationSuccessState extends BookValidationState {

    public BookValidationSuccessState() {
        this.message = "";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

}
