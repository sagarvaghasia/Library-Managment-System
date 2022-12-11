package com.project.Main.Models.UserFilter.Factories;

import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.UserFilter.IUserFilter;

public interface IUserFilterFactory {
    IUserFilter createUserFilter(IUserRepository userRepository);
}
