package com.example.coursework.dao;

import com.example.coursework.entity.Product;
import com.example.coursework.entity.Supplier;
import com.example.coursework.entity.Tag;
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

public class TagDao implements Dao<Integer, Tag>{

    private final String FIND_BY_ID = """
            SELECT * from tags where id = ?;
            """;

    private final String FIND_LIST_TO_CATEGORY = """
            SELECT * from product 
            join tags_product cp on product.id = cp.product_id
            join tags c on cp.tag_id = c.id
            where c.id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE tags
            SET name = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into tags(id, name)  VALUES 
             (?,?);
            """;

    private final String DELETE = """
            DELETE from tags where id = ?
            """;

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM tags");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                findById(id, connection).ifPresent(tags::add);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tags;
    }

    @Override
    public Optional<Tag> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
             PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_LIST_TO_CATEGORY)) {

            preparedStatement.setInt(1, id);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            List<Product> products = new ArrayList<>();

            while (resultSet1.next()){
                products.add(Product.builder()
                        .id(resultSet1.getInt("id"))
                        .name(resultSet1.getString("name"))
                        .metal(Metal.valueOf(resultSet1.getString("metal").replaceAll(" ", "_").toUpperCase()))
                        .cost(resultSet1.getInt("cost"))
                        .image(resultSet1.getString("image"))
                        .supplier((Supplier) DaoFactory.createDao(Supplier.class).findById(resultSet1.getInt("supplier_id"), connection).get())
                        .build());
            }

            if (resultSet.next()) {
                return Optional.of(Tag.builder()
                        .id(resultSet.getInt("id"))
                        .tag(resultSet.getString("name").replaceAll("-", "_").toUpperCase())
                        .products(products)
                        .build()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
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
    public int update(Tag entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.setString(1, entity.getTag());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Tag entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getTag());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
