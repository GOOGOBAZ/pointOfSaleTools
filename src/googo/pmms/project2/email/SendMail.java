/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.email;


import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import java.awt.Component;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
public class SendMail {
    
  fileInputOutPutStreams fios= new fileInputOutPutStreams();
  
  Transport t = null;  Session session = null;
  
    public enum SendMethod{
        
        HTTP, TLS, SSL
        
    }

    private  final String EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public boolean isValidEmail(String address){
        return (address!=null && address.matches(EMAIL_PATTERN));
    }

    public  String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public  boolean sendEmail(final String recipients, final String from,
            final String subject, final String contents,final List attachments,
            final String smtpserver, final String username, final String password, final SendMethod method,Component c) {

        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", smtpserver);

      

        switch (method){
        case HTTP:
            if (username!=null) props.setProperty("mail.user", username);
            if (password!=null) props.setProperty("mail.password", password);
            session = Session.getInstance(props);
            break;
        case TLS:
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(username, password);
                }
            });
            break;
        case SSL:
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(username, password);
                }
            });
            break;
        }

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(from);
            message.addRecipients(Message.RecipientType.TO, recipients.replace(";", ""));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            BodyPart bodypart = new MimeBodyPart();
            bodypart.setContent(contents, "text/html");

            multipart.addBodyPart(bodypart);

            if (!attachments.isEmpty()){
                for (Object theRepoert:attachments){
                    bodypart = new MimeBodyPart();
                    File file = new File(theRepoert.toString());
                    DataSource datasource = new FileDataSource(file);
                    bodypart.setDataHandler(new DataHandler(datasource));
                    bodypart.setFileName(file.getName());
                    multipart.addBodyPart(bodypart);
                }
            }

                    message.setContent(multipart);


//JOptionPane.showMessageDialog(c, username+"\n"+smtpserver+"\n"+password);

                  Runnable r = new Runnable() {
        @Override
        public void run() {

        try {
        t = session.getTransport("smtps");
        t.connect(smtpserver, username, password);
        t.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException ex) {
        ex.printStackTrace();
        } finally { // Transport does not implement Autocloseable :-(
        if (t != null)
        try {
        t.close();
        } catch (MessagingException e) {
             e.printStackTrace();
        // ignore
        }
        }
        }
        };
        Thread th = new Thread(r);
        th.start();
        //message.setText("");
        } catch (MessagingException ex) {
             ex.printStackTrace();
        return false;
        }
        return true;
            }
        }
