package com.project.Main.MockFactories;

import com.project.Main.Models.Book.Factories.IBookRepositoryFactory;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Database.IDBConnection;
import com.project.Main.MockRepositories.MockBookRepository;

public class MockBookRepositoryFactory implements IBookRepositoryFactory {

    private static IBookRepositoryFactory bookRepositoryFactory = null;

    private MockBookRepositoryFactory() {
    }

    public static IBookRepositoryFactory instance() {
        if (bookRepositoryFactory == null) {
            bookRepositoryFactory = new MockBookRepositoryFactory();
        }
        return bookRepositoryFactory;
    }

    @Override
    public IBookRepository createBookRepository(IDBConnection dbConnection) {

        return new MockBookRepository(null);
    }

}
