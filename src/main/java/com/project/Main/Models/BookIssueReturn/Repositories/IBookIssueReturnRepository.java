package com.project.Main.Models.BookIssueReturn.Repositories;

import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Database.State.DatabaseState;

import java.util.Map;

public interface IBookIssueReturnRepository {

    public DatabaseState issueBook(int bookId, int userId);

    public DatabaseState returnBook(IBookIssueReturn bookIssueReturn);

    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBookByBookAndUserID(int bookId, int userId);

    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBooksByUserID(int userId);

    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBooksByBookID(int bookId);
}
