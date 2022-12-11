package com.project.Main.Controllers.Book;

import com.project.Main.Database.DBConnection;
import com.project.Main.Models.Book.Factories.BookFactory;
import com.project.Main.Models.Book.Factories.BookRepositoryFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.BookFilter.Factories.BookFilterFactory;
import com.project.Main.Models.BookFilter.IBookFilter;
import com.project.Main.Models.Security.Security;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.UserFilter.Factories.UserFilterFactory;
import com.project.Main.Models.UserFilter.IUserFilter;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class RetrieveController {

    @RequestMapping(value = "/bookList")
    public ModelAndView getBooks(@RequestParam(name = "Message", required = false) String message,
                                 HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IBookRepository bookRepository = BookRepositoryFactory
                    .instance()
                    .createBookRepository(
                            DBConnection.instance()
                    );

            IBookFilter bookFilter = BookFilterFactory
                    .instance()
                    .createBookFilter(bookRepository);

            Map.Entry<List<IBook>, FilterState> booksResponse = bookFilter.getBooks();

            FilterState filterState = booksResponse.getValue();

            ModelAndView modelAndView = new ModelAndView();

            if (filterState.isSuccess()) {

                IUser user = (IUser) session.getAttribute("user");
                List<IBook> books = booksResponse.getKey();
                modelAndView.addObject("books", books);
                modelAndView.addObject("user", user);
            }

            modelAndView.addObject("Message", filterState.getMessage());
            modelAndView.addObject("user", session.getAttribute("user"));

            modelAndView.setViewName("book-list");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/bookSearch")
    public ModelAndView getSearchedBook(String search, HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IBookRepository bookRepository = BookRepositoryFactory
                    .instance()
                    .createBookRepository(
                            DBConnection.instance()
                    );

            IBookFilter bookFilter = BookFilterFactory
                    .instance()
                    .createBookFilter(bookRepository);

            ModelAndView modelAndView = new ModelAndView();

            Map.Entry<List<IBook>, FilterState> booksResponse = bookFilter.getBooks(search);
            FilterState filterState = booksResponse.getValue();

            if (filterState.isSuccess()) {

                IUser user = (IUser) session.getAttribute("user");
                List<IBook> books = booksResponse.getKey();
                modelAndView.addObject("books", books);
                modelAndView.addObject("user", user);

            }
            modelAndView.addObject("Message", filterState.getMessage());

            modelAndView.setViewName("book-list");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/bookHistory")
    public ModelAndView getIssuedBookHistoryForUser(@RequestParam(name = "Message", required = false) String message,
                                                    HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IUser user = (IUser) session.getAttribute("user");

            IBookRepository bookRepository = BookRepositoryFactory
                    .instance()
                    .createBookRepository(
                            DBConnection.instance()
                    );

            IBookFilter bookFilter = BookFilterFactory
                    .instance()
                    .createBookFilter(bookRepository);

            Map.Entry<List<IBook>, FilterState> returnBooksResponse = bookFilter.getReturnedBooksForUser(user.getId());

            ModelAndView modelAndView = new ModelAndView();

            FilterState filterState = returnBooksResponse.getValue();

            if (filterState.isSuccess()) {

                Map.Entry<IBook, FilterState> currentBooksResponse = bookFilter.getCurrentlyIssuedBookForUser(user.getId());

                filterState = currentBooksResponse.getValue();

                if (filterState.isSuccess()) {

                    List<IBook> books = returnBooksResponse.getKey();
                    IBook book = currentBooksResponse.getKey();
                    modelAndView.addObject("returnedBooks", books);
                    modelAndView.addObject("currentBook", book);
                    modelAndView.addObject("user", user);
                    modelAndView.addObject("Message", message);

                }
                modelAndView.addObject("Message", filterState.getMessage());

            }

            modelAndView.addObject("Message", filterState.getMessage());

            modelAndView.setViewName("book-history");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/bookDetailsPage")
    public ModelAndView getIssuedUserDetailsForBook(@RequestParam(name = "id") int id,
                                                    HttpServletRequest request,
                                                    RedirectAttributes redirectAttributes) {

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

                IUserRepository userRepository = UserRepositoryFactory.
                        instance().
                        createUserRepository(
                                DBConnection.instance()
                        );

                IUserFilter userFilter = UserFilterFactory
                        .instance()
                        .createUserFilter(userRepository);

                Map.Entry<List<IUser>, FilterState> getIssuedBookStatus = userFilter.getIssuedUserListForBook(book.getId());

                FilterState filterState = getIssuedBookStatus.getValue();

                if (filterState.isSuccess()) {

                    modelAndView.setViewName("book-details");
                    modelAndView.addObject("book", book);
                    modelAndView.addObject("issuedUserList", getIssuedBookStatus.getKey());

                } else {

                    modelAndView.setViewName("redirect:/book/bookList");
                    redirectAttributes.addAttribute("Message", filterState.getMessage());

                }

            } else {

                redirectAttributes.addAttribute("Message", loadState.getMessage());
                modelAndView.setViewName("redirect:/book/bookList");

            }

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

}
