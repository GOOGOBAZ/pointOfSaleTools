/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import googo.pmms.project2.accountsHelper.CreatingFolders;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.PostingMain;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.databaseConnectors.JdbcConnector1;
import googo.pmms.project2.databases.AccountNumberCreationDataBase;
import googo.pmms.project2.databases.BackUpRestoreDB;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import googo.pmms.project2.reportsHelper.BalanceSheet;
import java.sql.DatabaseMetaData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Stanchart
 */
public class Resource extends JFrame {
   String userId;
       MaxmumAmountBorrowedFormulas formulas= new  MaxmumAmountBorrowedFormulas();
         fileInputOutPutStreams fios= new fileInputOutPutStreams();
    GregorianCalendar cal = new GregorianCalendar(); 
   
 
    ArrayList<ArrayList<String>> data7;

    Formartter fmt = new Formartter();
    JdbcConnector quaryObj = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); 
 JdbcConnector1 loancon= new   JdbcConnector1(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "LoanDetails.txt")));
     DatabaseMetaData metadata = null;
      ArrayList<String> data8, column1,data;
      ArrayList<ArrayList<String>> data6;
    ArrayList<Object> data4;
DecimalFormat df2 = new DecimalFormat("#");
 
 DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
 DecimalFormat NumberFormatS =new DecimalFormat("#.##");
 ArrayList<ArrayList<Object>> data5;
   MyComboBoxModel modelcombo,modelcombo1;
   ObjectTableModel  model;
List datum;
 
    
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");

    Formartter ffm = new Formartter();
    Date Trndate,valuedate;
 
    Date date;
  SimpleDateFormat df;
  
          
     JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
    DatabaseQuaries dbq =new DatabaseQuaries();
    loanDatabaseQuaries loan=new loanDatabaseQuaries();
    Formartter form= new Formartter();
    AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
   ReportsDatabase report =new ReportsDatabase();
    BalanceSheet bsheet= new BalanceSheet();
    PostingMain post= new PostingMain(Resource.this);
    BackUpRestoreDB dbBackUp= new BackUpRestoreDB();
    CreatingFolders filesW= new CreatingFolders();

     public Resource(String userid){
     
     this.userId=userid;
     
     }
    
  
    
    public void cashierAccessRights(JTree tree){
      int v=0;
String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt")).split("[;]"); 

 DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
 
 while(v<=levels.length-2){
     if(v==levels.length-2){
      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
         top.add(level2);
     
     }else{
    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
         top.add(level2);
 }
  
    
v++;
 }   
      DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);
}   
     public void systemsAdminAcessRights(JTree tree){
      int v=0;
String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt")).split("[;]"); 

 DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
 
 while(v<=levels.length-2){
     if(v==levels.length-2){
      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
         top.add(level2);
     
     }else{
    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
         top.add(level2);
 }
  
    
v++;
 }   
      DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);
} 
 public void dateStarterAccessRights(JTree tree){
      int v=0;
String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt")).split("[;]"); 

 DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
 
 while(v<=levels.length-2){
     if(v==levels.length-2){
      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
         top.add(level2);
     
     }else{
    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
         top.add(level2);
 }
  
    
v++;
 }   
      DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);
}        
//        public void supervisorEmailRights(JTree tree){
//      int v=0;
//String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "supervisorpEmailAccess.txt")).split("[;]"); 
//
// DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
// 
// while(v<=levels.length-2){
//     if(v==levels.length-2){
//      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
//         top.add(level2);
//     
//     }else{
//    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
//         top.add(level2);
// }
//  
//    
//v++;
// }   
//      DefaultTreeModel treeModel = new DefaultTreeModel(top);
//     
//     tree.setModel(treeModel);
//}  
    
    public void accountantAccessRights(JTree tree){
      int v=0;
String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt")).split("[;]"); 

 DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
 
 while(v<=levels.length-2){
     if(v==levels.length-2){
      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
         top.add(level2);
     
     }else{
    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
         top.add(level2);
 }
  
    
v++;
 }             

  DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);

}
    
  
    
    
    public void supervisorAccessRights(JTree tree){
      int v=0;
String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt")).split("[;]"); 

 DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
 
 while(v<=levels.length-2){
     if(v==levels.length-2){
      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
         top.add(level2);
     
     }else{
    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
         top.add(level2);
 }
  
    
v++;
 }             

  DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);

}  

//    public void supervisorTreea(JTree tree){
//      int v=0;
//String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "supervisora.txt")).split("[;]"); 
//
// DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
// 
// while(v<=levels.length-2){
//     if(v==levels.length-2){
//      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
//         top.add(level2);
//     
//     }else{
//    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
//         top.add(level2);
// }
//  
//    
//v++;
// }             
//
//  DefaultTreeModel treeModel = new DefaultTreeModel(top);
//     
//     tree.setModel(treeModel);
//
//}  

public void managerAccessRights(JTree tree){
      int v=0;
String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt")).split("[;]"); 

 DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
 
 while(v<=levels.length-2){
     if(v==levels.length-2){
      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
         top.add(level2);
     
     }else{
    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
         top.add(level2);
 }
  
    
v++;
 }             

  DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);

}  
public void defaultAcessRights(JTree tree){
      int v=0;
String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt")).split("[;]"); 

 DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
 
 while(v<=levels.length-2){
     if(v==levels.length-2){
      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
         top.add(level2);
     
     }else{
    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
         top.add(level2);
 }
  
    
v++;
 }   
      DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);
} 
public void loansOfficerAccessRights(JTree tree){
      int v=0;
String [] levels=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt")).split("[;]"); 

 DefaultMutableTreeNode top = new DefaultMutableTreeNode(levels[0]);
 
 while(v<=levels.length-2){
     if(v==levels.length-2){
      DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1].replace(";", ""));    
         top.add(level2);
     
     }else{
    DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(levels[v+1]);   
         top.add(level2);
 }
  
    
