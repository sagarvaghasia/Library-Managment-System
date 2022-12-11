package com.project.Main.Models.Book.Validation.State;

public class AvailableQuantityGreaterThanTotalQuantityState extends BookValidationState {

    public AvailableQuantityGreaterThanTotalQuantityState() {
        this.message = "AVAILABLE QUANTITY CAN NOT BE GREATER THAN TOTAL QUANTITY.";
    }

}
