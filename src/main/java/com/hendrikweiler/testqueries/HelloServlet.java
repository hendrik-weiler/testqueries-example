package com.hendrikweiler.testqueries;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Logger;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    Connection connection = null;

    private final static Logger logger = Logger.getLogger(HelloServlet.class.getName());

    public class User {
        public int id;
        public String username;
        public String password;

        public User(int id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = SQL.init();
        String user = req.getParameter("username");
        String pw = req.getParameter("password");
        String sql = String.format("insert into users values (null,'%s','%s','')", user, pw);
        SQL.queryInsert(sql);

        InitialContext ic = null;
        try {
            ic = new InitialContext();
            String snName = "mail";
            Session session = (Session)ic.lookup(snName);
            Mail.sendMail(session.getProperty("to"), "New customer", "A new customer has been added to the database");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SQL.close();

        resp.sendRedirect(req.getContextPath() + "/hello-servlet");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        request.setAttribute("customers_json", "[]");
        connection = SQL.init();
        List<User> users = new ArrayList<User>();

        Object pageObj = request.getParameter("page");
        int page = 0;
        if(pageObj != null) {
            page = Integer.parseInt(pageObj.toString());
        }

        ResultSet set = null;
        try {
            set = SQL.querySelect("""
                select *
                from users
                limit %d, 100;
            """.formatted(page*100));
            while (set.next()) {
                var ctr = new User(set.getInt(1),set.getString(2), set.getString(3));
                users.add(ctr);
            }
            
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonArray = objectMapper.writeValueAsString(users);
            request.setAttribute("customers_json", jsonArray);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("page", page);

        SQL.close();
        logger.info("Hello, World!");
        request.setAttribute("customers", users);
        request.getRequestDispatcher("helloServlet.jsp").forward(request, response);
    }
}