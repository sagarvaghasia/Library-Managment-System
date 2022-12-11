package com.project.Main.Models.Book.Validation;

import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Validation.State.BookValidationState;
import com.project.Main.Models.Book.Validation.State.PriceLessThanZeroState;

public class PricePerUnitValidation extends BookValidatorHandler {

    @Override
    public BookValidationState validate(IBook book) {

        if (book.getPricePerUnit() >= 0) {
            return super.validateBookDetails(book);
        }

        return new PriceLessThanZeroState();
    }

}
