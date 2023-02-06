package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.product.Product;
import ru.akirakozov.sd.refactoring.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class QueryServlet extends AbstractServlet {
    public QueryServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            Optional<Product> maxPriceProduct = productDao.getMaxPriceProduct();
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");
            response.getWriter().println(maxPriceProduct.get().toHTML());
            response.getWriter().println("</body></html>");
        } else if ("min".equals(command)) {
            Optional<Product> maxPriceProduct = productDao.getMinPriceProduct();
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");
            response.getWriter().println(maxPriceProduct.get().toHTML());
            response.getWriter().println("</body></html>");
        } else if ("sum".equals(command)) {
            long productsSum = productDao.getProductsSum();
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");
            response.getWriter().println(productsSum);
            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            long productsCount = productDao.getProductsCount();
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");
            response.getWriter().println(productsCount);
            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
    }
}
