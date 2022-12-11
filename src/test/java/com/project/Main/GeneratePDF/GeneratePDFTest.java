package com.project.Main.GeneratePDF;

import com.project.Main.MockFactories.MockBookRepositoryFactory;
import com.project.Main.MockFactories.MockUserRepositoryFactory;
import com.project.Main.Models.Book.Factories.IBookRepositoryFactory;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.Book.Repositories.IBookRepository;
import com.project.Main.Models.BookFilter.Factories.BookFilterFactory;
import com.project.Main.Models.GeneratePDF.Factories.GeneratePDFFactory;
import com.project.Main.Models.GeneratePDF.IGeneratePDF;
import com.project.Main.Models.User.Factories.IUserRepositoryFactory;
import com.project.Main.Models.User.Factories.UserFactory;
import com.project.Main.Models.User.IUser;
import com.project.Main.Models.User.Repositories.IUserRepository;
import com.project.Main.Models.CommonState.FilterState.FilterState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class GeneratePDFTest {

    private static IBookRepositoryFactory bookRepositoryFactory = MockBookRepositoryFactory.instance();

    private static IBookRepository mockBookRepository = bookRepositoryFactory.createBookRepository(null);

    private static IUserRepositoryFactory userRepositoryFactory = MockUserRepositoryFactory.instance();

    private static IUserRepository mockUserRepository = userRepositoryFactory.createUserRepository(null);

    @Test
    public void generatePdfTest() {

        Map.Entry<List<IBook>, FilterState> books = BookFilterFactory
                .instance()
                .createBookFilter(
                        mockBookRepository
                ).getBooks();

        IUser user = UserFactory
                .instance()
                .createUser(
                        mockUserRepository
                );

        LoadState status = user.loadUserByEmailId("khushalgondaliya@gmail.com");
        IGeneratePDF generatePDF = GeneratePDFFactory
                .instance().createGeneratePDF();
        File fileOutput = generatePDF.generatePdf(books.getKey(), user);
        assertNotEquals(null, fileOutput);

    }
}
