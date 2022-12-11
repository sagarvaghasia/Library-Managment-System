package com.project.Main.Models.BookIssueReturn.Factories;

import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;
import com.project.Main.Models.BookIssueReturn.Validation.BookIssueReturnValidation;
import com.project.Main.Models.User.Repositories.IUserRepository;

public class BookIssueReturnValidationFactory implements IBookIssueReturnValidationFactory {

    private static IBookIssueReturnValidationFactory bookIssueReturnValidationFactory = null;

    private BookIssueReturnValidationFactory() {
    }

    public static IBookIssueReturnValidationFactory instance() {
        if (bookIssueReturnValidationFactory == null) {
            return new BookIssueReturnValidationFactory();
        }
        return bookIssueReturnValidationFactory;
    }

    @Override
    public BookIssueReturnValidation createBookIssueReturnValidation(IBookIssueReturnRepository bookIssueReturnRepository, IUserRepository userRepository) {
        return new BookIssueReturnValidation(bookIssueReturnRepository, userRepository);
    }

}
