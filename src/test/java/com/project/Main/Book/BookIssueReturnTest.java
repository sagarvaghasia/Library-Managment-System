package com.project.Main.Book;

import com.project.Main.MockFactories.MockBookIssueReturnRepositoryFactory;
import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.Factories.IBookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Models.BookIssueReturn.State.IssueState.IssueState;
import com.project.Main.Models.BookIssueReturn.State.ReturnState.ReturnState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookIssueReturnTest {

    private static final int TEST_ID = 1;
    private static final int TEST_BOOK_ID = 1;
    private static final int TEST_USER_ID = 1;
    private static final Date TEST_ISSUED_DATE = Date.valueOf(LocalDate.now());
    private static final int TEST_RETURN_STATUS = 0;
    private static IBookIssueReturnFactory bookIssueReturnFactory = BookIssueReturnFactory.instance();
    private IBookIssueReturn bookIssueReturn;

    @BeforeEach
    public void setUp() {
        this.bookIssueReturn = bookIssueReturnFactory.createBookIssueReturn(TEST_ID,
                TEST_BOOK_ID,
                TEST_USER_ID,
                TEST_ISSUED_DATE,
                TEST_RETURN_STATUS,
                MockBookIssueReturnRepositoryFactory.
                        instance()
                        .createIssueReturnRepository(null)
        );
    }

    @Test
    public void loadIssuedBookByBookAndUserIDTestInValidTest() {
        LoadState state = this.bookIssueReturn.loadIssuedBookByBookAndUserID(-1, TEST_USER_ID);
        assertEquals(false, state.isSuccess());

        state = this.bookIssueReturn.loadIssuedBookByBookAndUserID(TEST_BOOK_ID, -1);
        assertEquals(false, state.isSuccess());

        state = this.bookIssueReturn.loadIssuedBookByBookAndUserID(-1, -1);
        assertEquals(false, state.isSuccess());

        state = this.bookIssueReturn.loadIssuedBookByBookAndUserID(2, 2);
        assertEquals(false, state.isSuccess());
    }

    @Test
    public void loadIssuedBookByBookAndUserIDTestValidTest() {
        LoadState state = this.bookIssueReturn.loadIssuedBookByBookAndUserID(TEST_BOOK_ID, TEST_USER_ID);
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void loadIssuedBooksByUserIDTestInValidTest() {
        LoadState state = this.bookIssueReturn.loadIssuedBooksByUserID(-1);
        assertEquals(false, state.isSuccess());

        state = this.bookIssueReturn.loadIssuedBooksByUserID(2);
        assertEquals(false, state.isSuccess());
    }

    @Test
    public void loadIssuedBooksByUserIDTestValidTest() {
        LoadState state = this.bookIssueReturn.loadIssuedBooksByUserID(TEST_USER_ID);
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void loadIssuedBooksByBookIDTestInValidTest() {
        LoadState state = this.bookIssueReturn.loadIssuedBooksByBookID(-1);
        assertEquals(false, state.isSuccess());

        state = this.bookIssueReturn.loadIssuedBooksByBookID(2);
        assertEquals(false, state.isSuccess());

    }

    @Test
    public void loadIssuedBooksByBookIDValidTest() {
        LoadState state = this.bookIssueReturn.loadIssuedBooksByBookID(TEST_BOOK_ID);
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void issueBookInValidTest() {
        IssueState state = this.bookIssueReturn.issueBook(TEST_BOOK_ID, 1);
        assertEquals(false, state.isSuccess());
    }

    @Test
    public void issueBookValidTest() {
        IssueState state = this.bookIssueReturn.issueBook(TEST_BOOK_ID, 2);
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void returnBookInValidTest() {
        ReturnState state = this.bookIssueReturn.returnBook(TEST_BOOK_ID, TEST_USER_ID);
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void returnBookValidTest() {
        ReturnState state = this.bookIssueReturn.returnBook(TEST_BOOK_ID, TEST_USER_ID);
        assertEquals(true, state.isSuccess());
    }

}
