package com.project.Main.ForgotPassword;

import com.project.Main.MockFactories.MockForgotPasswordRepositoryFactory;
import com.project.Main.Models.ForgotPassword.Factories.ForgotPasswordFactory;
import com.project.Main.Models.ForgotPassword.Factories.IForgotPasswordRepositoryFactory;
import com.project.Main.Models.ForgotPassword.IForgotPassword;
import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ForgotPasswordTest {

    private static IForgotPasswordRepositoryFactory forgotPasswordRepositoryFactory = MockForgotPasswordRepositoryFactory.instance();

    private static IForgotPasswordRepository forgotPasswordRepository = forgotPasswordRepositoryFactory
            .createForgotPasswordRepository(null);

    private static IForgotPassword forgotPassword = ForgotPasswordFactory
            .instance().createForgotPassword(forgotPasswordRepository);

    @BeforeEach
    public void setUp() {
        long timestamp = new Timestamp((new Date(2022, 7, 15)).getTime()).getTime();
        forgotPassword.setUserId(1);
        forgotPassword.setTimestamp(timestamp);
    }

    @Test
    public void addForgotRequestTest() {
        IForgotPassword.ADD_FORGOT_STATUS status = forgotPassword.addForgotRequest();
        assertEquals(IForgotPassword.ADD_FORGOT_STATUS.SUCCESS, status);
    }

    @Test
    public void validateTokenPositiveTest() {
        boolean isTokenValid = forgotPassword.validateToken();
        assertEquals(true, isTokenValid);
    }

    @Test
    public void validateTokenNegativeTest() {
        forgotPassword.setUserId(5);
        boolean isTokenValid = forgotPassword.validateToken();
        assertEquals(false, isTokenValid);
    }

}
