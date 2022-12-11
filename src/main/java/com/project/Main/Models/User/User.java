package com.project.Main.Models.User;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.User.Factories.IUserFactory;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.User.State.ValidationState.*;
import com.project.Main.Models.CommonState.DeleteState.DeleteLogicFailureState;
import com.project.Main.Models.CommonState.DeleteState.DeleteSQLFailureState;
import com.project.Main.Models.CommonState.DeleteState.DeleteState;
import com.project.Main.Models.CommonState.DeleteState.DeleteSuccessState;
import com.project.Main.Models.CommonState.LoadState.LoadNotAvailableState;
import com.project.Main.Models.CommonState.LoadState.LoadSQLFailureState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import com.project.Main.Models.CommonState.LoadState.LoadSuccessState;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

public class User extends IUser {

    private static PasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    public User(IUserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public User() {
    }

    public User(String name, String emailId, long contactNumber, String password, USER_ROLE role, IUserRepository userRepository) {
        this.name = name;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
        this.password = password;
        this.role = role;
        this.userRepository = userRepository;
    }

    public User(int id, String name, String emailId, long contactNumber, String password, USER_ROLE role, IUserRepository userRepository) {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
        this.password = password;
        this.role = role;
        this.userRepository = userRepository;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public LoadState loadUserById(int id) {

        Map.Entry<IUser, DatabaseState> loadResponse = this.userRepository.loadUserById(id);
        DatabaseState databaseState = loadResponse.getValue();

        if (databaseState.isSQLException()) {
            return new LoadSQLFailureState();
        }

        if (loadResponse.getKey() == null) {
            return new LoadNotAvailableState();
        } else {
            IUser loadedUser = loadResponse.getKey();
            this.loadFromOtherUser(loadedUser);

            return new LoadSuccessState();
        }

    }

    @Override
    public LoadState loadUserByEmailId(String emailId) {

        Map.Entry<IUser, DatabaseState> loadResponse = this.userRepository.loadUserByEmailId(emailId);
        DatabaseState databaseState = loadResponse.getValue();

        if (databaseState.isSQLException()) {
            return new LoadSQLFailureState();
        }

        if (loadResponse.getKey() == null) {
            return new LoadNotAvailableState();
        }

        IUser loadedUser = loadResponse.getKey();
        this.loadFromOtherUser(loadedUser);

        return new LoadSuccessState();
    }

    @Override
    public LoadState loadUserByContactNumber(long contactNumber) {

        Map.Entry<IUser, DatabaseState> response = this.userRepository.loadUserByContactNumber(contactNumber);

        if (response.getValue().isSQLException()) {
            return new LoadSQLFailureState();
        }

        if (response.getKey() == null) {
            return new LoadNotAvailableState();
        }

        IUser loadedUser = response.getKey();
        this.loadFromOtherUser(loadedUser);

        return new LoadSuccessState();
    }

    @Override
    public UserValidationState saveUser() {

        long contactNumber = this.getContactNumber();
        String emailId = this.getEmailId();

        IUserFactory userFactory = UserFactory.instance();
        IUser userAvailabilityEmail = userFactory.createUser(this.userRepository);

        LoadState loadState = userAvailabilityEmail.loadUserByEmailId(emailId);

        if (loadState.isSQLException()) {
            return new SQLFailureState();
        }

        if (loadState.isSuccess()) {
            return new EmailAlreadyExistState();
        }

        IUser userAvailabilityContact = userFactory.createUser(this.userRepository);
        loadState = userAvailabilityContact.loadUserByContactNumber(contactNumber);

        if (loadState.isSQLException()) {
            return new SQLFailureState();
        }

        if (loadState.isSuccess()) {
            return new ContactNumberAlreadyExistState();
        }

        String encodedPassword = bCryptEncoder.encode(this.getPassword());
        this.setPassword(encodedPassword);
        DatabaseState response = userRepository.saveUser(this);
        if (response.isSuccess()) {
            return new SuccessState();
        } else {
            return new SQLFailureState();
        }

    }

    @Override
    public boolean updatePassword(String password) {

        String encodedPassword = bCryptEncoder.encode(password);
        this.setPassword(encodedPassword);

        return this.userRepository.updatePassword(this);
    }

    @Override
    public UserValidationState updateUser() {

        DatabaseState response = this.userRepository.updateUser(this);

        if (response.isSuccess()) {
            return new SuccessState();
        }

        return new SQLFailureState();

    }

    @Override
    public DeleteState deleteUser() {

        DatabaseState response = this.userRepository.deleteUser(this);

        if (response.isSuccess()) {
            return new DeleteSuccessState();
        }

        if (response.isSQLException()) {
            return new DeleteSQLFailureState();
        }

        return new DeleteLogicFailureState();
    }

    public void loadFromOtherUser(IUser user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmailId(user.getEmailId());
        this.setContactNumber(user.getContactNumber());
        this.setPassword(user.getPassword());
        this.setRole(user.getRole());
    }

}
