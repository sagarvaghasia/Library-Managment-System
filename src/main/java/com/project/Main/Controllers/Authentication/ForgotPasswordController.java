package com.project.Main.Controllers.Authentication;

import com.project.Main.Database.DBConnection;
import com.project.Main.Models.EmailSender.Factories.JavaMailSenderFactory;
import com.project.Main.Models.EmailSender.IForgotPasswordEmailSender;
import com.project.Main.Models.ForgotPassword.Factories.ForgotPasswordEmailSenderFactory;
import com.project.Main.Models.ForgotPassword.Factories.ForgotPasswordFactory;
import com.project.Main.Models.ForgotPassword.Factories.ForgotPasswordRepositoryFactory;
import com.project.Main.Models.ForgotPassword.IForgotPassword;
import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.User;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {

    @GetMapping(value = "/forgot-password")
    public ModelAndView getForgotPassword(@RequestParam(name = "Message", required = false) String message) {

        ModelAndView modelAndView = new ModelAndView();
        if (message == null) {

        } else {
            modelAndView.addObject("Message", message);
        }

        modelAndView.setViewName("forgot-password");

        return modelAndView;
    }

    @PostMapping(value = "/forgot-password")
    public ModelAndView addForgotPassword(@RequestParam(name = "emailId") String emailId,
                                          RedirectAttributes redirectAttributes,
                                          HttpServletRequest request) {

        IUserRepository userRepository = UserRepositoryFactory
                .instance()
                .createUserRepository(DBConnection.instance());


        IUser user = UserFactory.instance().createUser(userRepository);
        LoadState status = user.loadUserByEmailId(emailId);

        if (status.isSuccess()) {
            IForgotPasswordRepository forgotPasswordRepository = ForgotPasswordRepositoryFactory
                    .instance()
                    .createForgotPasswordRepository(DBConnection.instance());
            IForgotPassword forgotPassword = ForgotPasswordFactory
                    .instance()
                    .createForgotPassword(user.getId(), forgotPasswordRepository);

            forgotPassword.addForgotRequest();

            JavaMailSender javaMailSender = JavaMailSenderFactory.instance().createJavaMailSender();

            IForgotPasswordEmailSender forgotPasswordEmailSender = ForgotPasswordEmailSenderFactory
                    .instance()
                    .createForgotPasswordEmailSender(javaMailSender);

            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();

            String link = baseUrl + "/change-password?token=" + forgotPassword.getTimestamp() + "&emailId=" + user.getEmailId();

            forgotPasswordEmailSender.send(link, user.getEmailId());

            redirectAttributes.addAttribute("Message", "Email send successfully");
            return new ModelAndView("redirect:/forgot-password");

        } else {
            redirectAttributes.addAttribute("Message", "User not found");
            return new ModelAndView("redirect:/forgot-password");
        }
    }

    @GetMapping(value = "/change-password")
    public ModelAndView getChangePassword(@RequestParam(name = "token") long token,
                                          @RequestParam(name = "emailId") String emailId,
                                          HttpServletRequest request) {

        IUserRepository userRepository = UserRepositoryFactory
                .instance()
                .createUserRepository(DBConnection.instance());

        IUser user = UserFactory.instance().createUser(userRepository);
        LoadState status = user.loadUserByEmailId(emailId);

        if (status.isSuccess()) {
            IForgotPasswordRepository forgotPasswordRepository = ForgotPasswordRepositoryFactory
                    .instance()
                    .createForgotPasswordRepository(DBConnection.instance());


            IForgotPassword forgotPassword = ForgotPasswordFactory
                    .instance()
                    .createForgotPassword(user.getId(), token, forgotPasswordRepository);

            boolean isTokenValid = forgotPassword.validateToken();

            if (isTokenValid) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("user", user);
                String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                        .replacePath(null)
                        .build()
                        .toUriString();
                modelAndView.addObject("url", baseUrl + "/change-password");
                modelAndView.setViewName("reset-password");
                return modelAndView;

            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("Message", "link is not valid");
        modelAndView.setViewName("message");
        return modelAndView;

    }

    @PostMapping(value = "/change-password")
    public ModelAndView postChangePassword(@ModelAttribute User user, RedirectAttributes redirectAttributes) {

        String password = user.getPassword();

        IUserRepository userRepository = UserRepositoryFactory
                .instance()
                .createUserRepository(DBConnection.instance());

        user.setUserRepository(userRepository);
        LoadState status = user.loadUserById(user.getId());

        if (status.isSuccess()) {
            boolean passwordUpdateStatus = user.updatePassword(password);

            if (passwordUpdateStatus) {
                redirectAttributes.addAttribute("Message", "PASSWORD CHANGED SUCCESSFULLY.");
                return new ModelAndView("redirect:/login");
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("Message", "LINK EXPIRED OR INVALID.");
        modelAndView.setViewName("message");
        return modelAndView;
    }


}
