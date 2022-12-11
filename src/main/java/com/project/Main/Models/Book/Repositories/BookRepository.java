package com.project.Main.Models.Book.Repositories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.LogicFailureState;
import com.project.Main.Database.State.SQLFailureState;
import com.project.Main.Database.State.SuccessState;
import com.project.Main.Models.Book.Book;
import com.project.Main.Models.Book.Factories.BookFactory;
import com.project.Main.Models.Book.Factories.IBookFactory;
import com.project.Main.Models.Book.IBook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookRepository implements IBookRepository {

    private static IBookFactory bookFactory = BookFactory.instance();
    public final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final IDBConnection dbConn;

    public BookRepository(IDBConnection dbConn) {
        this.dbConn = dbConn;
    }

    public static List<IBook> loadFromResultSet(ResultSet resultSet,
                                                IBookRepository bookRepository) throws SQLException {
        List<IBook> books = new ArrayList<IBook>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            int availableQuantity = resultSet.getInt("available_quantity");
            int totalQuantity = resultSet.getInt("total_quantity");
            float pricePerUnit = resultSet.getFloat("priceperunit");
            String type = resultSet.getString("type");
            IBook book = bookFactory.createBook(id, title, author, publisher, availableQuantity,
                    totalQuantity, pricePerUnit, type, bookRepository);
            books.add(book);
        }

        return books;
    }

    @Override
    public Map.Entry<IBook, DatabaseState> loadBookById(int id) {
        IBook book = null;
        try {
            Connection conn = dbConn.getConnection();
            String query = "select * from book where id = ?";
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                book = new Book(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6), resultSet.getFloat(7),
                        resultSet.getString(8),
                        this);
                break;
            }
            if (book == null) {
                return new AbstractMap.SimpleEntry<>(book, new LogicFailureState());
            }

            return new AbstractMap.SimpleEntry<>(book, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<IBook, DatabaseState> addBook(IBook book) {

        try {
            Connection conn = dbConn.getConnection();
            String query = "insert into book(title,author,publisher,available_quantity,total_quantity,priceperunit,type) values(?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setInt(4, book.getAvailableQuantity());
            preparedStatement.setInt(5, book.getTotalQuantity());
            preparedStatement.setFloat(6, book.getPricePerUnit());
            preparedStatement.setString(7, book.getType());

            int numberOfRowsAffected = preparedStatement.executeUpdate();
            int autoGeneratedId = -1;

            if (numberOfRowsAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                autoGeneratedId = resultSet.getInt(1);
                book.setId(autoGeneratedId);
                return new AbstractMap.SimpleEntry(book, new SuccessState());
            }

            return new AbstractMap.SimpleEntry<>(book, new LogicFailureState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry(book, new SQLFailureState());
        }

    }

    @Override
    public DatabaseState deleteBook(IBook book) {

        int id = book.getId();
        try {
            Connection conn = dbConn.getConnection();
            String query = "delete from book where id = ?";
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            prepareStatement.setInt(1, id);

            int numberOfRowsAffected = prepareStatement.executeUpdate();

            if (numberOfRowsAffected == 1) {
                return new SuccessState();
            } else {
                return new LogicFailureState();
            }

        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new SQLFailureState();
        }

    }

    @Override
    public DatabaseState updateBook(IBook book) {

        int bookId = book.getId();
        try {
            Connection conn = dbConn.getConnection();
            String query = "update book set title=?,author=?,publisher=?,available_quantity=?,total_quantity=?,priceperunit=?,type=? where id = ?";
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            prepareStatement.setString(1, book.getTitle());
            prepareStatement.setString(2, book.getAuthor());
            prepareStatement.setString(3, book.getPublisher());
            prepareStatement.setLong(4, book.getAvailableQuantity());
            prepareStatement.setLong(5, book.getTotalQuantity());
            prepareStatement.setFloat(6, book.getPricePerUnit());
            prepareStatement.setString(7, book.getType());
            prepareStatement.setInt(8, bookId);
            int numberOfRowsAffected = prepareStatement.executeUpdate();
            if (numberOfRowsAffected == 1) {
                return new SuccessState();
            } else {
                return new LogicFailureState();
            }
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new SQLFailureState();
        }

    }

    @Override
    public Map.Entry<List<IBook>, DatabaseState> getBooks() {

        try {
            Connection conn = this.dbConn.getConnection();
            String query = "select * from book";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<IBook> books = loadFromResultSet(resultSet, this);

            return new AbstractMap.SimpleEntry<>(books, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<List<IBook>, DatabaseState> getReturnedBooksForUser(int userId) {

        try {
            Connection conn = this.dbConn.getConnection();
            String query = "select * from book where id in (select book_id from issued_books where user_id = ? and return_status = 1 ORDER BY issued_date DESC); ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<IBook> books = loadFromResultSet(resultSet, this);

            return new AbstractMap.SimpleEntry<>(books, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

    }

    public Map.Entry<IBook, DatabaseState> getCurrentlyIssuedBooksForUser(int userId) {

        IBook book = null;
        try {
            Connection conn = this.dbConn.getConnection();
            String query = "select * from book where id in (select book_id from issued_books where user_id = ? and return_status = 0 ORDER BY issued_date DESC); ";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                book = new Book(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6), resultSet.getFloat(7),
                        resultSet.getString(8),
                        this);
                break;
            }

            return new AbstractMap.SimpleEntry<>(book, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(book, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<List<IBook>, DatabaseState> getBooks(String name) {
        try {
            Connection conn = this.dbConn.getConnection();
            String query = "select * from book where title like ? or author like ? or publisher like ? or type like ? ";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            String matchPattern = "%" + name + "%";
            preparedStatement.setString(1, matchPattern);
            preparedStatement.setString(2, matchPattern);
            preparedStatement.setString(3, matchPattern);
            preparedStatement.setString(4, matchPattern);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<IBook> books = loadFromResultSet(resultSet, this);

            return new AbstractMap.SimpleEntry<>(books, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<Integer, DatabaseState> getBookCount() {

        try {
            Connection conn = this.dbConn.getConnection();
            String query = "select count(id) as count from book";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return new AbstractMap.SimpleEntry<>(resultSet.getInt("count"), new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

    }
}
