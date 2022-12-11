package com.project.Main.Models.User.Factories;

import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.User;

public class UserFactory implements IUserFactory {

    public static IUserRepositoryFactory userRepositoryFactory = null;
    private static IUserFactory userFactory = null;

    private UserFactory() {
        userRepositoryFactory = UserRepositoryFactory.instance();
    }

    public static IUserFactory instance() {
        if (userFactory == null) {
            return new UserFactory();
        }
        return userFactory;
    }


    @Override
    public IUser createUser(String name, String emailId, long contactNumber, String password, IUser.USER_ROLE role, IUserRepository userRepository) {

        IUser user = new User(name, emailId, contactNumber, password, role, userRepository);
        return user;
    }

    @Override
    public IUser createUser(IUserRepository userRepository) {

        IUser user = new User(userRepository);
        return user;
    }

    @Override
    public IUser createUser(int id, String name, String emailId, long contactNumber, String password, IUser.USER_ROLE role, IUserRepository userRepository) {

        IUser user = new User(id, name, emailId, contactNumber, password, role, userRepository);
        return user;
    }
}
