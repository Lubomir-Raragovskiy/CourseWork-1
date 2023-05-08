package com.example.coursework.servlets.tables;

import com.example.coursework.dao.CategoryDao;
import com.example.coursework.entity.Category;
import com.example.coursework.utill.ConnectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {

    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categoryList = new CategoryDao().findAll().stream().sorted().collect(Collectors.toList());
        req.setAttribute("allCategories", categoryList);
        req.getRequestDispatcher("/WEB-INF/categories.jsp").forward(req, resp);
        req.setAttribute("check", 0);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = ConnectionManager.get();
        String action = req.getParameter("action");
        if ("Save".equals(action)) {
            Category user = Category.builder()
                    .id(Integer.parseInt(req.getParameter("field1")))
                    .category(req.getParameter("field2"))
                    .build();
            new CategoryDao().save(user, connection);
            List<Category> categoryList = new CategoryDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allCategories", categoryList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/categories.jsp").forward(req, resp);
        }
        else if ("Delete".equals(action)) {
            String idsString = req.getParameter("dfield");
            boolean categoryDao = new CategoryDao().delete(Integer.parseInt(idsString),connection);
            List<Category> categoryList = new CategoryDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allCategories", categoryList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/categories.jsp").forward(req, resp);
        }
        else if ("Update".equals(action)) {
            String uString = req.getParameter("ufield");
            id=Integer.parseInt(uString);
            Category category = new CategoryDao().findById(id,connection).get();
            List<Category> categoryList = new CategoryDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("Category", category);
            req.setAttribute("allCategories", categoryList);
            req.setAttribute("check", 1);
            req.getRequestDispatcher("/WEB-INF/categories.jsp").forward(req, resp);
        }
        else if ("Submit".equals(action)) {
            Category user = Category.builder()
                    .id(id)
                    .category(req.getParameter("field10")).build();
            int success = new CategoryDao().update(user,connection);
            List<Category> categoryList = new CategoryDao().findAll().stream().sorted().collect(Collectors.toList());
            req.setAttribute("allCategories", categoryList);
            req.setAttribute("check", 0);
            req.getRequestDispatcher("/WEB-INF/categories.jsp").forward(req, resp);
        }
    }
}
