package com.project.Main.User;

import com.project.Main.MockFactories.MockUserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.UserFilter.Factories.UserFilterFactory;
import com.project.Main.Models.UserFilter.IUserFilter;
import com.project.Main.Models.CommonState.FilterState.FilterNotAvailableState;
import com.project.Main.Models.CommonState.FilterState.FilterSQLFailureState;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import com.project.Main.Models.CommonState.FilterState.FilterSuccessState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserFilterTest {

    private static IUserRepository userRepository = MockUserRepositoryFactory
            .instance()
            .createUserRepository(null);

    private static IUserFilter userFilter = UserFilterFactory
            .instance().createUserFilter(userRepository);


    @Test
    public void getUsersValidTest() {
        Map.Entry<List<IUser>, FilterState> response = userFilter.getUsers(IUser.USER_ROLE.ROLE_USER);
        assertEquals(FilterSuccessState.class, response.getValue().getClass());
    }

    @Test
    public void getUsersInvalidTest() {
        Map.Entry<List<IUser>, FilterState> response = userFilter.getUsers(null);
        assertEquals(FilterNotAvailableState.class, response.getValue().getClass());
    }


    @Test
    public void getUserCountValidTest() {
        Map.Entry<Integer, FilterState> response = userFilter.getUserCount(IUser.USER_ROLE.ROLE_USER);
        assertEquals(FilterSuccessState.class, response.getValue().getClass());
    }

    @Test
    public void getUserCountInvalidTest() {
        Map.Entry<Integer, FilterState> response = userFilter.getUserCount(null);
        assertEquals(FilterSQLFailureState.class, response.getValue().getClass());
    }

}
