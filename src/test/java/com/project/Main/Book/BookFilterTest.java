package com.project.Main.Book;

import com.project.Main.MockFactories.MockBookRepositoryFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.BookFilter.Factories.BookFilterFactory;
import com.project.Main.Models.BookFilter.IBookFilter;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookFilterTest {

    private static final int VALID_TEST_USER_ID = 1;
    private static final int INVALID_TEST_USER_ID = 2;
    private static IBookRepository bookRepository = MockBookRepositoryFactory
            .instance()
            .createBookRepository(null);

    private static IBookFilter bookFilter = BookFilterFactory
            .instance().createBookFilter(bookRepository);

    @Test
    public void getBooksValidTest() {
        Map.Entry<List<IBook>, FilterState> response = bookFilter.getBooks();
        FilterState state = response.getValue();
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void getBooksSearchValidTest() {
        Map.Entry<List<IBook>, FilterState> response = bookFilter.getBooks("abc");
        FilterState state = response.getValue();
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void getBooksSearchInvalidTest() {
        Map.Entry<List<IBook>, FilterState> response = bookFilter.getBooks(null);
        FilterState state = response.getValue();
        assertEquals(false, state.isSuccess());
    }

    @Test
    public void getBookCountValidTest() {
        Map.Entry<Integer, FilterState> response = bookFilter.getBookCount();
        FilterState state = response.getValue();
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void getCurrentlyIssuedBookForUserValidTest() {
        Map.Entry<IBook, FilterState> response = bookFilter.getCurrentlyIssuedBookForUser(VALID_TEST_USER_ID);
        FilterState state = response.getValue();
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void getCurrentlyIssuedBookForUserInValidTest() {
        Map.Entry<IBook, FilterState> response = bookFilter.getCurrentlyIssuedBookForUser(INVALID_TEST_USER_ID);
        FilterState state = response.getValue();
        assertEquals(false, state.isSuccess());
    }

    @Test
    public void getReturnedBooksForUserValidTest() {
        Map.Entry<List<IBook>, FilterState> response = bookFilter.getReturnedBooksForUser(VALID_TEST_USER_ID);
        FilterState state = response.getValue();
        assertEquals(true, state.isSuccess());
    }

    @Test
    public void getReturnedBooksForUserInValidTest() {
        Map.Entry<List<IBook>, FilterState> response = bookFilter.getReturnedBooksForUser(INVALID_TEST_USER_ID);
        FilterState state = response.getValue();
        assertEquals(false, state.isSuccess());
    }

}
