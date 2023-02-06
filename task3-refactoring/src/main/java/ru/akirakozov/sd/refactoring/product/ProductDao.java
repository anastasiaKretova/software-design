package ru.akirakozov.sd.refactoring.product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.SQLException;

import static ru.akirakozov.sd.refactoring.product.DataBase.getConnection;

public class ProductDao {
    private List<Product> parseProducts(ResultSet rs) throws SQLException {
        List<Product> result = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            result.add(new Product(name, price));
        }
        return result;
    }

    public void insertProduct(Product product) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "INSERT INTO PRODUCT " + "(NAME, PRICE) VALUES " +
                    "(\"" + product.getName() + "\"," + product.getPrice() + ")";
            stmt.executeUpdate(sql);
        }
    }

    public List<Product> getProducts() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "SELECT * FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);
            return parseProducts(rs);
        }
    }

    public Optional<Product> getMaxPriceProduct() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            return parseProducts(rs).stream().findFirst();
        }
    }

    public Optional<Product> getMinPriceProduct() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            return parseProducts(rs).stream().findFirst();
        }
    }

    public long getProductsSum() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "SELECT SUM(price) as sum FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);
            return rs.getLong("sum");
        }
    }

    public int getProductsCount() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            String sql = "SELECT COUNT(*) as cnt FROM PRODUCT";
            ResultSet rs = stmt.executeQuery(sql);
            return rs.getInt("cnt");
        }
    }
}
