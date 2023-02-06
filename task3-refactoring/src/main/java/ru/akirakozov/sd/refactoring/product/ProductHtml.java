package ru.akirakozov.sd.refactoring.product;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class ProductHtml {
    public static void printProducts(List<Product> products, PrintWriter printer) {
        printer.println("<html><body>");
        for (Product product : products) {
            printer.println(product.toHTML());
        }
        printer.println("</body></html>");
    }

    public static void printProductWithHeader(Optional<Product> product, String header, PrintWriter printer) {
        printer.println("<html><body>");
        printer.println("<h1>" + header + "</h1>");
        product.ifPresent(p -> printer.println(p.toHTML()));
        printer.println("</body></html>");
    }

    public static void printInfo(Object info, String data, PrintWriter printer) {
        printer.println("<html><body>");
        printer.println(data);
        printer.println(info);
        printer.println("</body></html>");
    }
}
