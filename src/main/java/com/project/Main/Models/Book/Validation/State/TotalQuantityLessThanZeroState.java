package com.project.Main.Models.Book.Validation.State;

public class TotalQuantityLessThanZeroState extends BookValidationState {

    public TotalQuantityLessThanZeroState() {
        this.message = "TOTAL QUANTITY CAN NOT BE LESS THAN ZERO.";
    }

}
