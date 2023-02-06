package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.product.Product;
import ru.akirakozov.sd.refactoring.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class GetProductsServlet extends AbstractServlet {

    public GetProductsServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        List<Product> products = productDao.getProducts();

        response.getWriter().println("<html><body>");
        for (Product product : products) {
            response.getWriter().println(product.toHTML());
        }
        response.getWriter().println("</body></html>");
    }
}
