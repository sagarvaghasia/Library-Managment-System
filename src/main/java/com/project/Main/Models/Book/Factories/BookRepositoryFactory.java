package com.project.Main.Models.Book.Factories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.Book.Repositories.BookRepository;
import com.project.Main.Models.Book.Repositories.IBookRepository;

public class BookRepositoryFactory implements IBookRepositoryFactory {

    private static IBookRepositoryFactory bookRepositoryFactory = null;

    private BookRepositoryFactory() {
    }

    public static IBookRepositoryFactory instance() {
        if (bookRepositoryFactory == null) {
            return new BookRepositoryFactory();
        }
        return bookRepositoryFactory;
    }

    @Override
    public IBookRepository createBookRepository(IDBConnection dbConnection) {

        return new BookRepository(dbConnection);
    }
}
