package com.project.Main.MockFactories;

import com.project.Main.Models.BookIssueReturn.Factories.IBookIssueReturnRepositoryFactory;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;
import com.project.Main.MockRepositories.MockBookIssueReturnRepository;
import com.project.Main.Database.IDBConnection;

public class MockBookIssueReturnRepositoryFactory implements IBookIssueReturnRepositoryFactory {

    private static IBookIssueReturnRepositoryFactory bookIssueReturnRepositoryFactory = null;

    private MockBookIssueReturnRepositoryFactory() {
    }

    public static IBookIssueReturnRepositoryFactory instance() {
        if (bookIssueReturnRepositoryFactory == null) {
            bookIssueReturnRepositoryFactory = new MockBookIssueReturnRepositoryFactory();
        }
        return bookIssueReturnRepositoryFactory;
    }

    @Override
    public IBookIssueReturnRepository createIssueReturnRepository(IDBConnection dbConnection) {
        return new MockBookIssueReturnRepository();
    }

}
