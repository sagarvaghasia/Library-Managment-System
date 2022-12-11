package com.project.Main.Controllers.Book;

import com.project.Main.Database.DBConnection;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.Book.Book;
import com.project.Main.Models.Book.Factories.BookFactory;
import com.project.Main.Models.Book.Factories.BookRepositoryFactory;
import com.project.Main.Models.Book.Factories.BookValidationHandlerFactory;
import com.project.Main.Models.Book.Factories.IBookValidationHandlerFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.Book.Validation.BookValidatorHandler;
import com.project.Main.Models.Book.Validation.State.BookValidationState;
import com.project.Main.Models.Security.Security;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/book")
public class UpdateController {

    @RequestMapping(value = "/updateBookPage")
    public ModelAndView updateBookPage(@RequestParam(name = "Message", required = false) String message,
                                       @RequestParam(name = "id") int id,
                                       HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IBookRepository bookRepository = BookRepositoryFactory
                    .instance()
                    .createBookRepository(
                            DBConnection.instance()
                    );

            IBook book = BookFactory
                    .instance()
                    .createBook(bookRepository);

            ModelAndView modelAndView = new ModelAndView();
            LoadState loadState = book.loadBookById(id);

            if (loadState.isSuccess()) {

                modelAndView.addObject("book", book);
                modelAndView.addObject("Message", message);

            }

            modelAndView.addObject("Message", loadState.getMessage());

            modelAndView.setViewName("update-book");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping(value = "/updateBook")
    public ModelAndView updateBook(@ModelAttribute Book book,
                                   RedirectAttributes redirectAttributes,
                                   HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IBookValidationHandlerFactory bookValidationHandlerFactory = BookValidationHandlerFactory.instance();
            BookValidatorHandler bookValidatorHandler = bookValidationHandlerFactory.createAddOrUpdateBookValidationHandler();
            BookValidationState bookValidationState = bookValidatorHandler.validate(book);

            ModelAndView modelAndView = new ModelAndView();

            if (bookValidationState.isSuccess()) {

                IBookRepository bookRepository = BookRepositoryFactory
                        .instance()
                        .createBookRepository(
                                DBConnection.instance()
                        );

                book.setBookRepository(bookRepository);
                DatabaseState state = book.updateBook();

                redirectAttributes.addAttribute("Message", state.getMessage());
                modelAndView.setViewName("redirect:/book/bookList");

            } else {

                redirectAttributes.addAttribute("Message", bookValidationState.getMessage());
                modelAndView.setViewName("redirect:/book/updateBookPage");
                modelAndView.addObject("id", book.getId());

            }

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

}
