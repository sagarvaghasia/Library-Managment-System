package com.project.Main.Models.User.Factories;

import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;

public interface IUserFactory {
    public IUser createUser(String name, String emailId, long contactNumber, String password, IUser.USER_ROLE role, IUserRepository userRepository);

    public IUser createUser(IUserRepository userRepository);

    public IUser createUser(int id, String name, String emailId, long contactNumber, String password, IUser.USER_ROLE role, IUserRepository userRepository);
}
