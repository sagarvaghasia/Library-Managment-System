package com.project.Main.Models.Book.Validation;

import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Validation.State.AvailableQuantityGreaterThanTotalQuantityState;
import com.project.Main.Models.Book.Validation.State.AvailableQuantityLessThanZeroState;
import com.project.Main.Models.Book.Validation.State.BookValidationState;

public class AvailableQuantityValidation extends BookValidatorHandler {

    @Override
    public BookValidationState validate(IBook book) {

        if (book.getAvailableQuantity() >= 0) {

            if (book.getAvailableQuantity() > book.getTotalQuantity()) {
                return new AvailableQuantityGreaterThanTotalQuantityState();
            } else {
                return super.validateBookDetails(book);
            }
        } else {
            return new AvailableQuantityLessThanZeroState();
        }
    }

}