v++;
 }             

  DefaultTreeModel treeModel = new DefaultTreeModel(top);
     
     tree.setModel(treeModel);

}

public void supervisorPostingRights(JComboBox jComboBox2){
    
      int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "supervisorPosting.txt")).split("[;]");     
        
        while(v<=post.length-1){if(v==0){userposting.add("Select Txn Type");}else if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo);  
        jComboBox2.setSelectedIndex(0);
}

public void CashierPostingRights(JComboBox jComboBox2){
        int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "cashierPosting.txt")).split("[;]");     
        
         while(v<=post.length-1){if(v==0){userposting.add("Select Txn Type");}else if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
        jComboBox2.setSelectedIndex(0);
}


public void accountantPostingRightsh(JComboBox jComboBox2){
       int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "accountantPosting.txt")).split("[;]");     
        
       while(v<=post.length-1){if(v==0){userposting.add("Select Txn Type");}else if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
        jComboBox2.setSelectedIndex(0);
}
public void managerPostingRights(JComboBox jComboBox2){
       int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "managerPosting.txt")).split("[;]");     
        
        while(v<=post.length-1){if(v==0){userposting.add("Select Txn Type");}else if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
        jComboBox2.setSelectedIndex(0);
}

public void loansOfficerPostingRights(JComboBox jComboBox2){
       int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "loansOfficerPosting.txt")).split("[;]");     
        
         while(v<=post.length-1){if(v==0){userposting.add("Select Txn Type");}else if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
        jComboBox2.setSelectedIndex(0);
}

public void systemsAdminPostingRights(JComboBox jComboBox2){
       int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "systemsAdminPosting.txt")).split("[;]");     
        
         while(v<=post.length-1){if(v==0){userposting.add("Select Txn Type");}else if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
        jComboBox2.setSelectedIndex(0);
}

public void allComboPostingRights(JComboBox jComboBox2){
       int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "posting_ITENS.txt")).split("[;]");     
        
        while(v<=post.length-1){if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
//        jComboBox2.setSelectedIndex(0);
}

public void allComboAccessRights(JComboBox jComboBox2){
       int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "accessLevel1.txt")).split("[;]");     
        
        while(v<=post.length-1){if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
}



public void displayAccessRights(JComboBox jComboBox2,String userGroup){

    switch(userGroup){
    case "Manager":
         int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt")).split("[;]");     
        
        while(v<=post.length-1){if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
        case "DateStarter":
         int vd=0; ArrayList <String> userpostingd=new ArrayList();
       
        String [] postd=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt")).split("[;]");     
        
        while(vd<=postd.length-1){if(vd==postd.length-1){userpostingd.add(postd[vd].replace(";", ""));}else{ userpostingd.add(postd[vd]);} vd++; }
       
        modelcombo = new MyComboBoxModel(userpostingd);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "Supervisor":
          int v1=0; ArrayList <String> userposting1=new ArrayList();
       
        String [] post1=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt")).split("[;]");     
        
        while(v1<=post1.length-1){if(v1==post1.length-1){userposting1.add(post1[v1].replace(";", ""));}else{ userposting1.add(post1[v1]);} v1++; }
       
        modelcombo = new MyComboBoxModel(userposting1);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "Accountant":
         int v2=0; ArrayList <String> userposting2=new ArrayList();
       
        String [] post2=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt")).split("[;]");     
        
        while(v2<=post2.length-1){if(v2==post2.length-1){userposting2.add(post2[v2].replace(";", ""));}else{ userposting2.add(post2[v2]);} v2++; }
       
        modelcombo = new MyComboBoxModel(userposting2);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "Cashier":
           int v3=0; ArrayList <String> userposting3=new ArrayList();
       
        String [] post3=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt")).split("[;]");     
        
        while(v3<=post3.length-1){if(v3==post3.length-1){userposting3.add(post3[v3].replace(";", ""));}else{ userposting3.add(post3[v3]);} v3++; }
       
        modelcombo = new MyComboBoxModel(userposting3);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "Loans Officer":
        int v4=0; ArrayList <String> userposting4=new ArrayList();
       
        String [] post4=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt")).split("[;]");     
        
        while(v4<=post4.length-1){if(v4==post4.length-1){userposting4.add(post4[v4].replace(";", ""));}else{ userposting4.add(post4[v4]);} v4++; }
       
        modelcombo = new MyComboBoxModel(userposting4);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "System Admin":
        
       int v5=0; ArrayList <String> userposting5=new ArrayList();
       
        String [] post5=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt")).split("[;]");     
        
        while(v5<=post5.length-1){if(v5==post5.length-1){userposting5.add(post5[v5].replace(";", ""));}else{ userposting5.add(post5[v5]);} v5++; }
       
        modelcombo = new MyComboBoxModel(userposting5);
       
        jComboBox2.setModel(modelcombo);    
        
        
        break;



    }

}

public void displayPostingRights(JComboBox jComboBox2,String userGroup){

    switch(userGroup){
    case "Manager":
         int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "managerPosting.txt")).split("[;]");     
        
        while(v<=post.length-1){if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "Supervisor":
          int v1=0; ArrayList <String> userposting1=new ArrayList();
       
        String [] post1=fios.stringFileReader(fios.createFileName("resources", "posting", "supervisorPosting.txt")).split("[;]");     
        
        while(v1<=post1.length-1){if(v1==post1.length-1){userposting1.add(post1[v1].replace(";", ""));}else{ userposting1.add(post1[v1]);} v1++; }
       
        modelcombo = new MyComboBoxModel(userposting1);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "Accountant":
         int v2=0; ArrayList <String> userposting2=new ArrayList();
       
        String [] post2=fios.stringFileReader(fios.createFileName("resources", "posting", "accountantPosting.txt")).split("[;]");     
        
        while(v2<=post2.length-1){if(v2==post2.length-1){userposting2.add(post2[v2].replace(";", ""));}else{ userposting2.add(post2[v2]);} v2++; }
       
        modelcombo = new MyComboBoxModel(userposting2);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "Cashier":
           int v3=0; ArrayList <String> userposting3=new ArrayList();
       
        String [] post3=fios.stringFileReader(fios.createFileName("resources", "posting", "cashierPosting.txt")).split("[;]");     
        
        while(v3<=post3.length-1){if(v3==post3.length-1){userposting3.add(post3[v3].replace(";", ""));}else{ userposting3.add(post3[v3]);} v3++; }
       
        modelcombo = new MyComboBoxModel(userposting3);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "Loans Officer":
        int v4=0; ArrayList <String> userposting4=new ArrayList();
       
        String [] post4=fios.stringFileReader(fios.createFileName("resources", "posting", "loansOfficerPosting.txt")).split("[;]");     
        
        while(v4<=post4.length-1){if(v4==post4.length-1){userposting4.add(post4[v4].replace(";", ""));}else{ userposting4.add(post4[v4]);} v4++; }
       
        modelcombo = new MyComboBoxModel(userposting4);
       
        jComboBox2.setModel(modelcombo); 
        
        break;
    case "System Admin":
        
       int v4X=0; ArrayList <String> userposting4X=new ArrayList();
       
        String [] post4X=fios.stringFileReader(fios.createFileName("resources", "posting", "systemsAdminPosting.txt")).split("[;]");     
        
        while(v4X<=post4X.length-1){if(v4X==post4X.length-1){userposting4X.add(post4X[v4X].replace(";", ""));}else{ userposting4X.add(post4X[v4X]);} v4X++; }
       
        modelcombo = new MyComboBoxModel(userposting4X);
       
        jComboBox2.setModel(modelcombo);   
        
        
        break;



    }

}

