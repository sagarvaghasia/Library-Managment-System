package com.project.Main.Models.BookFilter;

import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.CommonState.FilterState.FilterState;

import java.util.List;
import java.util.Map;

public interface IBookFilter {

    public Map.Entry<List<IBook>, FilterState> getBooks();

    public Map.Entry<List<IBook>, FilterState> getBooks(String name);

    public Map.Entry<List<IBook>, FilterState> getReturnedBooksForUser(int userId);

    public Map.Entry<IBook, FilterState> getCurrentlyIssuedBookForUser(int userId);

    public Map.Entry<Integer, FilterState> getBookCount();
}
