package com.project.Main.MockRepositories;

import com.project.Main.Database.DBConnection;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.LogicFailureState;
import com.project.Main.Database.State.SQLFailureState;
import com.project.Main.Database.State.SuccessState;
import com.project.Main.Models.User.Factories.IUserFactory;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.Factories.UserRepositoryFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockUserRepository implements IUserRepository {

    private static IUserFactory userFactory = UserFactory.instance();

    private static List<IUser> users = new ArrayList<>();
    ;

    public MockUserRepository() {
        IUserRepository userRepository = UserRepositoryFactory
                .instance()
                .createUserRepository(
                        DBConnection.instance()
                );

        IUser user1 = userFactory.createUser(1, "abc", "abcd@gmail.com", 1234567898, "abcd", IUser.USER_ROLE.ROLE_USER, userRepository);
        users.add(user1);

        IUser user2 = userFactory.createUser(2, "abc", "abcd@gmail.com", 1234567898, "abcd", IUser.USER_ROLE.ROLE_LIBRARIAN, userRepository);
        users.add(user2);

        IUser user3 = userFactory.createUser(3, "abc", "abcd@gmail.com", 1234567898, "abcd", IUser.USER_ROLE.ROLE_ADMIN, userRepository);
        users.add(user3);
    }

    @Override
    public Map.Entry<List<IUser>, DatabaseState> getUsers(IUser.USER_ROLE role) {

        List<IUser> us = new ArrayList<>();

        if (role == null) {
            return new AbstractMap.SimpleEntry<>(null, new SuccessState());
        }

        for (IUser user : users) {
            if (user.getRole().equals(role.name())) {
                us.add(user);
            }
        }

        return new AbstractMap.SimpleEntry<>(us, new SuccessState());
    }

    @Override
    public Map.Entry<List<IUser>, DatabaseState> getIssuedUserListForBook(int bookId) {
        return null;
    }

    @Override
    public Map.Entry<List<IUser>, DatabaseState> getUsers(String nameForPattern, IUser.USER_ROLE role) {

        if (role == null ||
                nameForPattern == null ||
                nameForPattern == "") {
            return new AbstractMap.SimpleEntry<>(null, new SuccessState());
        }

        List<IUser> us = new ArrayList<>();

        for (IUser user : users) {
            if (user.getRole().equals(role.name()) &&
                    (user.getEmailId().contains(nameForPattern) ||
                            user.getName().contains(nameForPattern))) {
                us.add(user);
            }
        }

        return new AbstractMap.SimpleEntry<>(users, new SuccessState());
    }

    @Override
    public Map.Entry<IUser, DatabaseState> loadUserById(int id) {

        if (id == 1) {
            IUser user = userFactory.createUser(1,
                    "khush",
                    "khushalgondaliya@gmail.com",
                    1234567890,
                    "$2a$12$xNO93RhXXWmriYDHTA8B0ey8iW8Z9V5BhfcWr7OkWTvBqNxjxXiY.",
                    IUser.USER_ROLE.ROLE_ADMIN,
                    this);

            return new AbstractMap.SimpleEntry<IUser, DatabaseState>(user, new SuccessState());
        } else {
            return new AbstractMap.SimpleEntry<IUser, DatabaseState>(null, new SuccessState());
        }

    }

    @Override
    public Map.Entry<IUser, DatabaseState> loadUserByEmailId(String emailId) {

        if (emailId == null) {
            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }
        if (emailId.equals("khushalgondaliya@gmail.com")) {
            IUser user = userFactory.createUser(1,
                    "khush",
                    "khushalgondaliya@gmail.com",
                    1234567890,
                    "$2a$12$xNO93RhXXWmriYDHTA8B0ey8iW8Z9V5BhfcWr7OkWTvBqNxjxXiY.",
                    IUser.USER_ROLE.ROLE_ADMIN,
                    this);

            return new AbstractMap.SimpleEntry<IUser, DatabaseState>(user, new SuccessState());
        } else {
            return new AbstractMap.SimpleEntry<IUser, DatabaseState>(null, new SuccessState());
        }

    }

    @Override
    public Map.Entry<IUser, DatabaseState> loadUserByContactNumber(long contactNumber) {

        if (contactNumber == 1234567890) {
            IUser user = userFactory.createUser(1,
                    "khush",
                    "khushalgondaliya@gmail.com",
                    1234567890,
                    "$2a$12$xNO93RhXXWmriYDHTA8B0ey8iW8Z9V5BhfcWr7OkWTvBqNxjxXiY.",
                    IUser.USER_ROLE.ROLE_ADMIN,
                    this);

            return new AbstractMap.SimpleEntry<IUser, DatabaseState>(user, new SuccessState());
        } else {
            return new AbstractMap.SimpleEntry<IUser, DatabaseState>(null, new SuccessState());
        }

    }

    @Override
    public DatabaseState saveUser(IUser user) {
        return new SuccessState();
    }

    @Override
    public DatabaseState updateUser(IUser user) {
        return new SuccessState();
    }

    @Override
    public boolean updatePassword(IUser user) {

        if (user.getId() == 1) {
            return true;
        }

        return false;
    }

    @Override
    public DatabaseState deleteUser(IUser user) {

        if (user.getId() == 1) {
            return new SuccessState();
        } else if (user.getId() == 2) {
            return new LogicFailureState();
        } else {
            return new SQLFailureState();
        }

    }

    @Override
    public Map.Entry<Integer, DatabaseState> getUserCount(IUser.USER_ROLE role) {

        if (role == null) {
            return new AbstractMap.SimpleEntry<>(0, new SQLFailureState());
        }

        return new AbstractMap.SimpleEntry<>(5, new SuccessState());
    }

}
