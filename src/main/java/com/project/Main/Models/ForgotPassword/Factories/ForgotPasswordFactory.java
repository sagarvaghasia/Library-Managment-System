package com.project.Main.Models.ForgotPassword.Factories;

import com.project.Main.Models.ForgotPassword.ForgotPassword;
import com.project.Main.Models.ForgotPassword.IForgotPassword;
import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;

import java.sql.Timestamp;

public class ForgotPasswordFactory implements IForgotPasswordFactory {

    private static IForgotPasswordFactory forgotPasswordFactory = null;

    private ForgotPasswordFactory() {
    }

    public static IForgotPasswordFactory instance() {
        if(forgotPasswordFactory == null){
            return new ForgotPasswordFactory();
        }
        return forgotPasswordFactory;
    }

    @Override
    public IForgotPassword createForgotPassword(IForgotPasswordRepository forgotPasswordRepository) {
        return new ForgotPassword(forgotPasswordRepository);
    }

    @Override
    public IForgotPassword createForgotPassword(int userId, IForgotPasswordRepository forgotPasswordRepository) {
        long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        return new ForgotPassword(userId, timestamp, forgotPasswordRepository);
    }

    @Override
    public IForgotPassword createForgotPassword(int userId, long timestamp, IForgotPasswordRepository forgotPasswordRepository) {
        return new ForgotPassword(userId, timestamp, forgotPasswordRepository);
    }

    @Override
    public IForgotPassword createForgotPassword(int id, int userId, long timestamp, IForgotPasswordRepository forgotPasswordRepository) {
        return new ForgotPassword(id, userId, timestamp, forgotPasswordRepository);
    }

}
