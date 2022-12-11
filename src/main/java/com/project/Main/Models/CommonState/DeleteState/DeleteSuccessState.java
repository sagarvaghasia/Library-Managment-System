package com.project.Main.Models.CommonState.DeleteState;

public class DeleteSuccessState extends DeleteState {

    public DeleteSuccessState(){
        this.message = "DELETED SUCCESSFULLY.";
    }
    public boolean isSuccess() {
        return true;
    }

}
