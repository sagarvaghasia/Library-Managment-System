package com.project.Main.Models.BookIssueReturn.Factories;

import com.project.Main.Models.BookIssueReturn.Repositories.IBookIssueReturnRepository;
import com.project.Main.Models.BookIssueReturn.Validation.BookIssueReturnValidation;
import com.project.Main.Models.User.Repositories.IUserRepository;

public interface IBookIssueReturnValidationFactory {

    public BookIssueReturnValidation createBookIssueReturnValidation(IBookIssueReturnRepository bookIssueReturnRepository, IUserRepository userRepository);
}
