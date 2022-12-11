package com.project.Main.User;

import com.project.Main.MockFactories.MockUserRepositoryFactory;
import com.project.Main.Models.CommonState.DeleteState.DeleteLogicFailureState;
import com.project.Main.Models.CommonState.DeleteState.DeleteSQLFailureState;
import com.project.Main.Models.CommonState.DeleteState.DeleteState;
import com.project.Main.Models.CommonState.DeleteState.DeleteSuccessState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import com.project.Main.Models.User.Factories.IUserFactory;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.State.ValidationState.ContactNumberAlreadyExistState;
import com.project.Main.Models.User.State.ValidationState.EmailAlreadyExistState;
import com.project.Main.Models.User.State.ValidationState.UserValidationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserTest {

    private static final int TEST_ID = 1;
    private static final String TEST_NAME = "test";
    private static final String TEST_EMAIL_ID = "test@gmail.com";
    private static final long TEST_CONTACT_NUMBER = 1239874564;
    private static final String TEST_PASSWORD = "Test1234";
    private static final IUser.USER_ROLE TEST_ROLE = IUser.USER_ROLE.ROLE_USER;
    private static IUserFactory userFactory = UserFactory.instance();
    private IUser userTest;

    @BeforeEach
    public void setUp() {
        this.userTest = userFactory.createUser(TEST_ID,
                TEST_NAME,
                TEST_EMAIL_ID,
                TEST_CONTACT_NUMBER,
                TEST_PASSWORD,
                TEST_ROLE,
                MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
        );
    }

    @Test
    public void saveUserEmailExistTest() {

        String userTestName = "khush";
        String userTestEmailId = "khushalgondaliya@gmail.com";
        long userTestContactNumber = 1111111111;
        String userTestpassword = "Khush1234";
        IUser.USER_ROLE userTestRole = IUser.USER_ROLE.ROLE_USER;

        IUser saveUserTest = userFactory
                .createUser(userTestName,
                        userTestEmailId,
                        userTestContactNumber,
                        userTestpassword,
                        userTestRole,
                        MockUserRepositoryFactory
                                .instance()
                                .createUserRepository(null)
                );

        UserValidationState userValidationState = saveUserTest.saveUser();
        assertEquals(EmailAlreadyExistState.class, userValidationState.getClass());
    }

    @Test
    public void saveUserContactNumberExistTest() {

        String userTestName = "sagar";
        String userTestEmailId = "sagar@gmail.com";
        long userTestContactNumber = 1234567890;
        String userTestpassword = "Sagar1234";
        IUser.USER_ROLE userTestRole = IUser.USER_ROLE.ROLE_USER;

        IUser saveUserTest = userFactory
                .createUser(userTestName,
                        userTestEmailId,
                        userTestContactNumber,
                        userTestpassword,
                        userTestRole,
                        MockUserRepositoryFactory
                                .instance()
                                .createUserRepository(null)
                );

        UserValidationState userValidationState = saveUserTest.saveUser();
        assertEquals(ContactNumberAlreadyExistState.class, userValidationState.getClass());
    }

    @Test
    public void saveUserCorrectTest() {
        UserValidationState userValidationState = this.userTest.saveUser();
        assertEquals(true, userValidationState.isSuccess());
    }

    @Test
    public void deleteUserWrongTest() {
        int id = -1;
        IUser deleteUserTest = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );
        deleteUserTest.setId(id);

        DeleteState deleteState = deleteUserTest.deleteUser();
        assertEquals(DeleteSQLFailureState.class, deleteState.getClass());
    }

    @Test
    public void deleteUserConstraintViolationTest() {
        int id = 2;
        IUser deleteUserTest = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );
        deleteUserTest.setId(id);

        DeleteState deleteState = deleteUserTest.deleteUser();
        assertEquals(DeleteLogicFailureState.class, deleteState.getClass());
    }

    @Test
    public void deleteUserCorrectTest() {
        DeleteState deleteState = this.userTest.deleteUser();
        assertEquals(DeleteSuccessState.class, deleteState.getClass());
    }

    @Test
    public void loadUserByEmailEmptyTest() {
        String email = "";
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserByEmailId(email);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByEmailNullTest() {
        String email = null;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserByEmailId(email);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByEmailWrongTest() {
        String email = "badEmailTest@gmail.com";
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserByEmailId(email);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByEmailCorrectTest() {
        String email = "khushalgondaliya@gmail.com";
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserByEmailId(email);
        assertEquals(true, loadState.isSuccess());
    }

    @Test
    public void loadUserByContactNumberNegativeTest() {
        long contactNumber = -123456789;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserByContactNumber(contactNumber);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByContactNumberZeroTest() {
        long contactNumber = 0;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserByContactNumber(contactNumber);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByContactNumberWrongTest() {
        long contactNumber = 1000000001;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserByContactNumber(contactNumber);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByContactNumberCorrectTest() {
        long contactNumber = 1234567890;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserByContactNumber(contactNumber);
        assertEquals(true, loadState.isSuccess());
    }

    @Test
    public void loadUserByIDNegativeTest() {
        int id = -1;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserById(id);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByIDZeroTest() {
        int id = 0;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserById(id);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByIDWrongTest() {
        int id = 99999;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserById(id);
        assertEquals(false, loadState.isSuccess());
    }

    @Test
    public void loadUserByIDCorrectTest() {
        int id = 1;
        IUser user = userFactory
                .createUser(MockUserRepositoryFactory
                        .instance()
                        .createUserRepository(null)
                );

        LoadState loadState = user.loadUserById(id);
        assertEquals(true, loadState.isSuccess());
    }

}
