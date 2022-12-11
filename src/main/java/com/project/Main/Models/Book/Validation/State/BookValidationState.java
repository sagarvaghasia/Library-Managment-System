package com.project.Main.Models.Book.Validation.State;

import lombok.Getter;

public class BookValidationState {

    @Getter protected String message;

    public BookValidationState(){
        message = "";
    }

    public boolean isSuccess(){
        return false;
    }

}
