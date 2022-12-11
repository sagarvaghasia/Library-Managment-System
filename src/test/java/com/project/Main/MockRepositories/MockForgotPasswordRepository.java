package com.project.Main.MockRepositories;

import com.project.Main.Models.ForgotPassword.IForgotPassword;
import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.SQLFailureState;
import com.project.Main.Database.State.SuccessState;

import java.sql.Date;
import java.sql.Timestamp;

public class MockForgotPasswordRepository implements IForgotPasswordRepository {

    public MockForgotPasswordRepository() {

    }

    @Override
    public DatabaseState addForgotRequest(IForgotPassword forgotPassword) {

        if (forgotPassword.getUserId() == 1) {
            forgotPassword.setId(1);

            return new SuccessState();
        }

        return new SQLFailureState();
    }

    @Override
    public boolean validateToken(long token, int userId) {

        long timestamp = new Timestamp((new Date(2022, 7, 15)).getTime()).getTime();

        return userId == 1 && timestamp == token;
    }

}
