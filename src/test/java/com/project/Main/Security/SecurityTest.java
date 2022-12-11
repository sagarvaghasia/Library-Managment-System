package com.project.Main.Security;

import com.project.Main.MockFactories.MockUserRepositoryFactory;
import com.project.Main.Models.Security.Factories.SecurityFactory;
import com.project.Main.Models.Security.ISecurity;
import com.project.Main.Models.User.Factories.IUserRepositoryFactory;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.State.LoginState.LoginState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SecurityTest {

    private static IUserRepositoryFactory userRepositoryFactory = MockUserRepositoryFactory.instance();
    private static IUserRepository userRepository = userRepositoryFactory.createUserRepository(null);

    @Test
    public void loginSuccessTest() {
        IUser user = UserFactory.
                instance().
                createUser(userRepository);

        user.setEmailId("khushalgondaliya@gmail.com");
        user.setPassword("khush");

        ISecurity security = SecurityFactory
                .instance()
                .createSecurity();

        LoginState response = security.login(user);
        assertEquals(true, response.isSuccess());
    }

    @Test
    public void loginInvalidTest() {
        IUser user = UserFactory.
                instance().
                createUser(userRepository);

        user.setEmailId("k@gmail.com");
        user.setPassword("Khush123");

        ISecurity security = SecurityFactory.
                instance()
                .createSecurity();

        LoginState response = security.login(user);
        assertEquals(false, response.isSuccess());
    }
}
