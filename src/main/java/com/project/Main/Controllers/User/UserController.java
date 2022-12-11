package com.project.Main.Controllers.User;

import com.project.Main.Database.DBConnection;
import com.project.Main.Models.Security.Security;
import com.project.Main.Models.User.Factories.IUserRepositoryFactory;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.State.ValidationState.UserValidationState;
import com.project.Main.Models.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping(value = "/update")
    public ModelAndView getUpdate(@RequestParam(name = "Message", required = false) String message,
                                  HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            IUser user = (IUser) session.getAttribute("user");

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("user", user);
            modelAndView.addObject("Message", message);
            modelAndView.setViewName("update-user");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/update")
    public ModelAndView postUpdate(@ModelAttribute User user,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            ModelAndView modelAndView = new ModelAndView();

            IUserRepositoryFactory userRepositoryFactory = UserRepositoryFactory.instance();
            IUserRepository userRepository = userRepositoryFactory
                    .createUserRepository(DBConnection.instance());

            user.setUserRepository(userRepository);
            UserValidationState userValidationState = user.updateUser();

            session.setAttribute("user", user);

            redirectAttributes.addAttribute("Message", userValidationState.getMessage());
            modelAndView.setViewName("redirect:/user/update");

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

}
