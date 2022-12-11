package com.project.Main.Models.BookIssueReturn.Factories;

import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

import java.sql.Date;

public interface IBookIssueReturnFactory {

    public IBookIssueReturn createBookIssueReturn(IBookIssueReturnRepository bookIssueReturnRepository);

    IBookIssueReturn createBookIssueReturn(int id, int bookId, int userId, Date issuedDate, int returnStatus, IBookIssueReturnRepository bookIssueReturnRepository);
}
