package com.project.Main.Models.Book;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public abstract class IBook {

    @Getter @Setter protected int id;
    @Getter @Setter protected String title;
    @Getter @Setter protected String author;
    @Getter @Setter protected String publisher;
    @Getter @Setter protected int availableQuantity ;
    @Getter @Setter protected int totalQuantity;
    @Getter @Setter protected float pricePerUnit;
    @Getter @Setter protected String type;
    @Getter @Setter protected IBookRepository bookRepository;

    public IBook(){
        
    }

    public IBook(int id, String title, String author, String publisher, int availableQuantity, int totalQuantity, float pricePerUnit, String type, IBookRepository bookRepository) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.availableQuantity = availableQuantity;
        this.totalQuantity = totalQuantity;
        this.pricePerUnit = pricePerUnit;
        this.type = type;
        this.bookRepository = bookRepository;
    }

    public IBook(IBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public abstract LoadState loadBookById(int id);
    public abstract Map.Entry<IBook, DatabaseState> addBook();
    public abstract DatabaseState deleteBook();
    public abstract DatabaseState updateBook();

}
