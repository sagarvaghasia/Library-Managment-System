package com.project.Main.Models.ForgotPassword.Factories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.ForgotPassword.Repositories.ForgotPasswordRepository;
import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;

public class ForgotPasswordRepositoryFactory implements IForgotPasswordRepositoryFactory {

    private static IForgotPasswordRepositoryFactory forgotPasswordFactory = null;

    private ForgotPasswordRepositoryFactory() {
    }

    public static IForgotPasswordRepositoryFactory instance() {
        if (forgotPasswordFactory == null) {
            return new ForgotPasswordRepositoryFactory();
        }
        return forgotPasswordFactory;
    }

    @Override
    public IForgotPasswordRepository createForgotPasswordRepository(IDBConnection dbConnection) {
        return new ForgotPasswordRepository(dbConnection);
    }

}
