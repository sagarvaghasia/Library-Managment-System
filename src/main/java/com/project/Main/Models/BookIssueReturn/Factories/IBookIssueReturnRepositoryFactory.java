package com.project.Main.Models.BookIssueReturn.Factories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

public interface IBookIssueReturnRepositoryFactory {

    public IBookIssueReturnRepository createIssueReturnRepository(IDBConnection dbConnection);

}
