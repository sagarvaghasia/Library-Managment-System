package com.project.Main.Models.BookIssueReturn.Factories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.BookIssueReturn.Repositories.BookIssueReturnRepository;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

public class BookIssueReturnRepositoryFactory implements IBookIssueReturnRepositoryFactory {

    private static IBookIssueReturnRepositoryFactory bookIssueReturnFactory = null;

    private BookIssueReturnRepositoryFactory() {
    }

    public static IBookIssueReturnRepositoryFactory instance() {
        if (bookIssueReturnFactory == null) {
            return new BookIssueReturnRepositoryFactory();
        }
        return bookIssueReturnFactory;
    }

    @Override
    public IBookIssueReturnRepository createIssueReturnRepository(IDBConnection dbConnection) {
        return new BookIssueReturnRepository(dbConnection);
    }
}
