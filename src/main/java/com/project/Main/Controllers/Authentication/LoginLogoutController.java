package com.project.Main.Controllers.Authentication;

import com.project.Main.Database.DBConnection;
import com.project.Main.Models.Security.Factories.ISecurityFactory;
import com.project.Main.Models.Security.Factories.SecurityFactory;
import com.project.Main.Models.Security.Security;
import com.project.Main.Models.User.Factories.IUserRepositoryFactory;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.State.LoginState.LoginState;
import com.project.Main.Models.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginLogoutController {

    @GetMapping("/login")
    public ModelAndView getLoginPage(@RequestParam(name = "Message", required = false) String message,
                                     HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {
            return new ModelAndView("redirect:/user/");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("Message", message);
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView processLogin(@ModelAttribute User user,
                                     HttpServletRequest request,
                                     RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();

        ISecurityFactory securityFactory = SecurityFactory.instance();
        IUserRepositoryFactory userRepositoryFactory = UserRepositoryFactory.instance();

        IUserRepository userRepository = userRepositoryFactory
                .createUserRepository(
                        DBConnection.instance()
                );

        user.setUserRepository(userRepository);

        LoginState loginState = securityFactory.createSecurity().login(user);

        if (loginState.isSuccess()) {

            session.setAttribute("user", user);

            if (user.getRole().equals(IUser.USER_ROLE.ROLE_USER)) {
                return new ModelAndView("redirect:/book/bookList");
            }
            return new ModelAndView("redirect:/dashboard");

        }

        redirectAttributes.addAttribute("Message", loginState.getMessage());

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logOut(HttpServletRequest request) {

        HttpSession session = request.getSession();

        ISecurityFactory securityFactory = SecurityFactory.instance();
        securityFactory.createSecurity().logout(session);

        return new ModelAndView("redirect:/login");
    }

}
