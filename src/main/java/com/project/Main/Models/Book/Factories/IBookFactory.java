package com.project.Main.Models.Book.Factories;

import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;


public interface IBookFactory {

    public IBook createBook(IBookRepository bookRepository);

    public IBook createBook(int id,
                            String title,
                            String author,
                            String publisher,
                            int availableQuantity,
                            int totalQuantity,
                            float pricePerUnit,
                            String type,
                            IBookRepository bookRepository);
}
