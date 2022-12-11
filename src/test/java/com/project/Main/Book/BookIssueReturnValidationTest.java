package com.project.Main.Book;

import com.project.Main.MockFactories.MockBookIssueReturnRepositoryFactory;
import com.project.Main.MockFactories.MockBookRepositoryFactory;
import com.project.Main.MockFactories.MockUserRepositoryFactory;
import com.project.Main.Models.Book.Factories.BookFactory;
import com.project.Main.Models.Book.Factories.IBookFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnValidationFactory;
import com.project.Main.Models.BookIssueReturn.Factories.IBookIssueReturnRepositoryFactory;
import com.project.Main.Models.BookIssueReturn.Factories.IBookIssueReturnValidationFactory;
import com.project.Main.Models.BookIssueReturn.Validation.BookIssueReturnValidation;
import com.project.Main.Models.BookIssueReturn.Validation.State.IssueReturnValidationState;
import com.project.Main.Models.User.Factories.IUserFactory;
import com.project.Main.Models.User.Factories.IUserRepositoryFactory;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookIssueReturnValidationTest {

    private static final int BOOK_TEST_ID = 1;
    private static final int BOOK_BAD_TEST_ID = 2;
    private static final String BOOK_TEST_TITLE = "abc";
    private static final String BOOK_TEST_AUTHOR = "abc";
    private static final String BOOK_TEST_PUBLISHER = "abc";
    private static final int BOOK_TEST_AVAILABLE_QUANTITY = 3;
    private static final int BOOK_TEST_TOTAL_QUANTITY = 5;
    private static final float BOOK_TEST_PRICE_PER_UNIT = (float) 20.2;
    private static final String BOOK_TEST_TYPE = "abc";
    private static final int USER_TEST_ID = 1;
    private static final String USER_TEST_NAME = "abc";
    private static final String USER_TEST_EMAIL_ID = "khushalgondaliya@gmail.com";
    private static final String USER_TEST_BAD_EMAIL_ID = "abc";
    private static final long USER_TEST_CONTACT_NUMBER = 1234567890;
    private static final String USER_TEST_PASSWORD = "abc";
    private static final IUser.USER_ROLE USER_TEST_ROLE = IUser.USER_ROLE.ROLE_USER;
    private static IBookFactory bookFactory = BookFactory.instance();
    private static IUserFactory userFactory = UserFactory.instance();
    private static IBookIssueReturnValidationFactory bookIssueReturnValidationFactory = BookIssueReturnValidationFactory.instance();
    private static IUserRepositoryFactory mockUserRepositoryFactory = MockUserRepositoryFactory.instance();
    private IBook book;
    private BookIssueReturnValidation bookIssueReturnValidation;
    private IUser user;
    private IBookIssueReturnRepositoryFactory mockBookIssueReturnRepositoryFactory = MockBookIssueReturnRepositoryFactory.instance();

    @BeforeEach
    public void setUp() {
        this.book = bookFactory.createBook(BOOK_TEST_ID,
                BOOK_TEST_TITLE,
                BOOK_TEST_AUTHOR,
                BOOK_TEST_PUBLISHER,
                BOOK_TEST_AVAILABLE_QUANTITY,
                BOOK_TEST_TOTAL_QUANTITY,
                BOOK_TEST_PRICE_PER_UNIT,
                BOOK_TEST_TYPE,
                MockBookRepositoryFactory.
                        instance()
                        .createBookRepository(null)
        );

        this.user = userFactory.createUser(
                USER_TEST_NAME,
                USER_TEST_EMAIL_ID,
                USER_TEST_CONTACT_NUMBER,
                USER_TEST_PASSWORD,
                USER_TEST_ROLE,
                mockUserRepositoryFactory.createUserRepository(null));

        this.user.setId(USER_TEST_ID);

        this.bookIssueReturnValidation = bookIssueReturnValidationFactory.createBookIssueReturnValidation(
                mockBookIssueReturnRepositoryFactory.createIssueReturnRepository(null),
                mockUserRepositoryFactory.createUserRepository(null)
        );

    }

    @Test
    public void userEmailInValidTest() {
        user.setEmailId(USER_TEST_BAD_EMAIL_ID);
        IssueReturnValidationState issueReturnValidationState = this.bookIssueReturnValidation.validate(book.getId(), user.getEmailId());
        assertEquals(false, issueReturnValidationState.hasUserIssuedAnyBook());
    }

    @Test
    public void userEmailValidTest() {
        IssueReturnValidationState issueReturnValidationState = this.bookIssueReturnValidation.validate(book.getId(), user.getEmailId());
        assertEquals(true, issueReturnValidationState.hasUserIssuedAnyBook());
    }

    @Test
    public void bookIdInValidTest() {
        book.setId(BOOK_BAD_TEST_ID);
        IssueReturnValidationState issueReturnValidationState = this.bookIssueReturnValidation.validate(book.getId(), user.getEmailId());
        assertEquals(false, issueReturnValidationState.hasUserIssuedAnyBook());
    }

    @Test
    public void bookIdValidTest() {
        IssueReturnValidationState issueReturnValidationState = this.bookIssueReturnValidation.validate(book.getId(), user.getEmailId());
        assertEquals(true, issueReturnValidationState.hasUserIssuedAnyBook());
    }

}
