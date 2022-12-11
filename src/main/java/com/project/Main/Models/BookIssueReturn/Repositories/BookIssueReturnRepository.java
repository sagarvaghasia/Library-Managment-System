package com.project.Main.Models.BookIssueReturn.Repositories;

import com.project.Main.Models.BookIssueReturn.BookIssueReturn;
import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Database.IDBConnection;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.LogicFailureState;
import com.project.Main.Database.State.SQLFailureState;
import com.project.Main.Database.State.SuccessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class BookIssueReturnRepository implements IBookIssueReturnRepository {

    public final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final IDBConnection dbConn;

    public BookIssueReturnRepository(IDBConnection dbConnection) {
        this.dbConn = dbConnection;
    }

    @Override
    public DatabaseState issueBook(int bookId, int userId) {

        try {
            Connection conn = dbConn.getConnection();
            conn.setAutoCommit(false);
            String query1 = "update book set available_quantity = available_quantity - 1 where id = ?";
            String query2 = "insert into issued_books (book_id,user_id,issued_date) values(?,?,now())";

            PreparedStatement prepareStatement = conn.prepareStatement(query1);
            prepareStatement.setInt(1, bookId);
            int affectedRows = prepareStatement.executeUpdate();
            prepareStatement = conn.prepareStatement(query2);

            prepareStatement.setInt(1, bookId);
            prepareStatement.setInt(2, userId);
            affectedRows += prepareStatement.executeUpdate();

            if (affectedRows == 2) {
                conn.commit();

                return new SuccessState();
            }

            return new LogicFailureState();
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new SQLFailureState();
        }

    }

    @Override
    public DatabaseState returnBook(IBookIssueReturn bookIssueReturn) {

        try {
            Connection conn = dbConn.getConnection();
            conn.setAutoCommit(false);

            String query1 = "update book set available_quantity = available_quantity + 1 where id = ?";
            PreparedStatement prepareStatement = conn.prepareStatement(query1);
            prepareStatement.setInt(1, bookIssueReturn.getBookId());
            int afftectedRows = prepareStatement.executeUpdate();

            String query2 = "insert into payments(issued_books_id,payment_value,date) values(?,?,now());";
            prepareStatement = conn.prepareStatement(query2);
            prepareStatement.setInt(1, bookIssueReturn.getId());

            java.util.Date issuedDate = new java.util.Date(bookIssueReturn.getDate().getTime());
            LocalDate localDate = java.time.LocalDate.now();
            java.util.Date currentDate = java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            long diff = currentDate.getTime() - issuedDate.getTime();
            long diffenceBetweenDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            float payment = (float) (diffenceBetweenDays * 0.1);

            prepareStatement.setFloat(2, payment);
            afftectedRows += prepareStatement.executeUpdate();

            String query3 = "update issued_books set return_status = 1 where id = ?";
            prepareStatement = conn.prepareStatement(query3);
            prepareStatement.setInt(1, bookIssueReturn.getId());
            afftectedRows += prepareStatement.executeUpdate();

            if (afftectedRows == 3) {
                conn.commit();

                return new SuccessState();
            }

            return new LogicFailureState();
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new SQLFailureState();
        }

    }

    @Override
    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBookByBookAndUserID(int bookId, int userId) {

        IBookIssueReturn bookIssueReturn = null;
        try {

            Connection conn = dbConn.getConnection();
            String query1 = "select * from issued_books where book_id = ? and user_id = ? and return_status = 0;";

            PreparedStatement prepareStatement = conn.prepareStatement(query1);
            prepareStatement.setInt(1, bookId);
            prepareStatement.setInt(2, userId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                bookIssueReturn = new BookIssueReturn(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getDate("issued_date"),
                        resultSet.getInt("return_status"),
                        this
                );
                break;
            }

            if (bookIssueReturn == null) {
                return new AbstractMap.SimpleEntry<>(bookIssueReturn, new LogicFailureState());
            }

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBooksByUserID(int userId) {

        IBookIssueReturn bookIssueReturn = null;
        try {

            Connection conn = dbConn.getConnection();
            String query1 = "select * from issued_books where user_id = ? and return_status = 0;";

            PreparedStatement prepareStatement = conn.prepareStatement(query1);
            prepareStatement.setInt(1, userId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                bookIssueReturn = new BookIssueReturn(
                        resultSet.getInt("id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDate("issued_date"),
                        resultSet.getInt("return_status"),
                        this
                );
                break;
            }

            if (bookIssueReturn == null) {
                return new AbstractMap.SimpleEntry<>(bookIssueReturn, new LogicFailureState());
            }

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<IBookIssueReturn, DatabaseState> loadIssuedBooksByBookID(int bookId) {

        IBookIssueReturn bookIssueReturn = null;
        try {
            Connection conn = dbConn.getConnection();
            String query1 = "select * from issued_books where book_id = ? and return_status = 0;";

            PreparedStatement prepareStatement = conn.prepareStatement(query1);
            prepareStatement.setInt(1, bookId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                bookIssueReturn = new BookIssueReturn(
                        resultSet.getInt("id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDate("issued_date"),
                        resultSet.getInt("return_status"),
                        this

                );
                break;
            }

            if (bookIssueReturn == null) {
                return new AbstractMap.SimpleEntry<>(bookIssueReturn, new LogicFailureState());
            }

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(bookIssueReturn, new SQLFailureState());
        }

    }
}
