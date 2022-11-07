//package googo.generalUse.project1.email;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//
//import com.google.api.client.util.store.FileDataStoreFactory;
//
//import com.google.api.services.gmail.GmailScopes;
//import com.google.api.services.gmail.Gmail;
//import java.io.FileInputStream;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
///**
// *
// * @author Stanchart
// */
//
//    
//
//
//
//public class GmailService {
//    /** Application name. */
//    private static final String APPLICATION_NAME =
//        "Gmail API Java Quickstart";
//
//    /** Directory to store user credentials for this application. */
//    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/gmail-java-quickstart.json");
//
//    /** Global instance of the {@link FileDataStoreFactory}. */
//    private static FileDataStoreFactory DATA_STORE_FACTORY;
//
//    /** Global instance of the JSON factory. */
//    private static final JsonFactory JSON_FACTORY=JacksonFactory.getDefaultInstance();
//
//    /** Global instance of the HTTP transport. */
//    private static HttpTransport HTTP_TRANSPORT;
//
//    /** Global instance of the scopes required by this quickstart.
//     *
//     * If modifying these scopes, delete your previously saved credentials
//     * at ~/.credentials/gmail-java-quickstart.json
//     */
//    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_COMPOSE);
//
//    static {
//        try {
//            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
//        } catch (GeneralSecurityException | IOException t) {
//            System.exit(1);
//        }
//    }
//
//    /**
//     * Creates an authorized Credential object.
//     * @return an authorized Credential object.
//     * @throws IOException
//     */
//    public static Credential authorize(){
//        
//    Credential credential=null;GoogleClientSecrets clientSecrets=null;
//        try {
//            // Load client secrets.
//            InputStream in =new FileInputStream("C:\\Users\\Stanchart\\Dropbox\\PMMS_2\\client_ids.json");
//          
//            clientSecrets =GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//            
//            // Build flow and trigger user authorization request.
//            GoogleAuthorizationCodeFlow flow =
//                    new GoogleAuthorizationCodeFlow.Builder(
//                            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                            .setDataStoreFactory(DATA_STORE_FACTORY)
//                            .setAccessType("offline")
//                            .build();
//            credential = new AuthorizationCodeInstalledApp(
//                    flow, new LocalServerReceiver()).authorize("user");
//            System.out.println(
//                    "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
//        } catch (Exception ex) {
//            Logger.getLogger(GmailService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return credential;
//    }
//
//    /**
//     * Build and return an authorized Gmail client service.
//     * @return an authorized Gmail client service
//     * @throws IOException
//     */
//    public static Gmail getGmailService(){
//        Gmail service=null;
//        Credential credential = authorize();
//        service= new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//   
//    return service;
//    }
//
//
//}
//
