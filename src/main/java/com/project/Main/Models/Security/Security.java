package com.project.Main.Models.Security;

import com.project.Main.Models.User.IUser;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import com.project.Main.Models.User.State.LoginState.LoginInvalidCredentialState;
import com.project.Main.Models.User.State.LoginState.LoginSQLExceptionState;
import com.project.Main.Models.User.State.LoginState.LoginState;
import com.project.Main.Models.User.State.LoginState.LoginSuccessState;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class Security implements ISecurity {

    private static PasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    public static boolean sessionValidation(HttpSession session) {

        Enumeration<String> names = session.getAttributeNames();

        if (names.hasMoreElements()) {
            return true;
        }
        return false;
    }

    @Override
    public LoginState login(IUser user) {

        String password = user.getPassword();
        LoadState loadStatus = user.loadUserByEmailId(user.getEmailId());

        if (loadStatus.isSQLException()) {
            return new LoginSQLExceptionState();
        }

        if (loadStatus.isSuccess()) {

            boolean isPasswordCorrect = bCryptEncoder.matches(password, user.getPassword());

            if (isPasswordCorrect) {
                return new LoginSuccessState();
            }

        }

        return new LoginInvalidCredentialState();
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
