package com.project.Main.Models.Book.Validation;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Validation.State.BookIsIssuedState;
import com.project.Main.Models.Book.Validation.State.BookValidationState;
import com.project.Main.Models.Book.Validation.State.BookValidationSuccessState;
import com.project.Main.Models.Book.Validation.State.ErrorState;
import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

import java.util.Map;

public class DeleteValidation extends BookValidatorHandler {

    IBookIssueReturnRepository bookIssueReturnRepository;

    public DeleteValidation(IBookIssueReturnRepository bookIssueReturnRepository) {
        this.bookIssueReturnRepository = bookIssueReturnRepository;
    }

    @Override
    public BookValidationState validate(IBook book) {

        Map.Entry<IBookIssueReturn, DatabaseState> loadState = bookIssueReturnRepository.loadIssuedBooksByBookID(book.getId());
        DatabaseState databaseState = loadState.getValue();

        if (databaseState.isSuccess()) {
            return new BookIsIssuedState();
        } else {
            if (databaseState.isSQLException()) {
                return new ErrorState();
            }
            return new BookValidationSuccessState();
        }
    }
}
