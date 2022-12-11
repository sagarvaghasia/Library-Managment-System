package com.project.Main.Models.Book.Validation.State;

public class AvailableQuantityLessThanZeroState extends BookValidationState {

    public AvailableQuantityLessThanZeroState() {
        this.message = "AVAILABLE QUANTITY CAN NOT BE LESS THAN ZERO.";
    }

}
