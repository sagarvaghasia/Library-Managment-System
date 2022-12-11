package com.project.Main.Models.Security;

import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.State.LoginState.LoginState;

import javax.servlet.http.HttpSession;

public interface ISecurity {

    LoginState login(IUser user);

    public void logout(HttpSession session);
}
