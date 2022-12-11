package com.project.Main.Models.ForgotPassword.Factories;

import com.project.Main.Models.ForgotPassword.IForgotPassword;
import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;

public interface IForgotPasswordFactory {

    public IForgotPassword createForgotPassword(IForgotPasswordRepository forgotPasswordRepository);

    public IForgotPassword createForgotPassword(int userId, IForgotPasswordRepository forgotPasswordRepository);

    public IForgotPassword createForgotPassword(int userId, long timestamp, IForgotPasswordRepository forgotPasswordRepository);

    public IForgotPassword createForgotPassword(int id, int userId, long timestamp, IForgotPasswordRepository forgotPasswordRepository);
}
