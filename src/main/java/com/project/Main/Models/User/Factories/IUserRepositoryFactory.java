package com.project.Main.Models.User.Factories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Models.User.Repositories.IUserRepository;

public interface IUserRepositoryFactory {

    public IUserRepository createUserRepository(IDBConnection dbConnection);
}
