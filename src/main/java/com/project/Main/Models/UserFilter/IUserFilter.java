package com.project.Main.Models.UserFilter;

import com.project.Main.Models.User.IUser;
import com.project.Main.Models.CommonState.FilterState.FilterState;

import java.util.List;
import java.util.Map;

public interface IUserFilter {
    Map.Entry<List<IUser>, FilterState> getUsers(IUser.USER_ROLE role);
    Map.Entry<List<IUser>, FilterState> getUsers(String name, IUser.USER_ROLE role);
    Map.Entry<List<IUser>, FilterState> getIssuedUserListForBook(int bookId);
    Map.Entry<Integer, FilterState> getUserCount(IUser.USER_ROLE role);
}
