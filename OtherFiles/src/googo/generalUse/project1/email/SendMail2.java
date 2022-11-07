//import javax.mail.*;
//import javax.mail.internet.*;
//import java.io.UnsupportedEncodingException;
//import java.util.*;
//public class Assimilator {
//public static void main(String[] args) {
//Properties props = new Properties();
//Session session = Session.getInstance(props);
//MimeMessage msg = new MimeMessage(session);
//Transport t = null;
//try {
//Address bill = new InternetAddress("god@microsoft.com", "Bill Gates");
//Address elliotte = new InternetAddress("elharo@ibiblio.org");
//msg.setText("Resistance is futile. You will be assimilated!");
//msg.setFrom(bill);
//msg.setRecipient(Message.RecipientType.TO, elliotte);
//msg.setSubject("You must comply.");
//t = session.getTransport("smtps");
//t.connect("smtp.gmail.com", "erharold", "password");
//t.sendMessage(msg, msg.getAllRecipients());
//} catch (MessagingException | UnsupportedEncodingException ex) {
//ex.printStackTrace();
//} finally { // Transport does not implement AutoCloseable :-(
//if (t != null) {
//try {
//t.close();
//} catch (MessagingException ex) {
//}
//}
//}
//}
//}
////Properties props = new Properties();
////final Session session = Session.getInstance(props);
////final Message msg = new MimeMessage(session);
////Address to = new InternetAddress(toField.getText());
////Address from = new InternetAddress(fromField.getText());
////msg.setContent(message.getText(), "text/plain");
////msg.setFrom(from);
////msg.setRecipient(Message.RecipientType.TO, to);
////msg.setSubject(subjectField.getText());
////final String hostname = hostField.getText();
////final String username = usernameField.getText();
////final String password = passwordField.getText();
////// Sending a message can take a non-trivial amount of time so
////// spawn a thread to handle it.
////Runnable r = new Runnable() {
////@Override
////public void run() {
////Transport t = null;
////try {
////t = session.getTransport("smtps");
////Sending Email from an Application | 13
////www.it-ebooks.info
////t.connect(hostname, username, password);
////t.sendMessage(msg, msg.getAllRecipients());
////} catch (MessagingException ex) {
////ex.printStackTrace();
////} finally { // Transport does not implement Autocloseable :-(
////if (t != null)
////try {
////t.close();
////} catch (MessagingException e) {
////// ignore
////}
////}
////}
////};
////Thread t = new Thread(r);
////t.start();
////message.setText("");
////} catch (MessagingException ex) {
////JOptionPane.showMessageDialog(getRootPane(),
////"Error sending message: " + ex.getMessage());
////}
////}
////}