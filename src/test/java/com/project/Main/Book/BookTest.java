package com.project.Main.Book;

import com.project.Main.Models.Book.Factories.BookFactory;
import com.project.Main.Models.Book.Factories.IBookFactory;
import com.project.Main.MockFactories.MockBookRepositoryFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookTest {

    private static final int TEST_ID_1 = 1;
    private static final int BAD_TEST_ID = 100;
    private static final String TEST_TITLE = "abc";
    private static final String TEST_AUTHOR = "abc";
    private static final String TEST_PUBLISHER = "abc";
    private static final int TEST_AVAILABLE_QUANTITY = 1;
    private static final int TEST_TOTAL_QUANTITY = 2;
    private static final float TEST_PRICE_PER_UNIT = (float) 20.2;
    private static final String TEST_TYPE = "abc";
    private static IBookFactory bookFactory = BookFactory.instance();
    private IBook book;

    @BeforeEach
    public void setUp() {
        this.book = bookFactory.createBook(TEST_ID_1,
                TEST_TITLE,
                TEST_AUTHOR,
                TEST_PUBLISHER,
                TEST_AVAILABLE_QUANTITY,
                TEST_TOTAL_QUANTITY,
                TEST_PRICE_PER_UNIT,
                TEST_TYPE,
                MockBookRepositoryFactory.
                        instance()
                        .createBookRepository(null)
        );

    }

    @Test
    public void negativeLoadBookByIdTest() {
        LoadState state = this.book.loadBookById(-1);
        assertEquals(false, state.isSuccess());
    }

    @Test
    public void badLoadBookByIdTest() {
        LoadState state = this.book.loadBookById(100);
        assertEquals(false, state.isSuccess());
    }

    @Test
    public void goodLoadBookByIdTest() {
        LoadState state = this.book.loadBookById(TEST_ID_1);
        assertEquals(true, state.isSuccess());

    }

    @Test
    public void badAddBookTest() {
        book.setId(BAD_TEST_ID);
        Map.Entry<IBook, DatabaseState> addBook = this.book.addBook();
        DatabaseState state = addBook.getValue();
        assertEquals(false, state.isSuccess());
    }


    @Test
    public void goodAddBookTest() {
        Map.Entry<IBook, DatabaseState> addBook = this.book.addBook();
        DatabaseState state = addBook.getValue();
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void badDeleteBookTest() {
        this.book.setId(BAD_TEST_ID);
        DatabaseState state = this.book.deleteBook();
        assertEquals(false, state.isSuccess());
    }

    @Test
    public void goodDeleteBookTest() {
        DatabaseState state = this.book.deleteBook();
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void badUpdateBookTest() {
        this.book.setId(BAD_TEST_ID);
        DatabaseState state = this.book.updateBook();
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void goodUpdateBookTest() {
        DatabaseState state = this.book.updateBook();
        assertEquals(true, state.isSuccess());
    }

}
