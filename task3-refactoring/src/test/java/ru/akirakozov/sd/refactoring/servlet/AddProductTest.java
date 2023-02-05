package ru.akirakozov.sd.refactoring.servlet;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class AddProductTest extends AbstractTest {
    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private AddProductServlet servlet;
    StringWriter stringWriter = new StringWriter();
    PrintWriter printer = new PrintWriter(stringWriter);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void addSetUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new AddProductServlet();

        when(servletResponse.getWriter()).thenReturn(printer);
    }

    @Test
    public void addProductSuccess() throws IOException {
        when(servletRequest.getParameter("name")).thenReturn("iphone6");
        when(servletRequest.getParameter("price")).thenReturn("300");

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("OK\n", stringWriter.toString());
    }
}

