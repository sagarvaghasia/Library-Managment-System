package com.project.Main.Models.UserFilter.Factories;

import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.UserFilter.IUserFilter;
import com.project.Main.Models.UserFilter.UserFilter;

public class UserFilterFactory implements IUserFilterFactory {

    private static IUserFilterFactory userFilterFactory = null;

    private UserFilterFactory() {
    }

    public static IUserFilterFactory instance() {
        if (userFilterFactory == null) {
            return new UserFilterFactory();
        }
        return userFilterFactory;
    }

    @Override
    public IUserFilter createUserFilter(IUserRepository userRepository) {

        return new UserFilter(userRepository);
    }

}
