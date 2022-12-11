package com.project.Main.Models.BookIssueReturn;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;
import com.project.Main.Models.BookIssueReturn.State.IssueState.IssueLogicFailureState;
import com.project.Main.Models.BookIssueReturn.State.IssueState.IssueSQLFailureBook;
import com.project.Main.Models.BookIssueReturn.State.IssueState.IssueState;
import com.project.Main.Models.BookIssueReturn.State.IssueState.IssueSuccessState;
import com.project.Main.Models.BookIssueReturn.State.ReturnState.ReturnLogicFailureState;
import com.project.Main.Models.BookIssueReturn.State.ReturnState.ReturnSQLFailureBook;
import com.project.Main.Models.BookIssueReturn.State.ReturnState.ReturnState;
import com.project.Main.Models.BookIssueReturn.State.ReturnState.ReturnSuccessState;
import com.project.Main.Models.CommonState.LoadState.LoadNotAvailableState;
import com.project.Main.Models.CommonState.LoadState.LoadSQLFailureState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import com.project.Main.Models.CommonState.LoadState.LoadSuccessState;

import java.sql.Date;
import java.util.Map;

public class BookIssueReturn extends IBookIssueReturn {

    public BookIssueReturn(IBookIssueReturnRepository bookIssueReturnRepository) {
        super(bookIssueReturnRepository);
    }

    public BookIssueReturn(int id, int bookId, int userId,
                           Date issuedDate, int returnStatus,
                           IBookIssueReturnRepository bookIssueReturnRepository) {
        super(id, bookId, userId, issuedDate, returnStatus, bookIssueReturnRepository);
    }

    @Override
    public LoadState loadIssuedBookByBookAndUserID(int bookId, int userId) {

        Map.Entry<IBookIssueReturn, DatabaseState> response = this.bookIssueReturnRepository.loadIssuedBookByBookAndUserID(bookId, userId);
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {

            IBookIssueReturn bookIssueReturn = response.getKey();
            this.loadBookIssueReturn(bookIssueReturn);

            return new LoadSuccessState();
        } else {
            if (databaseState.isSQLException()) {
                return new LoadSQLFailureState();
            }
            return new LoadNotAvailableState();
        }

    }

    @Override
    public LoadState loadIssuedBooksByUserID(int userId) {

        Map.Entry<IBookIssueReturn, DatabaseState> response = this.bookIssueReturnRepository.loadIssuedBooksByUserID(userId);
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {
            IBookIssueReturn bookIssueReturn = response.getKey();
            this.loadBookIssueReturn(bookIssueReturn);

            return new LoadSuccessState();
        } else {
            if (databaseState.isSQLException()) {
                return new LoadSQLFailureState();
            }
            return new LoadNotAvailableState();
        }

    }

    @Override
    public LoadState loadIssuedBooksByBookID(int bookId) {

        Map.Entry<IBookIssueReturn, DatabaseState> response = this.bookIssueReturnRepository.loadIssuedBooksByBookID(bookId);
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {
            IBookIssueReturn bookIssueReturn = response.getKey();
            this.loadBookIssueReturn(bookIssueReturn);

            return new LoadSuccessState();
        } else {
            if (databaseState.isSQLException()) {
                return new LoadSQLFailureState();
            }
            return new LoadNotAvailableState();
        }

    }

    @Override
    public IssueState issueBook(int bookId, int userId) {

        DatabaseState databaseState = this.bookIssueReturnRepository.issueBook(bookId, userId);

        if (databaseState.isSuccess()) {
            return new IssueSuccessState();
        } else {
            if (databaseState.isSQLException()) {
                return new IssueSQLFailureBook();
            }
            return new IssueLogicFailureState();
        }

    }

    @Override
    public ReturnState returnBook(int bookId, int userId) {

        LoadState loadIssueReturnBookState = this.loadIssuedBooksByBookID(bookId);
        DatabaseState databaseState = this.bookIssueReturnRepository.returnBook(this);

        if (loadIssueReturnBookState.isSuccess() && databaseState.isSuccess()) {
            return new ReturnSuccessState();
        } else {
            if (databaseState.isSQLException() || loadIssueReturnBookState.isSQLException()) {
                return new ReturnSQLFailureBook();
            }
            return new ReturnLogicFailureState();
        }

    }

    public void loadBookIssueReturn(IBookIssueReturn bookIssueReturn) {
        this.setId(bookIssueReturn.getId());
        this.setUserId(bookIssueReturn.getUserId());
        this.setBookId(bookIssueReturn.getBookId());
        this.setDate(bookIssueReturn.getDate());
        this.setReturnStatus(bookIssueReturn.getReturnStatus());
        this.setBookIssueReturnRepository(bookIssueReturn.getBookIssueReturnRepository());
    }

}
