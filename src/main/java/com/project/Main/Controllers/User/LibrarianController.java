package com.project.Main.Controllers.User;

import com.project.Main.Database.DBConnection;
import com.project.Main.Models.Security.Security;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.State.ValidationState.UserValidationState;
import com.project.Main.Models.User.User;
import com.project.Main.Models.UserFilter.Factories.IUserFilterFactory;
import com.project.Main.Models.UserFilter.Factories.UserFilterFactory;
import com.project.Main.Models.UserFilter.IUserFilter;
import com.project.Main.Models.CommonState.DeleteState.DeleteState;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/librarian")
public class LibrarianController {

    @GetMapping(value = "/")
    public ModelAndView getLibrarian(@RequestParam(name = "search", required = false) String search,
                                     @RequestParam(name = "Message", required = false) String message,
                                     HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IUserRepository userRepository = UserRepositoryFactory
                    .instance()
                    .createUserRepository(
                            DBConnection.instance()
                    );

            IUserFilterFactory userFilterFactory = UserFilterFactory.instance();
            IUserFilter userFilter = userFilterFactory.createUserFilter(userRepository);

            Map.Entry<List<IUser>, FilterState> librarianResponse = null;

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("Message", message);

            if (search == null) {
                librarianResponse = userFilter.getUsers(IUser.USER_ROLE.ROLE_LIBRARIAN);
            } else {
                librarianResponse = userFilter.getUsers(search, IUser.USER_ROLE.ROLE_LIBRARIAN);
                modelAndView.addObject("search", search);
            }

            FilterState filterState = librarianResponse.getValue();

            if (filterState.isSuccess()) {
                modelAndView.addObject("librarians", librarianResponse.getKey());
            } else {
                modelAndView.addObject("Message", filterState.getMessage());
            }

            modelAndView.setViewName("librarian-list");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @GetMapping(value = "/add")
    public ModelAndView getAddLibrarian(@RequestParam(name = "Message", required = false) String message,
                                        HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("Message", message);
            modelAndView.setViewName("add-librarian");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping(value = "/add")
    public ModelAndView postAddLibrarian(@ModelAttribute(name = "librarian") User user, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IUserRepository userRepository = UserRepositoryFactory
                    .instance()
                    .createUserRepository(
                            DBConnection.instance()
                    );

            user.setUserRepository(userRepository);
            user.setRole(IUser.USER_ROLE.ROLE_LIBRARIAN);

            UserValidationState state = user.saveUser();

            redirectAttributes.addAttribute("Message", state.getMessage());

            return new ModelAndView("redirect:/librarian/add");
        }

        return new ModelAndView("redirect:/login");
    }

    @GetMapping(value = "/update")
    public ModelAndView getUpdate(@RequestParam(name = "id") int id,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {

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

            LoadState loadStatus = user.loadUserById(id);

            if (loadStatus.isSuccess()) {

                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("librarian", user);
                modelAndView.setViewName("update-librarian");

                return modelAndView;
            }

            redirectAttributes.addAttribute("Message", loadStatus.getMessage());

            return new ModelAndView("redirect:/librarian/");
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping(value = "/update")
    public ModelAndView postUpdate(@ModelAttribute(name = "librarian") User user,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {


        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IUserRepository userRepository = UserRepositoryFactory
                    .instance()
                    .createUserRepository(
                            DBConnection.instance()
                    );

            user.setUserRepository(userRepository);

            UserValidationState userValidationState = user.updateUser();

            redirectAttributes.addAttribute("Message", userValidationState.getMessage());

            return new ModelAndView("redirect:/librarian/");
        }

        return new ModelAndView("redirect:/login");
    }

    @GetMapping(value = "/delete")
    public ModelAndView getDelete(@RequestParam(name = "id") int id,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {

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

            LoadState loadStatus = user.loadUserById(id);

            if (loadStatus.isSuccess()) {

                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("librarian", user);
                modelAndView.setViewName("delete-librarian");

                return modelAndView;
            }

            redirectAttributes.addAttribute("Message", loadStatus.getMessage());

            return new ModelAndView("redirect:/librarian/");
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping(value = "/delete")
    public ModelAndView postDelete(@ModelAttribute(name = "librarian") User user,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {


        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IUserRepository userRepository = UserRepositoryFactory
                    .instance()
                    .createUserRepository(
                            DBConnection.instance()
                    );

            user.setUserRepository(userRepository);

            DeleteState deleteStatus = user.deleteUser();

            redirectAttributes.addAttribute("Message", deleteStatus.getMessage());

            return new ModelAndView("redirect:/librarian/");
        }

        return new ModelAndView("redirect:/login");
    }

}
