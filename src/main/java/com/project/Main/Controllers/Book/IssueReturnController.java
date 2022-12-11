package com.project.Main.Controllers.Book;

import com.project.Main.Database.DBConnection;
import com.project.Main.Models.Book.Factories.BookFactory;
import com.project.Main.Models.Book.Factories.BookRepositoryFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.BookFilter.Factories.BookFilterFactory;
import com.project.Main.Models.BookFilter.IBookFilter;
import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnRepositoryFactory;
import com.project.Main.Models.BookIssueReturn.Factories.BookIssueReturnValidationFactory;
import com.project.Main.Models.BookIssueReturn.Factories.IBookIssueReturnFactory;
import com.project.Main.Models.BookIssueReturn.IBookIssueReturn;
import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;
import com.project.Main.Models.BookIssueReturn.State.IssueState.IssueState;
import com.project.Main.Models.BookIssueReturn.State.ReturnState.ReturnState;
import com.project.Main.Models.BookIssueReturn.Validation.BookIssueReturnValidation;
import com.project.Main.Models.BookIssueReturn.Validation.State.IssueReturnValidationState;
import com.project.Main.Models.GeneratePDF.GeneratePDF;
import com.project.Main.Models.Security.Security;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/book")
public class IssueReturnController {

    @RequestMapping(value = "/issueBookPage")
    public ModelAndView issueBookPage(@RequestParam(name = "Message", required = false) String message,
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

                if (book.getAvailableQuantity() <= 0) {

                    message = "Not enough quantity of book available.";
                    modelAndView.setViewName("redirect:/book/bookList");

                }

                modelAndView.addObject("Message", message);

            }

            modelAndView.addObject("Message", loadState.getMessage());

            modelAndView.setViewName("issue-book");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping(value = "/issueBook")
    public ModelAndView issueBook(@RequestParam(name = "bookId") int bookId,
                                  @RequestParam(name = "email") String email,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IUserRepository userRepository = UserRepositoryFactory
                    .instance()
                    .createUserRepository(
                            DBConnection.instance()
                    );
            IUser user = UserFactory
                    .instance()
                    .createUser(userRepository);

            IBookIssueReturnRepository bookIssueReturnRepository = BookIssueReturnRepositoryFactory.instance().createIssueReturnRepository(DBConnection.instance());
            BookIssueReturnValidation bookIssueReturnValidation = BookIssueReturnValidationFactory.instance().createBookIssueReturnValidation(bookIssueReturnRepository, userRepository);

            ModelAndView modelAndView = new ModelAndView();
            IssueReturnValidationState issueReturnValidationState = bookIssueReturnValidation.validate(bookId, email);

            if (issueReturnValidationState.hasUserIssuedAnyBook()) {

                modelAndView.setViewName("redirect:/book/issueBookPage");
                modelAndView.addObject("id", bookId);
                redirectAttributes.addAttribute("Message", issueReturnValidationState.getMessage());

            } else {

                user.loadUserByEmailId(email);
                IBookIssueReturnFactory bookIssueReturnFactory = BookIssueReturnFactory.instance();
                IBookIssueReturn bookIssueReturn = bookIssueReturnFactory.createBookIssueReturn(bookIssueReturnRepository);
                IssueState issueState = bookIssueReturn.issueBook(bookId, user.getId());
                modelAndView.setViewName("redirect:/book/bookList");
                redirectAttributes.addAttribute("Message", issueState.getMessage());

            }

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/returnBookPage")
    public ModelAndView returnBookPage(@RequestParam(name = "Message", required = false) String message,
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

                if (book.getAvailableQuantity() == book.getTotalQuantity()) {
                    message = "Book has not been issued by anyone";
                    modelAndView.setViewName("redirect:/book/bookList");
                }
                modelAndView.addObject("Message", message);

            }

            modelAndView.addObject("Message", loadState.getMessage());
            modelAndView.setViewName("return-book");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping(value = "/returnBook")
    public ModelAndView returnBook(@RequestParam(name = "bookId") int bookId,
                                   @RequestParam(name = "email") String email,
                                   RedirectAttributes redirectAttributes,
                                   HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IUserRepository userRepository = UserRepositoryFactory
                    .instance()
                    .createUserRepository(
                            DBConnection.instance()
                    );
            IUser user = UserFactory
                    .instance()
                    .createUser(userRepository);

            IBookIssueReturnRepository bookIssueReturnRepository = BookIssueReturnRepositoryFactory
                    .instance()
                    .createIssueReturnRepository(
                            DBConnection.instance()
                    );

            BookIssueReturnValidation bookIssueReturnValidation = BookIssueReturnValidationFactory.instance().createBookIssueReturnValidation(bookIssueReturnRepository, userRepository);

            ModelAndView modelAndView = new ModelAndView();
            IssueReturnValidationState issueReturnValidationState = bookIssueReturnValidation.validate(bookId, email);

            if (issueReturnValidationState.hasUserIssuedAnyBook()) {

                user.loadUserByEmailId(email);
                IBookIssueReturnFactory bookIssueReturnFactory = BookIssueReturnFactory.instance();
                IBookIssueReturn bookIssueReturn = bookIssueReturnFactory.createBookIssueReturn(bookIssueReturnRepository);
                ReturnState returnState = bookIssueReturn.returnBook(bookId, user.getId());


                redirectAttributes.addAttribute("id", user.getId());
                redirectAttributes.addAttribute("Message", returnState.getMessage());
                modelAndView.setViewName("redirect:/book/downloadPDF");

            } else {
                modelAndView.setViewName("redirect:/book/returnBookPage");
                modelAndView.addObject("id", bookId);
                redirectAttributes.addAttribute("Message", issueReturnValidationState.getMessage());

            }

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/downloadPDF")
    public void downloadPDF(@RequestParam(name = "id") int id,
                            @RequestParam(name = "Message", required = false) String message,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            GeneratePDF pdfService = new GeneratePDF();

            IUserRepository userRepository = UserRepositoryFactory
                    .instance()
                    .createUserRepository(
                            DBConnection.instance()
                    );

            IUser user = UserFactory.
                    instance()
                    .createUser(
                            userRepository
                    );

            LoadState loadStatus = user.loadUserById(id);

            IBookRepository bookRepository = BookRepositoryFactory
                    .instance()
                    .createBookRepository(
                            DBConnection.instance()
                    );

            IBookFilter bookFilter = BookFilterFactory
                    .instance()
                    .createBookFilter(
                            bookRepository
                    );

            Map.Entry<List<IBook>, FilterState> bookFilterResponse = bookFilter.getBooks();

            try {

                Path file = Paths.get(pdfService.generatePdf(bookFilterResponse.getKey(), user).getAbsolutePath());

                ModelAndView modelAndView = new ModelAndView();

                if (Files.exists(file)) {

                    response.setContentType("application/pdf");
                    response.addHeader("Content-Disposition",
                            "attachment; filename=" + file.getFileName());
                    Files.copy(file, response.getOutputStream());
                    response.getOutputStream().flush();

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
