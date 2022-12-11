package com.project.Main.Models.BookIssueReturn.Factories;

import com.project.Main.Models.BookIssueReturn.BookIssueReturn;
import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

import java.sql.Date;

public class BookIssueReturnFactory implements IBookIssueReturnFactory {

    private static IBookIssueReturnFactory bookIssueReturnFactory = null;

    private BookIssueReturnFactory() {

    }

    public static IBookIssueReturnFactory instance() {
        if (bookIssueReturnFactory == null) {
            return new BookIssueReturnFactory();
        }
        return bookIssueReturnFactory;
    }

    @Override
    public IBookIssueReturn createBookIssueReturn(IBookIssueReturnRepository bookIssueReturnRepository) {
        IBookIssueReturn bookIssueReturn = new BookIssueReturn(bookIssueReturnRepository);
        return bookIssueReturn;
    }

    @Override
    public IBookIssueReturn createBookIssueReturn(int id, int bookId, int userId, Date issuedDate, int returnStatus, IBookIssueReturnRepository bookIssueReturnRepository) {
        IBookIssueReturn bookIssueReturn = new BookIssueReturn(id, bookId, userId, issuedDate, returnStatus, bookIssueReturnRepository);
        return bookIssueReturn;
    }

}
