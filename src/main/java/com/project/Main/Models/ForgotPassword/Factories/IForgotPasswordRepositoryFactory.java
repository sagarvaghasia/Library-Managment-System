package com.project.Main.Models.ForgotPassword.Factories;

import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;
import com.project.Main.Database.IDBConnection;

public interface IForgotPasswordRepositoryFactory {

    public IForgotPasswordRepository createForgotPasswordRepository(IDBConnection dbConnection);

}
