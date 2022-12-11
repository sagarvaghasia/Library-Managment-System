package com.project.Main.Models.BookIssueReturn;

import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;
import com.project.Main.Models.BookIssueReturn.State.IssueState.IssueState;
import com.project.Main.Models.BookIssueReturn.State.ReturnState.ReturnState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public abstract class IBookIssueReturn {

    @Getter @Setter protected int id;
    @Getter @Setter protected int bookId;
    @Getter @Setter protected int userId;
    @Getter @Setter protected Date date;
    @Getter @Setter protected int returnStatus;
    @Getter @Setter protected IBookIssueReturnRepository bookIssueReturnRepository;

    public IBookIssueReturn(IBookIssueReturnRepository bookIssueReturnRepository) {
        this.bookIssueReturnRepository = bookIssueReturnRepository;
    }

    public IBookIssueReturn(int id, int bookId, int userId, Date issuedDate, int returnStatus, IBookIssueReturnRepository bookIssueReturnRepository) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.date = issuedDate;
        this.returnStatus = returnStatus;
        this.bookIssueReturnRepository = bookIssueReturnRepository;
    }

    public abstract LoadState loadIssuedBookByBookAndUserID(int bookId, int userId);

    public abstract LoadState loadIssuedBooksByUserID(int userId);

    public abstract LoadState loadIssuedBooksByBookID(int bookId);

    public abstract IssueState issueBook(int bookId, int userId);

    public abstract ReturnState returnBook(int bookId, int userId);

}