public boolean grantAccessRights(String accessRight,String userGroup){
boolean granted=false;


    switch(userGroup){
        
         case "Manager":
        
         int v=0,k=0; ArrayList <String> userAccessRightsT=new ArrayList();boolean done=true;
       
        String [] accessRightsFile=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt")).split("[;]");     
        
        while(v<=accessRightsFile.length-1){
            
            if(v==accessRightsFile.length-1){
                
                userAccessRightsT.add(accessRightsFile[v].replace(";", ""));}
            
            else{ 
                userAccessRightsT.add(accessRightsFile[v]) ;
            
            }
            
            v++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt"));
    
     while(k<userAccessRightsT.size()){
     
         if(k==1&&done){
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt"), accessRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt"), ";"); 
     k=0;done=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt"), userAccessRightsT.get(k));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt"), ";");           
         
         }
     k++;
     granted=true;
     }
        
        break;
    case "DateStarter":
        
         int vk=0,kk=0; ArrayList <String> userAccessRightsTk=new ArrayList();boolean donek=true;
       
        String [] accessRightsFilek=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt")).split("[;]");     
        
        while(vk<=accessRightsFilek.length-1){
            
            if(vk==accessRightsFilek.length-1){
                
                userAccessRightsTk.add(accessRightsFilek[vk].replace(";", ""));}
            
            else{ 
                userAccessRightsTk.add(accessRightsFilek[vk]) ;
            
            }
            
            vk++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt"));
    
     while(kk<userAccessRightsTk.size()){
     
         if(kk==1&&donek){
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt"), accessRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt"), ";"); 
     kk=0;donek=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt"), userAccessRightsTk.get(kk));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt"), ";");           
         
         }
     kk++;
     granted=true;
     }
        
        break;
    case "Supervisor":
           int v1=0,k1=0; ArrayList <String> userAccessRightsT1=new ArrayList();boolean done1=true;
          String [] accessRightsFile1=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt")).split("[;]");     
        
        while(v1<=accessRightsFile1.length-1){
            
            if(v1==accessRightsFile1.length-1){
                
                userAccessRightsT1.add(accessRightsFile1[v1].replace(";", ""));}
            
            else{ 
                userAccessRightsT1.add(accessRightsFile1[v1]) ;
            
            }
            
            v1++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt"));
    
     while(k1<userAccessRightsT1.size()){
     
         if(k1==1&&done1){
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt"), accessRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt"), ";"); 
     k1=0;done1=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt"), userAccessRightsT1.get(k1));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt"), ";");           
         
         }
     k1++;
     granted=true;
     }
        
        break;
    case "Accountant":
        int v2=0,k2=0; ArrayList <String> userAccessRightsT2=new ArrayList();boolean done2=true;
          String [] accessRightsFile2=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt")).split("[;]");     
        
        while(v2<=accessRightsFile2.length-1){
            
            if(v2==accessRightsFile2.length-1){
                
                userAccessRightsT2.add(accessRightsFile2[v2].replace(";", ""));}
            
            else{ 
                userAccessRightsT2.add(accessRightsFile2[v2]) ;
            
            }
            
            v2++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt"));
    
     while(k2<userAccessRightsT2.size()){
     
         if(k2==1&&done2){
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt"), accessRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt"), ";"); 
     k2=0;done2=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt"), userAccessRightsT2.get(k2));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt"), ";");           
         
         }
     k2++;
     granted=true;
     }
        
        break;
    case "Cashier":
          int v3=0,k3=0; ArrayList <String> userAccessRightsT3=new ArrayList();boolean done3=true;
          String [] accessRightsFile3=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt")).split("[;]");     
        
        while(v3<=accessRightsFile3.length-1){
            
            if(v3==accessRightsFile3.length-1){
                
                userAccessRightsT3.add(accessRightsFile3[v3].replace(";", ""));}
            
            else{ 
                userAccessRightsT3.add(accessRightsFile3[v3]) ;
            
            }
            
            v3++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt"));
    
     while(k3<userAccessRightsT3.size()){
     
         if(k3==1&&done3){
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt"), accessRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt"), ";"); 
     k3=0;done3=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt"), userAccessRightsT3.get(k3));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt"), ";");           
         
         }
     k3++;
     granted=true;
     }
        
        break;
    case "Loans Officer":
       int v4=0,k4=0; ArrayList <String> userAccessRightsT4=new ArrayList();boolean done4=true;
          String [] accessRightsFile4=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt")).split("[;]");     
        
        while(v4<=accessRightsFile4.length-1){
            
            if(v4==accessRightsFile4.length-1){
                
                userAccessRightsT4.add(accessRightsFile4[v4].replace(";", ""));}
            
            else{ 
                userAccessRightsT4.add(accessRightsFile4[v4]) ;
            
            }
            
            v4++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt"));
    
     while(k4<userAccessRightsT4.size()){
     
         if(k4==1&&done4){
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt"), accessRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt"), ";"); 
     k4=0;done4=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt"), userAccessRightsT4.get(k4));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt"), ";");           
         
         }
     k4++;
     granted=true;
     }
        
        break;
    case "System Admin":
        
      int v5=0,k5=0; ArrayList <String> userAccessRightsT5=new ArrayList();boolean done5=true;
          String [] accessRightsFile5=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt")).split("[;]");     
        
        while(v5<=accessRightsFile5.length-1){
            
            if(v5==accessRightsFile5.length-1){
                
                userAccessRightsT5.add(accessRightsFile5[v5].replace(";", ""));}
            
            else{ 
                userAccessRightsT5.add(accessRightsFile5[v5]) ;
            
            }
            
            v5++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt"));
    
     while(k5<userAccessRightsT5.size()){
     
         if(k5==1&&done5){
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt"), accessRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt"), ";"); 
     k5=0;done5=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt"), userAccessRightsT5.get(k5));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt"), ";");           
         
         }
     k5++;
     granted=true;
     }  
        
        
        break;



    }


