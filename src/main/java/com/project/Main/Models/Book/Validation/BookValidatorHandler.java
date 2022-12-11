package com.project.Main.Models.Book.Validation;

import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Validation.State.BookValidationState;
import com.project.Main.Models.Book.Validation.State.BookValidationSuccessState;

public abstract class BookValidatorHandler {

    private BookValidatorHandler nextBookValidatorHandler;

    public BookValidatorHandler setNext(BookValidatorHandler bookValidatorHandler) {
        this.nextBookValidatorHandler = bookValidatorHandler;

        return this.nextBookValidatorHandler;
    }

    public abstract BookValidationState validate(IBook book);

    protected BookValidationState validateBookDetails(IBook book) {

        if (this.nextBookValidatorHandler == null) {
            return new BookValidationSuccessState();
        }

        return nextBookValidatorHandler.validate(book);
    }
}
