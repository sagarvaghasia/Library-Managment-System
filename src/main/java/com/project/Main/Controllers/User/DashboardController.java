package com.project.Main.Controllers.User;

import com.project.Main.Database.DBConnection;
import com.project.Main.Models.Book.Factories.BookRepositoryFactory;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.BookFilter.Factories.BookFilterFactory;
import com.project.Main.Models.BookFilter.Factories.IBookFilterFactory;
import com.project.Main.Models.BookFilter.IBookFilter;
import com.project.Main.Models.BookTypeChart.Factories.ChartFactory;
import com.project.Main.Models.BookTypeChart.Factories.ChartRepositoryFactory;
import com.project.Main.Models.BookTypeChart.Factories.IChartRepositoryFactory;
import com.project.Main.Models.BookTypeChart.IBookTypeChart;
import com.project.Main.Models.RevenueChart.IRevenueChart;
import com.project.Main.Models.Security.Security;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.UserFilter.Factories.IUserFilterFactory;
import com.project.Main.Models.UserFilter.Factories.UserFilterFactory;
import com.project.Main.Models.UserFilter.IUserFilter;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class DashboardController {

    @RequestMapping(value = "/dashboard")
    public ModelAndView getDashBoard(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (Security.sessionValidation(session)) {

            ModelAndView modelAndView = new ModelAndView();

            IBookRepository bookRepository = BookRepositoryFactory
                    .instance()
                    .createBookRepository(
                            DBConnection.instance()
                    );

            IUserRepository userRepository = UserRepositoryFactory
                    .instance()
                    .createUserRepository(
                            DBConnection.instance()
                    );

            IBookFilterFactory bookFilterFactory = BookFilterFactory.instance();
            IBookFilter bookFilter = bookFilterFactory.createBookFilter(bookRepository);

            IUserFilterFactory userFilterFactory = UserFilterFactory.instance();
            IUserFilter userFilter = userFilterFactory.createUserFilter(userRepository);

            Map.Entry<Integer, FilterState> librarianCountResponse = userFilter.getUserCount(IUser.USER_ROLE.ROLE_LIBRARIAN);
            FilterState librarianFilterState = librarianCountResponse.getValue();

            if (librarianFilterState.isSuccess()) {
                modelAndView.addObject("librarianCount", librarianCountResponse.getKey());
            }

            Map.Entry<Integer, FilterState> userCountResponse = userFilter.getUserCount(IUser.USER_ROLE.ROLE_USER);
            FilterState userFilterState = userCountResponse.getValue();

            if (userFilterState.isSuccess()) {
                modelAndView.addObject("userCount", userCountResponse.getKey());
            }

            Map.Entry<Integer, FilterState> bookCountResponse = bookFilter.getBookCount();
            FilterState bookFilterState = bookCountResponse.getValue();

            if (bookFilterState.isSuccess()) {
                modelAndView.addObject("bookCount", bookCountResponse.getKey());
            }

            IChartRepositoryFactory chartRepositoryFactory = ChartRepositoryFactory.instance();
            IBookTypeChart bookTypeChart = ChartFactory.instance().createBookTypeChart(chartRepositoryFactory.createChartRepository());
            LoadState bookTypeChartStatus = bookTypeChart.getTypeOfBookIssuedData();

            if (bookTypeChartStatus.isSuccess()) {
                modelAndView.addObject("bookTypeChart", bookTypeChart);
            }

            IRevenueChart revenueChart = ChartFactory.instance().createRevenueChart(chartRepositoryFactory.createChartRepository());
            LoadState revenueChartStatus = revenueChart.getMonthlyRevenueData();

            if (revenueChartStatus.isSuccess()) {
                modelAndView.addObject("revenueChart", revenueChart);
            }

            modelAndView.addObject("user", session.getAttribute("user"));
            modelAndView.setViewName("dashboard");
            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

}
