package com.project.Main.Models.GeneratePDF;

import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.User.IUser;

import java.io.File;
import java.util.List;

public interface IGeneratePDF {
    public File generatePdf(List<IBook> books, IUser user);
}
