package com.project.Main.Models.BookFilter.Factories;

import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.BookFilter.BookFilter;
import com.project.Main.Models.BookFilter.IBookFilter;

public class BookFilterFactory implements IBookFilterFactory {

    private static IBookFilterFactory bookFilterFactory = null;

    private BookFilterFactory() {
    }

    public static IBookFilterFactory instance() {
        if (bookFilterFactory == null) {
            return new BookFilterFactory();
        }
        return bookFilterFactory;
    }

    @Override
    public IBookFilter createBookFilter(IBookRepository bookRepository) {
        return new BookFilter(bookRepository);
    }
}
