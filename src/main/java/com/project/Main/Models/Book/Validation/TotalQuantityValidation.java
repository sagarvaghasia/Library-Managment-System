package com.project.Main.Models.Book.Validation;

import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Validation.State.AvailableQuantityGreaterThanTotalQuantityState;
import com.project.Main.Models.Book.Validation.State.BookValidationState;
import com.project.Main.Models.Book.Validation.State.TotalQuantityLessThanZeroState;

public class TotalQuantityValidation extends BookValidatorHandler {

    @Override
    public BookValidationState validate(IBook book) {

        if (book.getTotalQuantity() >= 0) {
            if (book.getAvailableQuantity() > book.getTotalQuantity()) {
                return new AvailableQuantityGreaterThanTotalQuantityState();
            } else {
                return super.validateBookDetails(book);
            }
        }

        return new TotalQuantityLessThanZeroState();
    }

}
