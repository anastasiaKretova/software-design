package ru.akirakozov.sd.refactoring.servlet;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.product.Product;
import ru.akirakozov.sd.refactoring.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class AddProductTest extends AbstractTest {
    @Mock
    private ProductDao productDao;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private AbstractServlet servlet;
    StringWriter stringWriter = new StringWriter();
    PrintWriter printer = new PrintWriter(stringWriter);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void addSetUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new AddProductServlet(productDao);

        when(servletResponse.getWriter()).thenReturn(printer);
    }

    @Test
    public void addProductSuccess() throws SQLException, IOException {
        when(servletRequest.getParameter("name")).thenReturn("iphone6");
        when(servletRequest.getParameter("price")).thenReturn("300");

        servlet.doRequest(servletRequest, servletResponse);

        ArgumentCaptor<Product> product = ArgumentCaptor.forClass(Product.class);
        verify(productDao).insertProduct(product.capture());

        printer.flush();

        Assert.assertEquals("OK\n", stringWriter.toString());
        Assert.assertEquals(product.getValue(), new Product("iphone6", 300));
    }
}

