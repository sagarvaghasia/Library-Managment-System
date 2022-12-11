package com.project.Main.Models.ForgotPassword.Repositories;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.ForgotPassword.IForgotPassword;

public interface IForgotPasswordRepository {

    public DatabaseState addForgotRequest(IForgotPassword forgotPassword);

    public boolean validateToken(long token, int userId);

}
