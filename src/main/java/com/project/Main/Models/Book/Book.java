package com.project.Main.Models.Book;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.CommonState.LoadState.LoadNotAvailableState;
import com.project.Main.Models.CommonState.LoadState.LoadSQLFailureState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import com.project.Main.Models.CommonState.LoadState.LoadSuccessState;

import java.util.Map;

public class Book extends IBook {


    public Book() {

    }

    public Book(IBookRepository bookRepository) {
        super(bookRepository);
    }

    public Book(int id, String title, String author, String publisher,
                int availableQuantity, int totalQuantity, float pricePerUnit,
                String type, IBookRepository bookRepository) {
        super(id, title, author, publisher, availableQuantity, totalQuantity, pricePerUnit, type, bookRepository);
    }

    @Override
    public LoadState loadBookById(int id) {

        Map.Entry<IBook, DatabaseState> response = this.bookRepository.loadBookById(id);
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {
            IBook loadedBook = response.getKey();
            this.loadBook(loadedBook);
            return new LoadSuccessState();
        } else {
            if (databaseState.isSQLException()) {
                return new LoadSQLFailureState();
            }
            return new LoadNotAvailableState();
        }

    }

    @Override
    public Map.Entry<IBook, DatabaseState> addBook() {
        return bookRepository.addBook(this);
    }

    @Override
    public DatabaseState deleteBook() {
        return this.bookRepository.deleteBook(this);
    }

    @Override
    public DatabaseState updateBook() {
        return this.bookRepository.updateBook(this);
    }

    private void loadBook(IBook loadedBook) {
        this.setId(loadedBook.getId());
        this.setTitle(loadedBook.getTitle());
        this.setAuthor(loadedBook.getAuthor());
        this.setPublisher(loadedBook.getPublisher());
        this.setAvailableQuantity(loadedBook.getAvailableQuantity());
        this.setTotalQuantity(loadedBook.getTotalQuantity());
        this.setPricePerUnit(loadedBook.getPricePerUnit());
        this.setType(loadedBook.getType());
        this.setBookRepository(loadedBook.getBookRepository());
    }
}
