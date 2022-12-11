package com.project.Main.Models.User;

import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.State.ValidationState.UserValidationState;
import com.project.Main.Models.CommonState.DeleteState.DeleteState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import lombok.Getter;
import lombok.Setter;

public abstract class IUser {

    @Getter
    @Setter
    protected int id;
    @Getter
    @Setter
    protected String name;
    @Getter
    @Setter
    protected String emailId;
    @Getter
    @Setter
    protected long contactNumber;
    @Getter
    @Setter
    protected String password;
    @Getter
    @Setter
    protected USER_ROLE role;
    @Getter
    @Setter
    protected IUserRepository userRepository;

    public boolean isAdmin() {
        return this.role.equals(USER_ROLE.ROLE_ADMIN);
    }

    public boolean isLibrarian() {
        return this.role.equals(USER_ROLE.ROLE_LIBRARIAN);
    }

    public boolean isUser() {
        return this.role.equals(USER_ROLE.ROLE_USER);
    }

    public abstract void setPassword(String password);

    public abstract LoadState loadUserById(int id);

    public abstract LoadState loadUserByEmailId(String emailId);

    public abstract LoadState loadUserByContactNumber(long contactNumber);

    public abstract UserValidationState saveUser();

    public abstract UserValidationState updateUser();

    public abstract boolean updatePassword(String password);

    public abstract DeleteState deleteUser();

    public enum USER_ROLE {
        ROLE_ADMIN,
        ROLE_LIBRARIAN,
        ROLE_USER
    }

}
