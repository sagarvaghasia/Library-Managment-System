package com.project.Main.Models.Book.Factories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.Book.Repositories.IBookRepository;

public interface IBookRepositoryFactory {

    IBookRepository createBookRepository(IDBConnection dbConnection);
}
