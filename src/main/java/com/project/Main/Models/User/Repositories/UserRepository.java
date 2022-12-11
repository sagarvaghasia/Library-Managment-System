package com.project.Main.Models.User.Repositories;

import com.project.Main.Database.IDBConnection;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.LogicFailureState;
import com.project.Main.Database.State.SQLFailureState;
import com.project.Main.Database.State.SuccessState;
import com.project.Main.Models.User.Factories.IUserFactory;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.IUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserRepository implements IUserRepository {

    private static IUserFactory userFactory = UserFactory.instance();
    public final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final IDBConnection dbConn;

    public UserRepository(IDBConnection dbConnection) {
        this.dbConn = dbConnection;
    }

    public static List<IUser> loadFromResultSet(ResultSet resultSet, IUserRepository userRepository) throws SQLException {
        List<IUser> users = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String emailId = resultSet.getString("email");
            long contactNumber = resultSet.getLong("contact_number");
            String password = resultSet.getString("password");
            IUser.USER_ROLE role = IUser.USER_ROLE.valueOf(resultSet.getString("role"));
            IUser user = userFactory.createUser(id, name, emailId, contactNumber, password, role, userRepository);
            users.add(user);
        }

        return users;
    }

    @Override
    public Map.Entry<List<IUser>, DatabaseState> getUsers(IUser.USER_ROLE role) {

        try {
            Connection conn = dbConn.getConnection();
            String query = "select * from user where role = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, role.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<IUser> users = loadFromResultSet(resultSet, this);

            return new AbstractMap.SimpleEntry<>(users, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<List<IUser>, DatabaseState> getIssuedUserListForBook(int bookId) {

        try {
            Connection conn = dbConn.getConnection();
            String query = "select *from user where id in (select user_id from issued_books where book_id = ? and return_status = 1 ORDER BY issued_date ASC); ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<IUser> bookIssueReturnList = loadFromResultSet(resultSet, this);

            return new AbstractMap.SimpleEntry<>(bookIssueReturnList, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<List<IUser>, DatabaseState> getUsers(String nameForPattern, IUser.USER_ROLE role) {

        try {
            Connection conn = dbConn.getConnection();
            String query = "select * from user where name like ? and role = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + nameForPattern + "%");
            preparedStatement.setString(2, role.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<IUser> users = loadFromResultSet(resultSet, this);

            return new AbstractMap.SimpleEntry<>(users, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<IUser, DatabaseState> loadUserById(int id) {

        IUser user = null;

        try {
            Connection conn = dbConn.getConnection();
            String query = "select * from user where id = ?";
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                user = userFactory.createUser(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        resultSet.getString(5),
                        IUser.USER_ROLE.valueOf(resultSet.getString(6)),
                        this);
                break;
            }

            return new AbstractMap.SimpleEntry<>(user, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(user, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<IUser, DatabaseState> loadUserByEmailId(String emailId) {

        IUser user = null;

        try {
            Connection conn = dbConn.getConnection();
            String query = "select * from user where email = ?";
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            prepareStatement.setString(1, emailId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                user = userFactory.createUser(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        resultSet.getString(5),
                        IUser.USER_ROLE.valueOf(resultSet.getString(6)),
                        this);
                break;
            }

            return new AbstractMap.SimpleEntry<>(user, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(user, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<IUser, DatabaseState> loadUserByContactNumber(long contactNumber) {

        IUser user = null;

        try {
            Connection conn = dbConn.getConnection();
            String query = "select * from user where contact_number = ?";
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            prepareStatement.setLong(1, contactNumber);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                user = userFactory.createUser(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        resultSet.getString(5),
                        IUser.USER_ROLE.valueOf(resultSet.getString(6)),
                        this);
                break;
            }

            return new AbstractMap.SimpleEntry<>(user, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(user, new SQLFailureState());
        }

    }

    @Override
    public DatabaseState saveUser(IUser user) {

        String userName = user.getName();
        String userEmail = user.getEmailId();
        long userContactNumber = user.getContactNumber();

        try {
            Connection conn = dbConn.getConnection();
            String query = "insert into user(name,email,contact_number,password,role) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userEmail);
            preparedStatement.setLong(3, userContactNumber);
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().name());

            int numberOfRowsAffected = preparedStatement.executeUpdate();
            int autoGeneratedId = -1;

            if (numberOfRowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                autoGeneratedId = resultSet.getInt(1);
            }

            user.setId(autoGeneratedId);

            return new SuccessState();
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new SQLFailureState();
        }

    }

    @Override
    public DatabaseState updateUser(IUser user) {

        try {
            Connection conn = dbConn.getConnection();
            String query = "update user set name = ?, email = ?, contact_number = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmailId());
            preparedStatement.setLong(3, user.getContactNumber());
            preparedStatement.setInt(4, user.getId());

            int numberOfRowsAffected = preparedStatement.executeUpdate();

            if (numberOfRowsAffected == 1) {
                return new SuccessState();
            }

            return new LogicFailureState();
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new SQLFailureState();
        }

    }

    @Override
    public boolean updatePassword(IUser user) {

        try {
            Connection connection = this.dbConn.getConnection();
            String query = "update user set password = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());

            int updatedRecord = preparedStatement.executeUpdate();

            if (updatedRecord == 1) {
                return true;
            }

            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public DatabaseState deleteUser(IUser user) {

        int userId = user.getId();

        try {
            Connection conn = dbConn.getConnection();
            String query = "delete from user where id = ?";
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            prepareStatement.setInt(1, userId);

            int numberOfRowsAffected = prepareStatement.executeUpdate();

            if (numberOfRowsAffected == 1) {
                return new SuccessState();
            }

            return new LogicFailureState();
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.error("Constraint violation has occurred" + e.toString());

            return new LogicFailureState();
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new SQLFailureState();
        }

    }

    @Override
    public Map.Entry<Integer, DatabaseState> getUserCount(IUser.USER_ROLE role) {

        try {
            Connection conn = dbConn.getConnection();
            String query = "select count(id) as count from user where role = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, role.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return new AbstractMap.SimpleEntry<>(resultSet.getInt("count"), new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<>(null, new SQLFailureState());
        }
    }

}
