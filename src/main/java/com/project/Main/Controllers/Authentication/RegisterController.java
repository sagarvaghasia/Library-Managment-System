package com.project.Main.Controllers.Authentication;

import com.project.Main.Database.DBConnection;
import com.project.Main.Models.Security.Security;
import com.project.Main.Models.User.Factories.IUserRepositoryFactory;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.State.ValidationState.UserValidationState;
import com.project.Main.Models.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

    @RequestMapping("/register")
    public ModelAndView getRegisterPage(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {
            return registerRedirection(session);
        }

        return new ModelAndView("register");
    }

    @PostMapping("/registration")
    public ModelAndView registerUser(@ModelAttribute User user,
                                     HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {
            return registerRedirection(session);
        }

        IUserRepositoryFactory userRepositoryFactory = UserRepositoryFactory.instance();
        IUserRepository userRepository = userRepositoryFactory
                .createUserRepository(DBConnection.instance());

        user.setUserRepository(userRepository);
        user.setRole(IUser.USER_ROLE.ROLE_USER);
        UserValidationState userValidationState = user.saveUser();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("Message", userValidationState.getMessage());
        modelAndView.setViewName("register");

        return modelAndView;
    }

    public ModelAndView registerRedirection(HttpSession session) {

        IUser user = (IUser) session.getAttribute("user");

        if (user.getRole().equals(IUser.USER_ROLE.ROLE_USER)) {
            return new ModelAndView("redirect:/book/bookList");
        }

        if (user.getRole().equals(IUser.USER_ROLE.ROLE_LIBRARIAN)) {
            return new ModelAndView("redirect:/librarian/");
        }

        return new ModelAndView("redirect:/admin/dashboard");
    }
}
