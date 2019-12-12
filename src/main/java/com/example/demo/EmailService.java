package com.example.demo;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    private String host = "smtp.gmail.com";
    private String port = "587";
    private String mailFrom = "hiringmanagerdev@gmail.com";
    private String password = "Yu$ufr98";
    private String mailTo = "yusufreyazuddin@gmail.com";
    // sets SMTP server properties
    private Properties properties = new Properties();

    public void setProperties(){
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    }

    public void sendPlainTextEmail(String subject, String message) throws AddressException,
            MessagingException {

       setProperties();

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFrom, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(mailFrom));
        InternetAddress[] toAddresses = { new InternetAddress(mailTo) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setText(message);

        // sends the e-mail
        Transport.send(msg);
    }
    public void sendEmailAttachment(String subject, String message, File file) throws AddressException,
            MessagingException {

        setProperties();

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFrom, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        try {
            // Create a default MimeMessage object.
            Message message1 = new MimeMessage(session);

            // Set From: header field of the header.
            message1.setFrom(new InternetAddress(mailFrom));

            // Set To: header field of the header.
            message1.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailTo));

            // Set Subject: header field
            message1.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(message);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "Interview Q&A";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message1.setContent(multipart);

            // Send message
            Transport.send(message1);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

