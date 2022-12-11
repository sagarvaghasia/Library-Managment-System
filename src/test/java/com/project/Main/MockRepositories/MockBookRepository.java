package com.project.Main.MockRepositories;

import com.project.Main.Database.DBConnection;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.LogicFailureState;
import com.project.Main.Database.State.SQLFailureState;
import com.project.Main.Database.State.SuccessState;
import com.project.Main.Models.Book.Book;
import com.project.Main.Models.Book.Factories.BookFactory;
import com.project.Main.Models.Book.Factories.BookRepositoryFactory;
import com.project.Main.Models.Book.Factories.IBookFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnRepositoryFactory;
import com.project.Main.Models.BookIssueReturn.Factories.IBookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockBookRepository implements IBookRepository {

    private static IBookFactory bookFactory = BookFactory.instance();

    private static IBookIssueReturnFactory bookIssueReturnFactory = BookIssueReturnFactory.instance();

    private static List<IBook> books = new ArrayList<>();

    private static List<IBookIssueReturn> bookIssueReturnList = new ArrayList<>();

    public MockBookRepository(DBConnection dbConnection) {

        IBookRepository bookRepository = BookRepositoryFactory
                .instance()
                .createBookRepository(
                        null
                );

        IBook book1 = bookFactory.createBook(1, "abc", "abc", "abc", 1, 2, (float) 20.2, "abc", bookRepository);
        books.add(book1);

        IBook book2 = bookFactory.createBook(2, "abc", "abc", "abc", 1, 2, (float) 20.2, "abc", bookRepository);
        books.add(book2);


        IBookIssueReturnRepository bookIssueReturnRepository = BookIssueReturnRepositoryFactory
                .instance()
                .createIssueReturnRepository(
                        null
                );

        IBookIssueReturn bookIssueReturn1 = bookIssueReturnFactory.createBookIssueReturn(1, 1, 1, Date.valueOf(LocalDate.now()), 0, bookIssueReturnRepository);
        bookIssueReturnList.add(bookIssueReturn1);

        IBookIssueReturn bookIssueReturn2 = bookIssueReturnFactory.createBookIssueReturn(2, 2, 1, Date.valueOf(LocalDate.now()), 1, bookIssueReturnRepository);
        bookIssueReturnList.add(bookIssueReturn2);

    }

    @Override
    public Map.Entry<IBook, DatabaseState> loadBookById(int id) {

        for (IBook b : books) {
            if (b.getId() == id) {
                return new AbstractMap.SimpleEntry<>(b, new SuccessState());
            }
        }

        return new AbstractMap.SimpleEntry<>(null, new LogicFailureState());
    }

    @Override
    public Map.Entry<IBook, DatabaseState> addBook(IBook book) {

        for (IBook b : books) {
            if (b.getId() == book.getId()) {
                return new AbstractMap.SimpleEntry<>(book, new SuccessState());
            }
        }

        return new AbstractMap.SimpleEntry<>(book, new LogicFailureState());
    }

    @Override
    public DatabaseState deleteBook(IBook book) {

        List<IBook> bookList = books;

        for (IBook b : bookList) {
            if (b.getId() == book.getId()) {
                return new SuccessState();
            }
        }

        return new LogicFailureState();
    }

    @Override
    public DatabaseState updateBook(IBook book) {
        book.setAuthor("A1");
        book.setPublisher("P1");
        book.setTitle("T1");
        book.setAvailableQuantity(20);
        book.setTotalQuantity(20);
        book.setPricePerUnit(20);
        book.setType("TYPE1");

        if (book.getAuthor() == "A1" && book.getPublisher() == "P1"
                && book.getTitle() == "T1" && book.getType() == "TYPE1"
                && book.getTotalQuantity() == 20 && book.getAvailableQuantity() == 20 && book.getPricePerUnit() == 20) {

            return new SuccessState();
        }

        return new LogicFailureState();
    }

    @Override
    public Map.Entry<List<IBook>, DatabaseState> getReturnedBooksForUser(int userId) {

        List<IBook> bookList = new ArrayList<>();

        for (IBookIssueReturn iBookIssueReturn : bookIssueReturnList) {
            if (iBookIssueReturn.getUserId() == userId && iBookIssueReturn.getReturnStatus() == 1) {
                return new AbstractMap.SimpleEntry<>(bookList, new SuccessState());
            }
        }

        return new AbstractMap.SimpleEntry<>(bookList, new LogicFailureState());
    }

    @Override
    public Map.Entry<IBook, DatabaseState> getCurrentlyIssuedBooksForUser(int userId) {

        IBook book = new Book();

        for (IBookIssueReturn iBookIssueReturn : bookIssueReturnList) {
            if (iBookIssueReturn.getUserId() == userId && iBookIssueReturn.getReturnStatus() == 0) {
                return new AbstractMap.SimpleEntry<>(book, new SuccessState());
            }
        }

        return new AbstractMap.SimpleEntry<>(book, new LogicFailureState());
    }

    @Override
    public Map.Entry<List<IBook>, DatabaseState> getBooks(String name) {

        if (name == null) {
            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

        List<IBook> bs = new ArrayList<>();

        for (IBook book : books) {
            if (book.getTitle().contains(name) ||
                    book.getAuthor().contains(name) ||
                    book.getPublisher().contains(name) ||
                    book.getType().contains(name)) {
                bs.add(book);
            }
        }

        return new AbstractMap.SimpleEntry<>(bs, new SuccessState());
    }

    @Override
    public Map.Entry<Integer, DatabaseState> getBookCount() {
        return new AbstractMap.SimpleEntry<>(5, new SuccessState());
    }

    @Override
    public Map.Entry<List<IBook>, DatabaseState> getBooks() {
        return new AbstractMap.SimpleEntry<>(books, new SuccessState());
    }

}
