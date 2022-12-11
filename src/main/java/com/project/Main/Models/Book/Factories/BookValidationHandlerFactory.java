package com.project.Main.Models.Book.Factories;

import com.project.Main.Models.Book.Validation.*;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

public class BookValidationHandlerFactory implements IBookValidationHandlerFactory {

    private static IBookValidationHandlerFactory bookValidationFactory = null;

    private BookValidationHandlerFactory() {
    }

    public static IBookValidationHandlerFactory instance() {
        if (bookValidationFactory == null) {
            return new BookValidationHandlerFactory();
        }
        return bookValidationFactory;
    }

    public BookValidatorHandler createAddOrUpdateBookValidationHandler() {
        BookValidatorHandler bookValidatorHandler = new AvailableQuantityValidation();
        bookValidatorHandler.setNext(new TotalQuantityValidation()).setNext(new PricePerUnitValidation());
        return bookValidatorHandler;
    }

    @Override
    public BookValidatorHandler createDeleteBookValidationHandler(IBookIssueReturnRepository bookIssueReturnRepository) {
        BookValidatorHandler bookValidatorHandler = new DeleteValidation(bookIssueReturnRepository);
        return bookValidatorHandler;
    }

}
