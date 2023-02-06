package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.product.ProductDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractServlet extends HttpServlet {
    protected final ProductDao productDao;

    public AbstractServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            doRequest(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected abstract void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException;
}
