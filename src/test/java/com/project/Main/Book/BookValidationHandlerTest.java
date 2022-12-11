package com.project.Main.Book;

import com.project.Main.MockFactories.MockBookIssueReturnRepositoryFactory;
import com.project.Main.MockFactories.MockBookRepositoryFactory;
import com.project.Main.Models.Book.Factories.BookFactory;
import com.project.Main.Models.Book.Factories.BookValidationHandlerFactory;
import com.project.Main.Models.Book.Factories.IBookFactory;
import com.project.Main.Models.Book.Factories.IBookValidationHandlerFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Validation.BookValidatorHandler;
import com.project.Main.Models.Book.Validation.State.BookValidationState;
import com.project.Main.Models.BookIssueReturn.Factories.IBookIssueReturnRepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookValidationHandlerTest {

    private static final int BAD_TEST_ID = 1;
    private static final int TEST_ID = 2;
    private static final String TEST_TITLE = "abc";
    private static final String TEST_AUTHOR = "abc";
    private static final String TEST_PUBLISHER = "abc";
    private static final int TEST_AVAILABLE_QUANTITY = 3;
    private static final int TEST_AVAILABLE_QUANTITY_GREATER_THAN_TOTAL_QUANTITY = 10;
    private static final int TEST_TOTAL_QUANTITY = 5;
    private static final int TEST_TOTAL_QUANTITY_LESS_THAN_AVAILABLE_QUANTITY = 2;
    private static final float TEST_PRICE_PER_UNIT = (float) 20.2;
    private static final String TEST_TYPE = "abc";
    private static IBookFactory bookFactory = BookFactory.instance();
    private static IBookValidationHandlerFactory bookValidationHandlerFactory = BookValidationHandlerFactory.instance();
    private IBook book;
    private BookValidatorHandler addOrUpdateBookValidatorHandler;
    private BookValidatorHandler deleteBookValidatorHandler;
    private IBookIssueReturnRepositoryFactory mockBookIssueReturnRepositoryFactory = MockBookIssueReturnRepositoryFactory.instance();

    @BeforeEach
    public void setUp() {
        this.book = bookFactory.createBook(TEST_ID,
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

        this.addOrUpdateBookValidatorHandler = bookValidationHandlerFactory.createAddOrUpdateBookValidationHandler();
        this.deleteBookValidatorHandler = bookValidationHandlerFactory.createDeleteBookValidationHandler(
                mockBookIssueReturnRepositoryFactory.createIssueReturnRepository(null));

    }

    @Test
    public void availableQuantityInValidTest() {
        this.book.setAvailableQuantity(-1);
        BookValidationState bookValidationState = this.addOrUpdateBookValidatorHandler.validate(book);
        assertEquals(false, bookValidationState.isSuccess());

        this.book.setAvailableQuantity(TEST_AVAILABLE_QUANTITY_GREATER_THAN_TOTAL_QUANTITY);
        bookValidationState = this.addOrUpdateBookValidatorHandler.validate(book);
        assertEquals(false, bookValidationState.isSuccess());
    }

    @Test
    public void availableQuantityValidTest() {
        BookValidationState bookValidationState = this.addOrUpdateBookValidatorHandler.validate(book);
        assertEquals(true, bookValidationState.isSuccess());
    }

    @Test
    public void totalQuantityInValidTest() {
        this.book.setTotalQuantity(-1);
        BookValidationState bookValidationState = this.addOrUpdateBookValidatorHandler.validate(book);
        assertEquals(false, bookValidationState.isSuccess());

        this.book.setTotalQuantity(TEST_TOTAL_QUANTITY_LESS_THAN_AVAILABLE_QUANTITY);
        bookValidationState = this.addOrUpdateBookValidatorHandler.validate(book);
        assertEquals(false, bookValidationState.isSuccess());
    }

    @Test
    public void totalQuantityValidTest() {
        BookValidationState bookValidationState = this.addOrUpdateBookValidatorHandler.validate(book);
        assertEquals(true, bookValidationState.isSuccess());
    }


    @Test
    public void pricePerUnitInValidTest() {
        this.book.setPricePerUnit(-1);
        BookValidationState bookValidationState = this.addOrUpdateBookValidatorHandler.validate(book);
        assertEquals(false, bookValidationState.isSuccess());
    }

    @Test
    public void pricePerUnitValidTest() {
        BookValidationState bookValidationState = this.addOrUpdateBookValidatorHandler.validate(book);
        assertEquals(true, bookValidationState.isSuccess());
    }

    @Test
    public void deleteBookInValidTest() {
        this.book.setId(BAD_TEST_ID);
        BookValidationState bookValidationState = this.deleteBookValidatorHandler.validate(book);
        assertEquals(false, bookValidationState.isSuccess());
    }

    @Test
    public void deleteBookValidTest() {
        BookValidationState bookValidationState = this.deleteBookValidatorHandler.validate(book);
        assertEquals(true, bookValidationState.isSuccess());
    }

}
