package com.project.Main.Models.Book.Validation.State;

public class BookIsIssuedState extends BookValidationState {

    public BookIsIssuedState() {
        this.message = "BOOK IS ISSUED BY SOMEONE, CAN NOT DELETE BOOK.";
    }

}
