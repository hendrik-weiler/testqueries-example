package com.hendrikweiler.testqueries;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    Connection connection = null;

    private static final Logger logger = LoggerFactory.getLogger(HelloServlet.class);

    public void init() {
        connection = SQL.init();
    }

    public class Customer {
        public int id;
        public String first_name;

        public Customer(int id, String first_name) {
            this.id = id;
            this.first_name = first_name;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SQL.queryInsert("""
           insert into customers (first_name,number_of_complaints) values ('""" + req.getParameter("first_name") + """
        ',0)
        """);

        InitialContext ic = null;
        try {
            ic = new InitialContext();
            String snName = "mail";
            Session session = (Session)ic.lookup(snName);
            MimeMessage msg = new MimeMessage(session);
            msg.addRecipient(MimeMessage.RecipientType.TO, new jakarta.mail.internet.InternetAddress(session.getProperty("to")));
            msg.setSubject("New Customer");
            msg.setContent("A new customer has been added: " + req.getParameter("first_name"), "text/plain");
            Transport.send(msg, session.getProperty("user"), session.getProperty("password"));
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/hello-servlet");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        request.setAttribute("customers_json", "[]");

        List<Customer> customers = new ArrayList<Customer>();
        ResultSet set = null;
        try {
            set = SQL.querySelect("""
                SELECT customer_id, first_name
                FROM customers
                ORDER BY first_name desc
            """);
            while (set.next()) {
                var ctr = new Customer(set.getInt(1),set.getString(2));
                customers.add(ctr);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonArray = objectMapper.writeValueAsString(customers);
            request.setAttribute("customers_json", jsonArray);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        logger.info("Hello, World!");
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public void destroy() {
        SQL.close();
    }
}