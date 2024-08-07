package com.hendrikweiler.testqueries;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Logger;

public class Mail {

    private final static Logger logger = Logger.getLogger(Mail.class.getName());

    public static void sendMail(String to, String subject, String message) {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
            String snName = "mail";
            Session session = (Session)ic.lookup(snName);
            MimeMessage msg = new MimeMessage(session);
            msg.addRecipient(MimeMessage.RecipientType.TO, new jakarta.mail.internet.InternetAddress(to));
            msg.setSubject(subject);
            msg.setContent(message, "text/plain");
            Transport.send(msg, session.getProperty("user"), session.getProperty("password"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