return granted;

}

public boolean accessRightAlreadyExists(String accessRight,String userGroup){
     boolean itsIn=false;
      switch(userGroup){
    case "Manager":
        int v=0;  
  String [] acessRight=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt")).split("[;]");     

    while(v<=acessRight.length-1){

    if(acessRight[v].replace(";", "").equalsIgnoreCase(accessRight)){

    itsIn=true;

    break;
    }

    v++;    
    }
         case "DateStarter":
        int vg=0;  
  String [] acessRightg=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt")).split("[;]");     

    while(vg<=acessRightg.length-1){

    if(acessRightg[vg].replace(";", "").equalsIgnoreCase(accessRight)){

    itsIn=true;

    break;
    }

    vg++;    
    }  
        break;
    case "Supervisor":
        int v1=0;
   String [] acessRights=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt")).split("[;]");     

    while(v1<=acessRights.length-1){

    if(acessRights[v1].replace(";", "").equalsIgnoreCase(accessRight)){

    itsIn=true;

    break;
    }

    v1++;    
    }
      
        
        break;
    case "Accountant":
        int v2=0;
    String [] acessRightsa=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt")).split("[;]");      

    while(v2<=acessRightsa.length-1){

    if(acessRightsa[v2].replace(";", "").equalsIgnoreCase(accessRight)){

    itsIn=true;

    break;
    }

    v2++;    
    }
        
        break;
    case "Cashier":
        int v3=0;
  String [] acessRightsac=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt")).split("[;]");    

    while(v3<=acessRightsac.length-1){

    if(acessRightsac[v3].replace(";", "").equalsIgnoreCase(accessRight)){

    itsIn=true;

    break;
    }

    v3++;    
    }
        
        break;
    case "Loans Officer":
        int v4=0;
   String [] acessRightsacl=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt")).split("[;]");  
    while(v4<=acessRightsacl.length-1){

    if(acessRightsacl[v4].replace(";", "").equalsIgnoreCase(accessRight)){

    itsIn=true;

    break;
    }

    v4++;    
    }
        
        break;
    case "System Admin":
        int v5=0;
      String [] acessRightsacls=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt")).split("[;]");     
    while(v5<=acessRightsacls.length-1){

    if(acessRightsacls[v5].replace(";", "").equalsIgnoreCase(accessRight)){

    itsIn=true;

    break;
    }

    v5++;    
    }
       
        
        
        break;



    }


    return itsIn;   

}

