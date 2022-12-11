package com.project.Main.Controllers.Book;

import com.project.Main.Database.DBConnection;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.Book.Book;
import com.project.Main.Models.Book.Factories.BookRepositoryFactory;
import com.project.Main.Models.Book.Factories.BookValidationHandlerFactory;
import com.project.Main.Models.Book.Factories.IBookValidationHandlerFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.Book.Validation.BookValidatorHandler;
import com.project.Main.Models.Book.Validation.State.BookValidationState;
import com.project.Main.Models.Security.Security;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class AddController {

    @GetMapping(value = "/addBookPage")
    public ModelAndView addBookPage(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {
            return new ModelAndView("add-book");
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/addbook")
    public ModelAndView addBook(@ModelAttribute Book book, HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IBookRepository bookRepository = BookRepositoryFactory
                    .instance()
                    .createBookRepository(
                            DBConnection.instance()
                    );

            book.setBookRepository(bookRepository);

            IBookValidationHandlerFactory bookValidationHandlerFactory = BookValidationHandlerFactory.instance();
            BookValidatorHandler bookValidatorHandler = bookValidationHandlerFactory.createAddOrUpdateBookValidationHandler();

            ModelAndView modelAndView = new ModelAndView();

            BookValidationState bookValidationState = bookValidatorHandler.validate(book);

            if (bookValidationState.isSuccess()) {

                Map.Entry<IBook, DatabaseState> addResponse = book.addBook();
                DatabaseState databaseState = addResponse.getValue();

                modelAndView.addObject("Message", databaseState.getMessage());

            }

            modelAndView.addObject("Message", bookValidationState.getMessage());

            modelAndView.setViewName("add-book");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

}
