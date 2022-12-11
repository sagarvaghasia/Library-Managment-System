package com.project.Main.Models.BookFilter;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.CommonState.FilterState.FilterNotAvailableState;
import com.project.Main.Models.CommonState.FilterState.FilterSQLFailureState;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import com.project.Main.Models.CommonState.FilterState.FilterSuccessState;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class BookFilter implements IBookFilter {

    private IBookRepository bookRepository;

    public BookFilter(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Map.Entry<List<IBook>, FilterState> getBooks() {

        Map.Entry<List<IBook>, DatabaseState> response = bookRepository.getBooks();

        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {

            List<IBook> books = response.getKey();

            if (books == null) {
                return new AbstractMap.SimpleEntry<>(null, new FilterNotAvailableState());
            }
            return new AbstractMap.SimpleEntry<>(books, new FilterSuccessState());

        }
        if (databaseState.isSQLException()) {
            return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
        }

        return new AbstractMap.SimpleEntry<>(null, new FilterSuccessState());
    }

    @Override
    public Map.Entry<List<IBook>, FilterState> getBooks(String name) {

        Map.Entry<List<IBook>, DatabaseState> response = bookRepository.getBooks(name);
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {

            List<IBook> books = response.getKey();
            if (books == null) {
                return new AbstractMap.SimpleEntry<>(null, new FilterNotAvailableState());
            }
            return new AbstractMap.SimpleEntry<>(books, new FilterSuccessState());

        }
        if (databaseState.isSQLException()) {
            return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
        }

        return new AbstractMap.SimpleEntry<>(null, new FilterSuccessState());
    }

    @Override
    public Map.Entry<IBook, FilterState> getCurrentlyIssuedBookForUser(int userId) {

        Map.Entry<IBook, DatabaseState> response = bookRepository.getCurrentlyIssuedBooksForUser(userId);
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {
            return new AbstractMap.SimpleEntry<>(response.getKey(), new FilterSuccessState());
        } else {
            if (databaseState.isSQLException()) {
                return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
            }
            return new AbstractMap.SimpleEntry<>(null, new FilterNotAvailableState());
        }

    }

    @Override
    public Map.Entry<List<IBook>, FilterState> getReturnedBooksForUser(int userId) {

        Map.Entry<List<IBook>, DatabaseState> response = bookRepository.getReturnedBooksForUser(userId);
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {
            List<IBook> books = response.getKey();
            return new AbstractMap.SimpleEntry<>(books, new FilterSuccessState());
        } else {
            if (databaseState.isSQLException()) {
                return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
            }
            return new AbstractMap.SimpleEntry<>(null, new FilterNotAvailableState());
        }

    }

    @Override
    public Map.Entry<Integer, FilterState> getBookCount() {

        Map.Entry<Integer, DatabaseState> response = bookRepository.getBookCount();
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {
            return new AbstractMap.SimpleEntry<>(response.getKey(), new FilterSuccessState());
        } else {
            if (databaseState.isSQLException()) {
                return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
            }
            return new AbstractMap.SimpleEntry<>(null, new FilterNotAvailableState());
        }

    }
}
