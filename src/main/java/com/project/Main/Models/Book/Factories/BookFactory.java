package com.project.Main.Models.Book.Factories;

import com.project.Main.Models.Book.Book;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;


public class BookFactory implements IBookFactory {

    private static IBookFactory bookFactory = null;

    private BookFactory() {
    }

    public static IBookFactory instance() {
        if (bookFactory == null) {
            return new BookFactory();
        }
        return bookFactory;
    }

    @Override
    public IBook createBook(IBookRepository bookRepository) {
        IBook book = new Book(bookRepository);
        return book;
    }

    @Override
    public IBook createBook(int id, String title, String author, String publisher,
                            int availableQuantity, int totalQuantity, float pricePerUnit,
                            String type, IBookRepository bookRepository) {
        return new Book(id, title, author, publisher, availableQuantity, totalQuantity, pricePerUnit, type, bookRepository);
    }
}
