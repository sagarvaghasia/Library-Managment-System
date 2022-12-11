package com.project.Main.Models.User.Repositories;

import java.util.List;
import java.util.Map;

import com.project.Main.Models.User.IUser;
import com.project.Main.Database.State.DatabaseState;

public interface IUserRepository {

	Map.Entry<List<IUser>, DatabaseState> getUsers(IUser.USER_ROLE role);

    Map.Entry<List<IUser>, DatabaseState> getIssuedUserListForBook(int bookId);

    Map.Entry<List<IUser>, DatabaseState> getUsers(String nameForPattern, IUser.USER_ROLE role);

	public Map.Entry<IUser, DatabaseState> loadUserById(int id);

	public Map.Entry<IUser, DatabaseState> loadUserByEmailId(String emailId);

	public Map.Entry<IUser, DatabaseState> loadUserByContactNumber(long contactNumber);

	public DatabaseState saveUser(IUser user);

	public DatabaseState updateUser(IUser user);

	public boolean updatePassword(IUser user);

	public DatabaseState deleteUser(IUser user);

	public Map.Entry<Integer, DatabaseState> getUserCount(IUser.USER_ROLE role);

}
