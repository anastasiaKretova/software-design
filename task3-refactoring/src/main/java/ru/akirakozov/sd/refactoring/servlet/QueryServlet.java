package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static ru.akirakozov.sd.refactoring.product.ProductHtml.printProductWithHeader;
import static ru.akirakozov.sd.refactoring.product.ProductHtml.printInfo;

public class QueryServlet extends AbstractServlet {

    public QueryServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            printProductWithHeader(productDao.getMaxPriceProduct(), "Product with max price: ", response.getWriter());
        } else if ("min".equals(command)) {
            printProductWithHeader(productDao.getMinPriceProduct(), "Product with min price: ", response.getWriter());
        } else if ("sum".equals(command)) {
            printInfo(productDao.getProductsSum(), "Summary price: ", response.getWriter());
        } else if ("count".equals(command)) {
            printInfo(productDao.getProductsCount(), "Number of products: ", response.getWriter());
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
    }
}
