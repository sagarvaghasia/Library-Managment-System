package com.project.Main.Models.UserFilter;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.CommonState.FilterState.FilterNotAvailableState;
import com.project.Main.Models.CommonState.FilterState.FilterSQLFailureState;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import com.project.Main.Models.CommonState.FilterState.FilterSuccessState;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class UserFilter implements IUserFilter {

    private IUserRepository userRepository;

    public UserFilter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map.Entry<List<IUser>, FilterState> getUsers(IUser.USER_ROLE role) {

        Map.Entry<List<IUser>, DatabaseState> response = userRepository.getUsers(role);
        DatabaseState databaseState = response.getValue();

        if (databaseState.isSuccess()) {
            List<IUser> users = response.getKey();

            if (users == null) {
                return new AbstractMap.SimpleEntry<>(null, new FilterNotAvailableState());
            }

            return new AbstractMap.SimpleEntry<List<IUser>, FilterState>(users, new FilterSuccessState());
        }

        return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
    }

    @Override
    public Map.Entry<List<IUser>, FilterState> getUsers(String name, IUser.USER_ROLE role) {

        Map.Entry<List<IUser>, DatabaseState> response = userRepository.getUsers(name, role);

        if (response.getValue().isSuccess()) {
            List<IUser> users = response.getKey();

            if (users == null) {
                return new AbstractMap.SimpleEntry<>(null, new FilterNotAvailableState());
            }

            return new AbstractMap.SimpleEntry<List<IUser>, FilterState>(users, new FilterSuccessState());
        }

        return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
    }

    @Override
    public Map.Entry<List<IUser>, FilterState> getIssuedUserListForBook(int bookId) {

        Map.Entry<List<IUser>, DatabaseState> statusEntry = userRepository.getIssuedUserListForBook(bookId);
        DatabaseState databaseState = statusEntry.getValue();

        if (databaseState.isSuccess()) {
            return new AbstractMap.SimpleEntry<>(statusEntry.getKey(), new FilterSuccessState());
        }

        return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
    }

    @Override
    public Map.Entry<Integer, FilterState> getUserCount(IUser.USER_ROLE role) {

        Map.Entry<Integer, DatabaseState> userCountResponse = userRepository.getUserCount(role);
        DatabaseState databaseState = userCountResponse.getValue();

        if (databaseState.isSuccess()) {
            return new AbstractMap.SimpleEntry<Integer, FilterState>(userCountResponse.getKey(), new FilterSuccessState());
        }

        return new AbstractMap.SimpleEntry<>(null, new FilterSQLFailureState());
    }
}
