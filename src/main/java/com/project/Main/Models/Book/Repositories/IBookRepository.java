package com.project.Main.Models.Book.Repositories;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.Book.IBook;

import java.util.List;
import java.util.Map;

public interface IBookRepository {

    public Map.Entry<IBook, DatabaseState> loadBookById(int id);

    public Map.Entry<IBook, DatabaseState> addBook(IBook book);

    public DatabaseState deleteBook(IBook book);

    public DatabaseState updateBook(IBook book);

    public Map.Entry<List<IBook>, DatabaseState> getBooks();

    Map.Entry<List<IBook>, DatabaseState> getReturnedBooksForUser(int userId);

    public Map.Entry<IBook, DatabaseState> getCurrentlyIssuedBooksForUser(int userId);

    public Map.Entry<List<IBook>, DatabaseState> getBooks(String name);

    public Map.Entry<Integer, DatabaseState> getBookCount();

}