public boolean revokeAccessRights(String accessRight,String userGroup){
boolean removed=false;


    switch(userGroup){
    case "Manager":
        
         int v=0,k=0; ArrayList <String> userAccessRightsT=new ArrayList();
       
        String [] accessRightsFile=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt")).split("[;]");     
        
        while(v<=accessRightsFile.length-1){
            
            if(v==accessRightsFile.length-1){
                
                userAccessRightsT.add(accessRightsFile[v].replace(";", ""));}
            
            else{ 
                userAccessRightsT.add(accessRightsFile[v]) ;
            
            }
            
            v++; 
        
        }
        
        int index=userAccessRightsT.indexOf(accessRight);
        if(index==0||index==userAccessRightsT.size()-1){
          removed=false; 
        }else{
        userAccessRightsT.remove(index);
          removed=true; 
        }
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt"));
    
     while(k<userAccessRightsT.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt"), userAccessRightsT.get(k));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "managerAccessLevel.txt"), ";");           
         
         
     k++;
     
     }
     
        break;
        
         case "DateStarter":
        
         int vc=0,kc=0; ArrayList <String> userAccessRightsTc=new ArrayList();
       
        String [] accessRightsFilec=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt")).split("[;]");     
        
        while(vc<=accessRightsFilec.length-1){
            
            if(vc==accessRightsFilec.length-1){
                
                userAccessRightsTc.add(accessRightsFilec[vc].replace(";", ""));}
            
            else{ 
                userAccessRightsTc.add(accessRightsFilec[vc]) ;
            
            }
            
            vc++; 
        
        }
        
        int indexc=userAccessRightsTc.indexOf(accessRight);
        if(indexc==0||indexc==userAccessRightsTc.size()-1){
          removed=false; 
        }else{
        userAccessRightsTc.remove(indexc);
          removed=true; 
        }
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt"));
    
     while(kc<userAccessRightsTc.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt"), userAccessRightsTc.get(kc));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "dateStarterAccessLevel.txt"), ";");           
         
         
     kc++;
     
     }
     
        break;
    case "Supervisor":
           int v1=0,k1=0; ArrayList <String> userAccessRightsT1=new ArrayList();boolean done1=true;
          String [] accessRightsFile1=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt")).split("[;]");     
        
        while(v1<=accessRightsFile1.length-1){
            
            if(v1==accessRightsFile1.length-1){
                
                userAccessRightsT1.add(accessRightsFile1[v1].replace(";", ""));}
            
            else{ 
                userAccessRightsT1.add(accessRightsFile1[v1]) ;
            
            }
            
            v1++; 
        
        }
         int index1=userAccessRightsT1.indexOf(accessRight);
        if(index1==0||index1==userAccessRightsT1.size()-1){
          removed=false; 
        }else{
        userAccessRightsT1.remove(index1);
          removed=true; 
        }
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt"));
    
     while(k1<userAccessRightsT1.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt"), userAccessRightsT1.get(k1));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "supervisorAccessLevel.txt"), ";");           
         
        k1++;
    
     }
       
        break;
    case "Accountant":
        int v2=0,k2=0; ArrayList <String> userAccessRightsT2=new ArrayList();boolean done2=true;
          String [] accessRightsFile2=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt")).split("[;]");     
        
        while(v2<=accessRightsFile2.length-1){
            
            if(v2==accessRightsFile2.length-1){
                
                userAccessRightsT2.add(accessRightsFile2[v2].replace(";", ""));}
            
            else{ 
                userAccessRightsT2.add(accessRightsFile2[v2]) ;
            
            }
            
            v2++; 
        
        }
        
          int index2=userAccessRightsT2.indexOf(accessRight);
        if(index2==0||index2==userAccessRightsT2.size()-1){
          removed=false; 
        }else{
        userAccessRightsT2.remove(index2);
          removed=true; 
        }
          
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt"));
    
     while(k2<userAccessRightsT2.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt"), userAccessRightsT2.get(k2));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "accountantAccessLevel.txt"), ";");           
         
         
     k2++;
    
     }
   
        break;
    case "Cashier":
          int v3=0,k3=0; ArrayList <String> userAccessRightsT3=new ArrayList();boolean done3=true;
          String [] accessRightsFile3=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt")).split("[;]");     
        
        while(v3<=accessRightsFile3.length-1){
            
            if(v3==accessRightsFile3.length-1){
                
                userAccessRightsT3.add(accessRightsFile3[v3].replace(";", ""));}
            
            else{ 
                userAccessRightsT3.add(accessRightsFile3[v3]) ;
            
            }
            
            v3++; 
        
        }
        
            int index3=userAccessRightsT3.indexOf(accessRight);
        if(index3==0||index3==userAccessRightsT3.size()-1){
          removed=false; 
        }else{
        userAccessRightsT3.remove(index3);
          removed=true; 
        }
        
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt"));
    
     while(k3<userAccessRightsT3.size()){
     
       
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt"), userAccessRightsT3.get(k3));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "cashierAccessLevel.txt"), ";");           
         
         
     k3++;
    
     }
        
        break;
    case "Loans Officer":
       int v4=0,k4=0; ArrayList <String> userAccessRightsT4=new ArrayList();boolean done4=true;
          String [] accessRightsFile4=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt")).split("[;]");     
        
        while(v4<=accessRightsFile4.length-1){
            
            if(v4==accessRightsFile4.length-1){
                
                userAccessRightsT4.add(accessRightsFile4[v4].replace(";", ""));}
            
            else{ 
                userAccessRightsT4.add(accessRightsFile4[v4]) ;
            
            }
            
            v4++; 
        
        }
             int index4=userAccessRightsT4.indexOf(accessRight);
        if(index4==0||index4==userAccessRightsT4.size()-1){
          removed=false; 
        }else{
        userAccessRightsT4.remove(index4);
          removed=true; 
        }
           
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt"));
    
     while(k4<userAccessRightsT4.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt"), userAccessRightsT4.get(k4));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "loansOfficerAccessLevel.txt"), ";");           
         
       
     k4++;
    
     }
        
        break;
    case "System Admin":
        
      int v5=0,k5=0; ArrayList <String> userAccessRightsT5=new ArrayList();boolean done5=true;
          String [] accessRightsFile5=fios.stringFileReader(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt")).split("[;]");     
        
        while(v5<=accessRightsFile5.length-1){
            
            if(v5==accessRightsFile5.length-1){
                
                userAccessRightsT5.add(accessRightsFile5[v5].replace(";", ""));}
            
            else{ 
                userAccessRightsT5.add(accessRightsFile5[v5]) ;
            
            }
            
            v5++; 
        
        }
            int index5=userAccessRightsT5.indexOf(accessRight);
        if(index5==0||index5==userAccessRightsT5.size()-1){
          removed=false; 
        }else{
        userAccessRightsT5.remove(index5);
          removed=true; 
        }
         
    fios.deleteFileExistance(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt"));
    
     while(k5<userAccessRightsT5.size()){
     
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt"), userAccessRightsT5.get(k5));
     fios.stringFileWriterAppend(fios.createFileName("resources", "accessLevels", "systemsAdminAccessLevel.txt"), ";");           
         
         
     k5++;
    
     }  
        
        
        break;



    }


return removed;

}



public boolean postingRightAlreadyExists(String postingRight,String userGroup){
     boolean itsIn=false;
      switch(userGroup){
    case "Manager":
        int v=0;  
  String [] acessRight=fios.stringFileReader(fios.createFileName("resources", "posting", "managerPosting.txt")).split("[;]");     

    while(v<=acessRight.length-1){

    if(acessRight[v].replace(";", "").equalsIgnoreCase(postingRight)){

    itsIn=true;

    break;
    }

    v++;    
    }
        
        break;
    case "Supervisor":
        int v1=0;
   String [] acessRights=fios.stringFileReader(fios.createFileName("resources", "posting", "supervisorPosting.txt")).split("[;]");     

    while(v1<=acessRights.length-1){

    if(acessRights[v1].replace(";", "").equalsIgnoreCase(postingRight)){

    itsIn=true;

    break;
    }

    v1++;    
    }
      
        
        break;
    case "Accountant":
        int v2=0;
    String [] acessRightsa=fios.stringFileReader(fios.createFileName("resources", "posting", "accountantPosting.txt")).split("[;]");      

    while(v2<=acessRightsa.length-1){

    if(acessRightsa[v2].replace(";", "").equalsIgnoreCase(postingRight)){

    itsIn=true;

    break;
    }

    v2++;    
    }
        
        break;
    case "Cashier":
        int v3=0;
  String [] acessRightsac=fios.stringFileReader(fios.createFileName("resources", "posting", "cashierPosting.txt")).split("[;]");    

    while(v3<=acessRightsac.length-1){

    if(acessRightsac[v3].replace(";", "").equalsIgnoreCase(postingRight)){

    itsIn=true;

    break;
    }

    v3++;    
    }
        
        break;
    case "Loans Officer":
        int v4=0;
   String [] acessRightsacl=fios.stringFileReader(fios.createFileName("resources", "posting", "loansOfficerPosting.txt")).split("[;]");  
    while(v4<=acessRightsacl.length-1){

    if(acessRightsacl[v4].replace(";", "").equalsIgnoreCase(postingRight)){

    itsIn=true;

    break;
    }

    v4++;    
    }
        
        break;
    case "System Admin":
        int v5=0;
      String [] acessRightsacls=fios.stringFileReader(fios.createFileName("resources", "posting", "systemsAdminPosting.txt")).split("[;]");     
    while(v5<=acessRightsacls.length-1){

    if(acessRightsacls[v5].replace(";", "").equalsIgnoreCase(postingRight)){

    itsIn=true;

    break;
    }

    v5++;    
    }
       
        
        
        break;



    }


    return itsIn;   

}

public boolean revokepostingRights(String postingRight,String userGroup){
boolean removed=false;


    switch(userGroup){
    case "Manager":
        
         int v=0,k=0; ArrayList <String> userPostingRightsT=new ArrayList();
       
        String [] postingRightsFile=fios.stringFileReader(fios.createFileName("resources", "posting", "managerPosting.txt")).split("[;]");     
        
        while(v<=postingRightsFile.length-1){
            
            if(v==postingRightsFile.length-1){
                
                userPostingRightsT.add(postingRightsFile[v].replace(";", ""));}
            
            else{ 
                userPostingRightsT.add(postingRightsFile[v]) ;
            
            }
            
            v++; 
        
        }
        
        int index=userPostingRightsT.indexOf(postingRight);
        userPostingRightsT.remove(index);
          removed=true; 
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "managerPosting.txt"));
    
     while(k<userPostingRightsT.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "managerPosting.txt"), userPostingRightsT.get(k));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "managerPosting.txt"), ";");           
         
         
     k++;
     
     }
     
        break;
    case "Supervisor":
           int v1=0,k1=0; ArrayList <String> userPostingRightsT1=new ArrayList();
          String [] postingRightsFile1=fios.stringFileReader(fios.createFileName("resources", "posting", "supervisorPosting.txt")).split("[;]");     
        
        while(v1<=postingRightsFile1.length-1){
            
            if(v1==postingRightsFile1.length-1){
                
                userPostingRightsT1.add(postingRightsFile1[v1].replace(";", ""));}
            
            else{ 
                userPostingRightsT1.add(postingRightsFile1[v1]) ;
            
            }
            
            v1++; 
        
        }
         int index1=userPostingRightsT1.indexOf(postingRight);
        userPostingRightsT1.remove(index1);
          removed=true; 
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "supervisorPosting.txt"));
    
     while(k1<userPostingRightsT1.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "supervisorPosting.txt"), userPostingRightsT1.get(k1));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "supervisorPosting.txt"), ";");           
         
        k1++;
    
     }
       
        break;
    case "Accountant":
        int v2=0,k2=0; ArrayList <String> userPostingRightsT2=new ArrayList();
          String [] postingRightsFile2=fios.stringFileReader(fios.createFileName("resources", "posting", "accountantPosting.txt")).split("[;]");     
        
        while(v2<=postingRightsFile2.length-1){
            
            if(v2==postingRightsFile2.length-1){
                
                userPostingRightsT2.add(postingRightsFile2[v2].replace(";", ""));}
            
            else{ 
                userPostingRightsT2.add(postingRightsFile2[v2]) ;
            
            }
            
            v2++; 
        
        }
        
          int index2=userPostingRightsT2.indexOf(postingRight);
        userPostingRightsT2.remove(index2);
          removed=true; 
        
          
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "accountantPosting.txt"));
    
     while(k2<userPostingRightsT2.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "accountantPosting.txt"), userPostingRightsT2.get(k2));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "accountantPosting.txt"), ";");           
         
         
     k2++;
    
     }
   
        break;
    case "Cashier":
          int v3=0,k3=0; ArrayList <String> userPostingRightsT3=new ArrayList();
          String [] postingRightsFile3=fios.stringFileReader(fios.createFileName("resources", "posting", "cashierPosting.txt")).split("[;]");     
        
        while(v3<=postingRightsFile3.length-1){
            
            if(v3==postingRightsFile3.length-1){
                
                userPostingRightsT3.add(postingRightsFile3[v3].replace(";", ""));}
            
            else{ 
                userPostingRightsT3.add(postingRightsFile3[v3]) ;
            
            }
            
            v3++; 
        
        }
        
            int index3=userPostingRightsT3.indexOf(postingRight);
        userPostingRightsT3.remove(index3);
          removed=true; 
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "cashierPosting.txt"));
    
     while(k3<userPostingRightsT3.size()){
     
       
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "cashierPosting.txt"), userPostingRightsT3.get(k3));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "cashierPosting.txt"), ";");           
         
         
     k3++;
   
     }
        
        break;
    case "Loans Officer":
       int v4=0,k4=0; ArrayList <String> userPostingRightsT4=new ArrayList();
          String [] postingRightsFile4=fios.stringFileReader(fios.createFileName("resources", "posting", "loansOfficerPosting.txt")).split("[;]");     
        
        while(v4<=postingRightsFile4.length-1){
            
            if(v4==postingRightsFile4.length-1){
                
                userPostingRightsT4.add(postingRightsFile4[v4].replace(";", ""));}
            
            else{ 
                userPostingRightsT4.add(postingRightsFile4[v4]) ;
            
            }
            
            v4++; 
        
        }
             int index4=userPostingRightsT4.indexOf(postingRight);
        userPostingRightsT4.remove(index4);
          removed=true; 
        
           
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"));
    
     while(k4<userPostingRightsT4.size()){
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"), userPostingRightsT4.get(k4));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"), ";");           
         
       
     k4++;
    
     }
        
        break;
    case "System Admin":
        
      int v5=0,k5=0; ArrayList <String> userPostingRightsT5=new ArrayList();
          String [] postingRightsFile5=fios.stringFileReader(fios.createFileName("resources", "posting", "systemsAdminPosting.txt")).split("[;]");     
        
        while(v5<=postingRightsFile5.length-1){
            
            if(v5==postingRightsFile5.length-1){
                
                userPostingRightsT5.add(postingRightsFile5[v5].replace(";", ""));}
            
            else{ 
                userPostingRightsT5.add(postingRightsFile5[v5]) ;
            
            }
            
            v5++; 
        
        }
           int index5=userPostingRightsT5.indexOf(postingRight);
          userPostingRightsT5.remove(index5);
          removed=true; 
     
         
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"));
    
     while(k5<userPostingRightsT5.size()){
     
     
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"), userPostingRightsT5.get(k5));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"), ";");           
         
         
     k5++;
   
     }  
        
        
        break;



    }


