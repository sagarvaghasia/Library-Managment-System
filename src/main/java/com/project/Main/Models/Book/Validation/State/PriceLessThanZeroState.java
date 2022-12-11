package com.project.Main.Models.Book.Validation.State;

public class PriceLessThanZeroState extends BookValidationState {

    public PriceLessThanZeroState() {
        this.message = "PRICE IS LESS THAN ZERO.";
    }

}
