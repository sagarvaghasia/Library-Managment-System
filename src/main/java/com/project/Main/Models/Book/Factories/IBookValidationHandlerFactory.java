package com.project.Main.Models.Book.Factories;

import com.project.Main.Models.Book.Validation.BookValidatorHandler;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

public interface IBookValidationHandlerFactory {
    public BookValidatorHandler createAddOrUpdateBookValidationHandler();

    public BookValidatorHandler createDeleteBookValidationHandler(IBookIssueReturnRepository bookIssueReturnRepository);
}
