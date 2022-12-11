package com.project.Main.Models.ForgotPassword;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.ForgotPassword.Repositories.IForgotPasswordRepository;

public class ForgotPassword extends IForgotPassword {


    public ForgotPassword(IForgotPasswordRepository forgotPasswordRepository) {
        super(forgotPasswordRepository);
    }

    public ForgotPassword(int userId, long timestamp, IForgotPasswordRepository forgotPasswordRepository) {
        super(userId, timestamp, forgotPasswordRepository);
    }

    public ForgotPassword(int id, int userId, long timestamp,
                          IForgotPasswordRepository forgotPasswordRepository) {
        super(id, userId, timestamp, forgotPasswordRepository);
    }

    @Override
    public ADD_FORGOT_STATUS addForgotRequest() {

        DatabaseState databaseState = this.forgotPasswordRepository.addForgotRequest(this);

        if (databaseState.isSuccess()) {
            return ADD_FORGOT_STATUS.SUCCESS;
        }

        return ADD_FORGOT_STATUS.ERROR;
    }

    @Override
    public boolean validateToken() {
        return this.forgotPasswordRepository.validateToken(this.timestamp, this.userId);
    }
}
