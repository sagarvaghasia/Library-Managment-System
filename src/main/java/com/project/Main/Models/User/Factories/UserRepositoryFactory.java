package com.project.Main.Models.User.Factories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.Repositories.UserRepository;

public class UserRepositoryFactory implements IUserRepositoryFactory {

    private static IUserRepositoryFactory userRepositoryFactory = null;

    private UserRepositoryFactory() {
    }

    public static IUserRepositoryFactory instance() {
        if (userRepositoryFactory == null) {
            return new UserRepositoryFactory();
        }
        return userRepositoryFactory;
    }

    @Override
    public IUserRepository createUserRepository(IDBConnection dbConnection) {
        return new UserRepository(dbConnection);
    }
}
