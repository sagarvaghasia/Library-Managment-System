package com.project.Main.Models.GeneratePDF;


import com.lowagie.text.DocumentException;
import com.project.Main.Models.Book.IBook;
import com.project.Main.Models.User.IUser;
import org.springframework.core.io.ClassPathResource;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GeneratePDF implements IGeneratePDF {

    private static final String PDF_RESOURCES = "/pdf-resources/";

    @Override
    public File generatePdf(List<IBook> books, IUser user) {

        try {
            String html = createHTML(books, user);
            return renderPdf(html);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("Document Exception");
        }
        return null;
    }


    private File renderPdf(String html) throws IOException, DocumentException {

        File file = File.createTempFile("bill", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();

        return file;
    }

    private String createHTML(List<IBook> books, IUser user) {

        String html = "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"https://thymeleaf.org\">\n" +
                "\n" +
                "<head>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.min.css\"/>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"container\" style=\"margin-top:50px;\">\n" +
                "\n" +
                "\n" +
                "    <div class=\"row\">\n" +
                "        <center><h1> Book Issue Return Invoice</h1></center>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-6\">\n" +
                "            <table class=\"table table-bordered\">\n" +
                "                <tbody>\n" +
                "                <tr >\n" +
                "                    <td class=\"col-6\">User Name</td>\n" +
                "                    <td class=\"col-6\">" +
                user.getName() +
                "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td class=\"col-6\">User Email</td>\n" +
                "                    <td class=\"col-6\">" +
                user.getEmailId() +
                "</td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-sm\">\n" +
                "            <table class=\"table table-bordered\">\n" +
                "                <thead>\n" +
                "                <tr>\n" +
                "                    <th>ID</th>\n" +
                "                    <th>Book Name</th>\n" +
                "                    <th>Price</th>\n" +
                "                </tr>\n" +
                "                </thead>\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                    <td>" + 1 + "</td>\n" +
                "                    <td>" + books.get(0).getTitle() + "</td>\n" +
                "                    <td>" + books.get(0).getPricePerUnit() + "</td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col center\">\n" +
                "            Total Price: <span>" + books.get(0).getPricePerUnit() + "</span>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "<!--    <footer class=\"footer\">-->\n" +
                "<!--        This invoice is generated automatically.-->\n" +
                "<!--    </footer>-->\n" +
                "\n" +
                "    <footer class=\"page-footer font-small blue\">\n" +
                "\n" +
                "        <!-- Copyright -->\n" +
                "        <div class=\"footer-copyright text-center py-3\">\n" +
                "            © 2020 Copyright, This invoice is generated automatically.\n" +
                "        </div>\n" +
                "        <!-- Copyright -->\n" +
                "\n" +
                "    </footer>\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";

        return html;
    }
}
