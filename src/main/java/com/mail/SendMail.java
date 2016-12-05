package com.mail;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    public static void sendMail(MessageVo mv ){

        final String username = "heng85208520@163.com";
        final String password = "heng85208520";
        boolean isSSL = true;
        String host = "smtp.163.com";
        int port = 465;
        boolean isAuth = true;
        
        String from = "heng85208520@163.com";
        
        Properties props = new Properties();
        props.put("mail.smtp.ssl.enable", isSSL);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", isAuth);
        
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
 
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject(mv.getSubject());
            message.setText(mv.getText());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mv.getToMailAddress()));

            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
 
        System.out.println("发送完毕！");
    }
}