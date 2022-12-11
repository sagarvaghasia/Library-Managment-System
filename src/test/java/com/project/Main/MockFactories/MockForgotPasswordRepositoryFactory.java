package com.project.Main.MockFactories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.ForgotPassword.Factories.IForgotPasswordRepositoryFactory;
import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;
import com.project.Main.MockRepositories.MockForgotPasswordRepository;

public class MockForgotPasswordRepositoryFactory implements IForgotPasswordRepositoryFactory {


    private static IForgotPasswordRepositoryFactory mockForgotPasswordFactory = null;

    private MockForgotPasswordRepositoryFactory() {
    }

    public static IForgotPasswordRepositoryFactory instance() {
        if (mockForgotPasswordFactory == null) {
            return new MockForgotPasswordRepositoryFactory();
        }
        return mockForgotPasswordFactory;
    }


    @Override
    public IForgotPasswordRepository createForgotPasswordRepository(IDBConnection dbConnection) {
        return new MockForgotPasswordRepository();
    }
}
