package com.project.Main.MockFactories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.User.Factories.IUserRepositoryFactory;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.MockRepositories.MockUserRepository;

public class MockUserRepositoryFactory implements IUserRepositoryFactory {

    private static IUserRepositoryFactory userRepositoryFactory;

    private MockUserRepositoryFactory() {
    }

    public static IUserRepositoryFactory instance() {
        if (userRepositoryFactory == null) {
            userRepositoryFactory = new MockUserRepositoryFactory();
        }
        return userRepositoryFactory;
    }

    @Override
    public IUserRepository createUserRepository(IDBConnection dbConnection) {
        return new MockUserRepository();
    }
}
