package com.example.coursework.dao;

import com.example.coursework.entity.Cart;
import com.example.coursework.entity.Product;
import com.example.coursework.entity.Supplier;
import com.example.coursework.entity.enums.Metal;
import com.example.coursework.factories.DaoFactory;
import com.example.coursework.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartDao implements Dao<Integer, Cart> {

    private final String FIND_BY_ID = """
            SELECT * from cart where id = ?;
            """;

    private final static String SELECT_ALL = """
            Select * from cart
            """;

    private final String FIND_PRODUCTS_BY_ID = """
            SELECT * from cart_product
            join product p on cart_product.product_id = p.id
            where cart_product.cart_id=?;
            """;

    private final String UPDATE_DATA = """
            UPDATE cart
            SET total_cost = ?,
                total_amount = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into cart(id, total_cost, total_amount)  VALUES 
             (?,?,?);
            """;

    private final String DELETE = """
            DELETE from cart where id = ?
            """;


    @Override
    public List<Cart> findAll() {
        List<Cart> carts = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                carts.add(Cart.builder()
                        .id(resultSet.getInt("id"))
                        .totalCost(resultSet.getFloat("total_cost"))
                        .totalAmount(resultSet.getInt("total_amount"))
                        .build()
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return carts;
    }

    @Override
    public Optional<Cart> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
             PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_PRODUCTS_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                preparedStatement1.setInt(1, id);
            }
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            List<Product> products = new ArrayList<>();

            while (resultSet1.next()) {
                products.add(Product.builder()
                        .id(resultSet1.getInt("id"))
                        .name(resultSet1.getString("name"))
                        .metal(Metal.valueOf(resultSet1.getString("metal").replaceAll(" ", "_").toUpperCase()))
                        .cost(resultSet1.getInt("cost"))
                        .image(resultSet1.getString("image"))
                        .supplier((Supplier) DaoFactory.createDao(Supplier.class).findById(resultSet1.getInt("supplier_id"), connection).get())
                        .build()
                );
            }
            return Optional.of(new Cart(
                    resultSet.getInt("id"),
                    resultSet.getFloat("total_cost"),
                    resultSet.getInt("total_amount"),
                    products
            ));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int update(Cart entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.setFloat(1, entity.getTotalCost());
            preparedStatement.setInt(2, entity.getTotalAmount());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Cart entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setFloat(2, entity.getTotalCost());
            preparedStatement.setInt(3, entity.getTotalAmount());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
