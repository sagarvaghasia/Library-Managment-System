package com.project.Main.Models.ForgotPassword;

import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;
import lombok.Getter;
import lombok.Setter;


public abstract class IForgotPassword {

    @Getter @Setter protected int id;

    @Getter @Setter protected int userId;

    @Getter @Setter protected long timestamp;

    @Getter @Setter protected IForgotPasswordRepository forgotPasswordRepository;

    public enum ADD_FORGOT_STATUS{
        SUCCESS,
        ERROR
    }

    public IForgotPassword(IForgotPasswordRepository forgotPasswordRepository){
        this.forgotPasswordRepository = forgotPasswordRepository;
    }



    public IForgotPassword(int userId, long timestamp,
                           IForgotPasswordRepository forgotPasswordRepository){
        this.userId = userId;
        this.timestamp = timestamp;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    public IForgotPassword(int id, int userId, long timestamp,
                           IForgotPasswordRepository forgotPasswordRepository){
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    public abstract ADD_FORGOT_STATUS addForgotRequest();

    public abstract boolean validateToken();

}
