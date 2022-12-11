package com.project.Main.MockRepositories;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.LogicFailureState;
import com.project.Main.Database.State.SQLFailureState;
import com.project.Main.Database.State.SuccessState;
import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.Factories.IBookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockBookIssueReturnRepository implements IBookIssueReturnRepository {

    private static List<IBookIssueReturn> bookIssueReturnList = new ArrayList<>();

    private static IBookIssueReturnFactory bookIssueReturnFactory = BookIssueReturnFactory.instance();

    public MockBookIssueReturnRepository() {

        IBookIssueReturn bookIssueReturn1 = bookIssueReturnFactory.createBookIssueReturn(1, 1, 1, Date.valueOf(LocalDate.now()), 0, this);
        bookIssueReturnList.add(bookIssueReturn1);

        IBookIssueReturn bookIssueReturn2 = bookIssueReturnFactory.createBookIssueReturn(2, 2, 2, Date.valueOf(LocalDate.now()), 1, this);
        bookIssueReturnList.add(bookIssueReturn2);

        IBookIssueReturn bookIssueReturn3 = bookIssueReturnFactory.createBookIssueReturn(3, 2, 3, Date.valueOf(LocalDate.now()), 0, this);
        bookIssueReturnList.add(bookIssueReturn3);

        IBookIssueReturn bookIssueReturn4 = bookIssueReturnFactory.createBookIssueReturn(4, 2, 4, Date.valueOf(LocalDate.now()), 0, this);
        bookIssueReturnList.add(bookIssueReturn4);

    }

    @Override
    public DatabaseState issueBook(int bookId, int userId) {

        for (IBookIssueReturn bookIssueReturn : bookIssueReturnList) {
            if (bookIssueReturn.getUserId() == userId && bookIssueReturn.getReturnStatus() == 0) {
                return new LogicFailureState();
            }
        }

        return new SuccessState();
    }

    @Override
    public DatabaseState returnBook(IBookIssueReturn bookIssueReturn) {

        for (IBookIssueReturn iBookIssueReturn : bookIssueReturnList) {
            if (iBookIssueReturn.getId() == 4 && iBookIssueReturn.getReturnStatus() == 0) {
                return new SuccessState();
            }
        }

        return new LogicFailureState();
    }

    @Override
    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBookByBookAndUserID(int bookId, int userId) {

        if (bookId == 1 && userId == 1) {
            IBookIssueReturn bookIssueReturn = bookIssueReturnFactory.createBookIssueReturn(
                    1, bookId, userId, Date.valueOf(LocalDate.now()), 0, this);

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SuccessState());
        } else if (bookId < 0 || userId < 0) {
            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

        return new AbstractMap.SimpleEntry<>(null, new LogicFailureState());
    }

    @Override
    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBooksByUserID(int userId) {

        if (userId == 1) {
            IBookIssueReturn bookIssueReturn = bookIssueReturnFactory.createBookIssueReturn(
                    1, 1, userId, Date.valueOf(LocalDate.now()), 0, this);

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SuccessState());
        } else if (userId < 0) {
            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

        return new AbstractMap.SimpleEntry<>(null, new LogicFailureState());
    }

    @Override
    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBooksByBookID(int bookId) {

        if (bookId == 1) {
            IBookIssueReturn bookIssueReturn = bookIssueReturnFactory.createBookIssueReturn(
                    1, bookId, 1, Date.valueOf(LocalDate.now()), 0, this);

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SuccessState());
        } else if (bookId < 0) {
            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

        return new AbstractMap.SimpleEntry<>(null, new LogicFailureState());
    }
}
