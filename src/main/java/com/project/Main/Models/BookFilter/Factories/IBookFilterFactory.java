package com.project.Main.Models.BookFilter.Factories;

import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.BookFilter.IBookFilter;

public interface IBookFilterFactory {
    IBookFilter createBookFilter(IBookRepository bookRepository);
}
