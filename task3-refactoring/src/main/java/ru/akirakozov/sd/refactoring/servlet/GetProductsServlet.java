package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static ru.akirakozov.sd.refactoring.product.ProductHtml.printProducts;

public class GetProductsServlet extends AbstractServlet {

    public GetProductsServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        printProducts(productDao.getProducts(), response.getWriter());
    }
}
