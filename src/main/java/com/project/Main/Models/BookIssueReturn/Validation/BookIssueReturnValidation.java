package com.project.Main.Models.BookIssueReturn.Validation;

import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;
import com.project.Main.Models.BookIssueReturn.Validation.State.*;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.CommonState.LoadState.LoadState;

public class BookIssueReturnValidation {

    IUserRepository userRepository;
    IBookIssueReturnRepository bookIssueReturnRepository;

    public BookIssueReturnValidation(IBookIssueReturnRepository bookIssueReturnRepository, IUserRepository userRepository) {
        this.bookIssueReturnRepository = bookIssueReturnRepository;
        this.userRepository = userRepository;
    }

    public IssueReturnValidationState validate(int bookId, String email) {

        IUser user = UserFactory.instance().createUser(userRepository);
        LoadState loadState = user.loadUserByEmailId(email);

        if (loadState.isSuccess()) {

            IBookIssueReturn bookIssueReturn = BookIssueReturnFactory.instance().createBookIssueReturn(bookIssueReturnRepository);
            loadState = bookIssueReturn.loadIssuedBookByBookAndUserID(bookId, user.getId());

            if (loadState.isSuccess()) {
                return new UserHasIssuedOneBookState();
            } else {
                if (loadState.isSQLException()) {
                    return new SQLExceptionLoadBookState();
                }
                return new UserHasNotIssuedBookState();
            }
        } else {
            if (loadState.isSQLException()) {
                return new SQLExceptionLoadUserState();
            }
            return new UserDoesNotExistState();
        }

    }
}