return removed;

}

public boolean grantpostingRights(String postingRight,String userGroup){
boolean granted=false;


    switch(userGroup){
    case "Manager":
        
         int v=0,k=0; ArrayList <String> userPostingRightsT=new ArrayList();boolean done=true;
       
        String [] postingRightsFile=fios.stringFileReader(fios.createFileName("resources", "posting", "managerPosting.txt")).split("[;]");     
        
        while(v<=postingRightsFile.length-1){
            
            if(v==postingRightsFile.length-1){
                
                userPostingRightsT.add(postingRightsFile[v].replace(";", ""));}
            
            else{ 
                userPostingRightsT.add(postingRightsFile[v]) ;
            
            }
            
            v++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "managerPosting.txt"));
    
    if(userPostingRightsT.isEmpty()){
        
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "managerPosting.txt"), postingRight);
     
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "managerPosting.txt"), ";"); 
     
    }else{
    
    
    while(k<userPostingRightsT.size()){
     
         if(k==0&&done){
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "managerPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "managerPosting.txt"), ";"); 
     k=-1;done=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "managerPosting.txt"), userPostingRightsT.get(k));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "managerPosting.txt"), ";");           
         
         }
     k++;
    
     }
    
    }  
     granted=true;
     
        break;
    case "Supervisor":
           int v1=0,k1=0; ArrayList <String> userPostingRightsT1=new ArrayList();boolean done1=true;
          String [] accessRightsFile1=fios.stringFileReader(fios.createFileName("resources", "posting", "supervisorPosting.txt")).split("[;]");     
        
        while(v1<=accessRightsFile1.length-1){
            
            if(v1==accessRightsFile1.length-1){
                
                userPostingRightsT1.add(accessRightsFile1[v1].replace(";", ""));}
            
            else{ 
                userPostingRightsT1.add(accessRightsFile1[v1]) ;
            
            }
            
            v1++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "supervisorPosting.txt"));
    if(userPostingRightsT1.isEmpty()){
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "supervisorPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "supervisorPosting.txt"), ";"); 
    }else{
     while(k1<userPostingRightsT1.size()){
     
         if(k1==0&&done1){
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "supervisorPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "supervisorPosting.txt"), ";"); 
     k1=-1;done1=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "supervisorPosting.txt"), userPostingRightsT1.get(k1));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "supervisorPosting.txt"), ";");           
         
         }
     k1++;
    
     }
    
    }
        granted=true; 
        break;
    case "Accountant":
        int v2=0,k2=0; ArrayList <String> userpostingRightsT2=new ArrayList();boolean done2=true;
          String [] accessRightsFile2=fios.stringFileReader(fios.createFileName("resources", "posting", "accountantPosting.txt")).split("[;]");     
        
        while(v2<=accessRightsFile2.length-1){
            
            if(v2==accessRightsFile2.length-1){
                
                userpostingRightsT2.add(accessRightsFile2[v2].replace(";", ""));}
            
            else{ 
                userpostingRightsT2.add(accessRightsFile2[v2]) ;
            
            }
            
            v2++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "accountantPosting.txt"));
    if(userpostingRightsT2.isEmpty()){
      fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "accountantPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "accountantPosting.txt"), ";"); 
    
    }else{
     while(k2<userpostingRightsT2.size()){
     
         if(k2==0&&done2){
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "accountantPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "accountantPosting.txt"), ";"); 
     k2=-1;done2=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "accountantPosting.txt"), userpostingRightsT2.get(k2));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "accountantPosting.txt"), ";");           
         
         }
     k2++;
    
     }}
        granted=true; 
        break;
    case "Cashier":
          int v3=0,k3=0; ArrayList <String> userPostingRightsT3=new ArrayList();boolean done3=true;
          String [] accessRightsFile3=fios.stringFileReader(fios.createFileName("resources", "posting", "cashierPosting.txt")).split("[;]");     
        
        while(v3<=accessRightsFile3.length-1){
            
            if(v3==accessRightsFile3.length-1){
                
                userPostingRightsT3.add(accessRightsFile3[v3].replace(";", ""));}
            
            else{ 
                userPostingRightsT3.add(accessRightsFile3[v3]) ;
            
            }
            
            v3++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "cashierPosting.txt"));
    
    if(userPostingRightsT3.isEmpty()){
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "cashierPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "cashierPosting.txt"), ";"); 
    }else{
     while(k3<userPostingRightsT3.size()){
     
         if(k3==0&&done3){
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "cashierPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "cashierPosting.txt"), ";"); 
     k3=-1;done3=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "cashierPosting.txt"), userPostingRightsT3.get(k3));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "cashierPosting.txt"), ";");           
         
         }
     k3++;
  
     }}
        granted=true;   
        break;
    case "Loans Officer":
       int v4=0,k4=0; ArrayList <String> userPostingRightsT4=new ArrayList();boolean done4=true;
          String [] accessRightsFile4=fios.stringFileReader(fios.createFileName("resources", "posting", "loansOfficerPosting.txt")).split("[;]");     
        
        while(v4<=accessRightsFile4.length-1){
            
            if(v4==accessRightsFile4.length-1){
                
                userPostingRightsT4.add(accessRightsFile4[v4].replace(";", ""));}
            
            else{ 
                userPostingRightsT4.add(accessRightsFile4[v4]) ;
            
            }
            
            v4++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"));
    
    
    if(userPostingRightsT4.isEmpty()){
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"), ";"); 
    }else{
     while(k4<userPostingRightsT4.size()){
     
         if(k4==0&&done4){
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"), ";"); 
     k4=-1;done4=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"), userPostingRightsT4.get(k4));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "loansOfficerPosting.txt"), ";");           
         
         }
     k4++;
     granted=true;
     }
    }
        break;
    case "System Admin":
        
      int v5=0,k5=0; ArrayList <String> userPostingRightsT5=new ArrayList();boolean done5=true;
          String [] accessRightsFile5=fios.stringFileReader(fios.createFileName("resources", "posting", "systemsAdminPosting.txt")).split("[;]");     
        
        while(v5<=accessRightsFile5.length-1){
            
            if(v5==accessRightsFile5.length-1){
                
                userPostingRightsT5.add(accessRightsFile5[v5].replace(";", ""));}
            
            else{ 
                userPostingRightsT5.add(accessRightsFile5[v5]) ;
            
            }
            
            v5++; 
        
        }
        
        
    fios.deleteFileExistance(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"));
    
    if(userPostingRightsT5.isEmpty()){
      fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"), ";"); 
    }else{
     while(k5<userPostingRightsT5.size()){
     
         if(k5==0&&done5){
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"), postingRight);
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"), ";"); 
     k5=-1;done5=false;
         }else{
    fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"), userPostingRightsT5.get(k5));
     fios.stringFileWriterAppend(fios.createFileName("resources", "posting", "systemsAdminPosting.txt"), ";");           
         
         }
     k5++;
  
     }  
    }
         granted=true;  
        break;



    }


return granted;

}

}
