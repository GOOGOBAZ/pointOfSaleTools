package googo.pmms.project2.frames;


import googo.pmms.project2.accountsHelper.BatchPosting;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.PostingMain;
import googo.pmms.project2.accountsHelper.PostingModal;
import googo.pmms.project2.frameHelper.IUpdateText;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import googo.pmms.project2.frameHelper.MyComboBoxModel;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.accountsHelper.updateLoans;
import googo.pmms.project2.databases.AccountNumberCreationDataBase;
import googo.pmms.project2.databases.BackUpRestoreDB;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import googo.pmms.project2.frameHelper.Resource;
import googo.pmms.project2.smallFrames.PayRollModule;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stanchart
 */
public class CreateNewStaff extends JFrame implements MouseListener, IUpdateText, ActionListener {
       BackUpRestoreDB dbBackup=new BackUpRestoreDB();
       SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
  
        CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
     String userId;
    SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
 int n=0;
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
    DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
    ArrayList<String> data4, column1;
 ArrayList<ArrayList<String>> data5;
          JOptionPane p;
    PostingModal model ;
    JFrame fi, f,fa,fb,fc;
    Date date;
  SimpleDateFormat df;
     Formartter fmt= new Formartter();
 DatabaseQuaries dbq= new DatabaseQuaries();
 MaxmumAmountBorrowedFormulas maxValue= new MaxmumAmountBorrowedFormulas();
 loanDatabaseQuaries loan=new loanDatabaseQuaries();
 GregorianCalendar cal = new GregorianCalendar(); 
 AccountNumberCreationDataBase db =new AccountNumberCreationDataBase();

    String dates, dates2,getFieldValue,actualFieldValue,  jTFuserId1mt,thistime,today1,newDate1,jTFuserId1mt1,newDate11,today2;
    
    String staffid;
 
    MyComboBoxModel modelcombo;

  
   String jTFuserId1;
    PostingMain postinAccrualsn1=new PostingMain(CreateNewStaff.this);    
    public CreateNewStaff(String userId) {
        this.userId=userId;
        initComponents();
    AdminAccountTable.setAlignmentX(LEFT_ALIGNMENT);
  AdminAccountTable.setAlignmentY(CENTER_ALIGNMENT);
  AdminAccountTable.setAutoscrolls(true);
  AdminAccountTable.setShowHorizontalLines(true);
  AdminAccountTable.setShowGrid(true);
  AdminAccountTable.setRowHeight(20);
  AdminAccountTable.setCellSelectionEnabled(false);
  AdminAccountTable.setColumnSelectionAllowed(false);
  AdminAccountTable.setRowSelectionAllowed(true);
  AdminAccountTable.setAlignmentX(LEFT_ALIGNMENT);
  AdminAccountTable.setAlignmentY(CENTER_ALIGNMENT);
  AdminAccountTable.setAutoscrolls(true);
  AdminAccountTable.setShowHorizontalLines(true);
  AdminAccountTable.setShowGrid(true);
  AdminAccountTable.setRowHeight(20);


  AdminAccountTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
     this.setHorizontalAlignment(JLabel.CENTER);
      if(col==4){
          Number c = (Number)parseDouble(value.toString());
       String text = NumberFormat.format(c );
           this.setText(text);
      
      }
      else{this.setText(value.toString());  }
//      if(isSelected){setBackground(Color.CYAN);}
   
        return this;
    }   
});
     AdminAccountTable.addMouseListener( this);
     adminMainTable.addMouseListener( this);
        Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
      this.setIconImage(img);
       this.setTitle("STAFF MANAGEMENT MODULE"); 
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
       jTree1.addMouseListener( this);
     
       new Timer (1000, this).start();
       
        adminMainTable.setAutoscrolls(true);
adminMainTable.setRowHeight(30);
adminMainTable.setGridColor(Color.gray);

         AdminAccountTable.setAutoscrolls(true);
AdminAccountTable.setRowHeight(30);
AdminAccountTable.setGridColor(Color.gray);


 Resource rscs=new Resource(userId);
switch(dbq.title(userId)){
            case "DateStarter":
        rscs.dateStarterAccessRights(jTree1);
        jLabel31.setVisible(false);
            jComboBox8.setVisible(false);
            jLabel24.setVisible(false);
            jLabel32 .setVisible(false);      
            jLabel34  .setVisible(false);     
            jLabel29 .setVisible(false);    
            jComboBox6    .setVisible(false); 
            jComboBox7.setVisible(false);
            jComboBox11  .setVisible(false);    
            jComboBox5 .setVisible(false);    
            jButton19  .setVisible(false);   
            jButton18  .setVisible(false);  
            jButton17.setVisible(false);
            jButton16  .setVisible(false);     
                                
                                
                break;
            case "System Admin":
  rscs.systemsAdminAcessRights(jTree1);
break;
            default: 
             rscs.defaultAcessRights(jTree1);
                    
                    break;
        
        }
    
    }          
//public void setUserID(String userid){
//this.userId=userid;
//}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        staffPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        AdminMainPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        adminMainTable = new javax.swing.JTable();
        jButton23 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField9 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField16 = new javax.swing.JTextField();
        jPasswordField4 = new javax.swing.JPasswordField();
        jPasswordField5 = new javax.swing.JPasswordField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        AccountNumberPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AdminAccountTable = new javax.swing.JTable();
        jButton13 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        accessRights = new javax.swing.JPanel();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jButton16 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox();
        jLabel34 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jTextField15 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jComboBox9 = new javax.swing.JComboBox<>();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jTextField23 = new javax.swing.JTextField();
        jComboBox10 = new javax.swing.JComboBox<>();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jComboBox12 = new javax.swing.JComboBox<>();
        jComboBox13 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox14 = new javax.swing.JComboBox<>();
        jButton30 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Staff Module");
        setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        staffPanel.setBackground(java.awt.SystemColor.activeCaption);
        staffPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 51), null));
        staffPanel.setForeground(java.awt.SystemColor.control);
        staffPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        staffPanel.setFocusCycleRoot(true);
        staffPanel.setPreferredSize(new java.awt.Dimension(900, 697));
        staffPanel.setLayout(null);

        jTree1.setBackground(java.awt.SystemColor.activeCaption);
        jTree1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTree1.setForeground(java.awt.SystemColor.control);
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            public void treeCollapsed(javax.swing.event.TreeExpansionEvent evt) {
            }
            public void treeExpanded(javax.swing.event.TreeExpansionEvent evt) {
                jTree1TreeExpanded(evt);
            }
        });
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        staffPanel.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 180, 640);

        AdminMainPanel.setBackground(java.awt.SystemColor.activeCaption);
        AdminMainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        AdminMainPanel.setLayout(null);

        jPanel4.setBackground(java.awt.SystemColor.activeCaption);
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), null));
        jPanel4.setForeground(java.awt.SystemColor.control);
        jPanel4.setLayout(null);

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("Create Admin");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5);
        jButton5.setBounds(0, 0, 120, 30);

        jButton9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton9.setText("Create/Change password");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9);
        jButton9.setBounds(120, 0, 210, 30);
        jButton9 .setEnabled(false);

        jButton12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton12.setText("Edit Admin");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton12);
        jButton12.setBounds(330, 0, 120, 30);
        jButton12 .setEnabled(false);

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Delete Admin");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);
        jButton3.setBounds(450, 0, 120, 30);
        jButton3.setEnabled(false);

        jButton15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton15.setText("Acess Rights");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton15);
        jButton15.setBounds(720, 0, 70, 30);
        jButton15.setEnabled(false);
        jButton15.setVisible(false);

        jButton31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton31.setText("Create PayRoll");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton31);
        jButton31.setBounds(570, 0, 150, 30);
        jButton31.setEnabled(false);

        jButton32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton32);
        jButton32.setBounds(800, 0, 40, 30);

        AdminMainPanel.add(jPanel4);
        jPanel4.setBounds(0, 5, 850, 40);

        adminMainTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane4.setViewportView(adminMainTable);

        AdminMainPanel.add(jScrollPane4);
        jScrollPane4.setBounds(0, 300, 850, 310);

        jButton23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton23.setText("Export Excel");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        AdminMainPanel.add(jButton23);
        jButton23.setBounds(110, 510, 110, 40);

        jButton25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton25.setText("Print");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        AdminMainPanel.add(jButton25);
        jButton25.setBounds(0, 510, 110, 40);

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Search By Any Column Field");
        AdminMainPanel.add(jLabel26);
        jLabel26.setBounds(220, 510, 240, 40);

        jTextField22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        AdminMainPanel.add(jTextField22);
        jTextField22.setBounds(470, 510, 290, 40);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(java.awt.SystemColor.control);
        jPanel1.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(255, 204, 255));
        jPanel5.setLayout(null);

        jLabel1.setText("Emp Name");
        jPanel5.add(jLabel1);
        jLabel1.setBounds(10, 11, 79, 27);

        jLabel2.setText("Emp Name");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(385, 238, 50, 14);

        jLabel3.setText("Emp Name");
        jPanel5.add(jLabel3);
        jLabel3.setBounds(530, 218, 50, 14);

        jLabel4.setText("Emp Name");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(530, 198, 50, 14);

        jLabel5.setText("Emp Name");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(530, 178, 50, 14);

        jLabel6.setText("Emp Name");
        jPanel5.add(jLabel6);
        jLabel6.setBounds(530, 158, 50, 14);

        jLabel7.setText("Emp Name");
        jPanel5.add(jLabel7);
        jLabel7.setBounds(530, 138, 50, 14);

        jLabel8.setText("Emp Name");
        jPanel5.add(jLabel8);
        jLabel8.setBounds(530, 118, 50, 14);

        jLabel9.setText("Emp Name");
        jPanel5.add(jLabel9);
        jLabel9.setBounds(530, 98, 50, 14);

        jLabel10.setText("Emp Name");
        jPanel5.add(jLabel10);
        jLabel10.setBounds(530, 78, 50, 14);

        jTextField1.setText("jTextField1");
        jPanel5.add(jTextField1);
        jTextField1.setBounds(283, 270, 59, 20);

        jTextField2.setText("jTextField1");
        jPanel5.add(jTextField2);
        jTextField2.setBounds(283, 212, 59, 20);

        jTextField3.setText("jTextField1");
        jPanel5.add(jTextField3);
        jTextField3.setBounds(283, 154, 59, 20);

        jTextField4.setText("jTextField1");
        jPanel5.add(jTextField4);
        jTextField4.setBounds(283, 96, 59, 20);

        jTextField5.setText("jTextField1");
        jPanel5.add(jTextField5);
        jTextField5.setBounds(478, 38, 59, 20);

        jTextField6.setText("jTextField1");
        jPanel5.add(jTextField6);
        jTextField6.setBounds(413, 38, 59, 20);

        jTextField7.setText("jTextField1");
        jPanel5.add(jTextField7);
        jTextField7.setBounds(348, 38, 59, 20);

        jTextField8.setText("jTextField1");
        jPanel5.add(jTextField8);
        jTextField8.setBounds(93, 14, 110, 20);

        jPanel1.add(jPanel5);
        jPanel5.setBounds(0, 40, 0, 330);

        jLabel11.setBackground(new java.awt.Color(255, 255, 51));
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("First Name");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(10, 60, 70, 30);
        jLabel11.setVisible(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(370, 110, 170, 30);
        jComboBox1.setVisible(false);

        jTextField9.setForeground(new java.awt.Color(0, 102, 51));
        jPanel1.add(jTextField9);
        jTextField9.setBounds(340, 60, 200, 30);
        jTextField9 .setVisible(false);

        jLabel12.setBackground(new java.awt.Color(255, 255, 51));
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Manager");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(290, 110, 80, 30);
        jLabel12.setVisible(false);

        jLabel13.setBackground(new java.awt.Color(255, 255, 51));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Account No.");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(350, 10, 70, 30);

        jTextField10.setForeground(new java.awt.Color(0, 102, 51));
        jPanel1.add(jTextField10);
        jTextField10.setBounds(130, 110, 130, 30);
        jTextField10.setVisible(false);

        jTextField11.setForeground(new java.awt.Color(0, 102, 51));
        jTextField11.setText("Self");
        jPanel1.add(jTextField11);
        jTextField11.setBounds(670, 110, 140, 30);
        jTextField11.setVisible(false);

        jLabel14.setBackground(new java.awt.Color(255, 255, 51));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Last Name");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(270, 60, 70, 30);
        jLabel14.setVisible(false);

        jLabel15.setBackground(new java.awt.Color(255, 255, 51));
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Birth Date");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(590, 60, 60, 30);
        jLabel15.setVisible(false);

        jTextField12.setForeground(new java.awt.Color(0, 102, 51));
        jPanel1.add(jTextField12);
        jTextField12.setBounds(660, 60, 130, 30);
        jTextField12.setVisible(false);

        jLabel16.setBackground(new java.awt.Color(255, 255, 51));
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("UserName");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(180, 20, 130, 30);
        jLabel16.setVisible(false);

        jComboBox2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select", " " }));
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(190, 10, 130, 30);

        jLabel17.setBackground(new java.awt.Color(255, 255, 51));
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Recruitement Date");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(10, 110, 110, 30);
        jLabel17 .setVisible(false);

        jTextField13.setForeground(new java.awt.Color(0, 102, 51));
        jTextField13.setText("Office Attendant");
        jPanel1.add(jTextField13);
        jTextField13.setBounds(340, 160, 210, 30);
        jTextField13.setVisible(false);

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(440, 210, 130, 30);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(260, 110, 20, 30);
        jButton6.setVisible(false);

        jButton7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7);
        jButton7.setBounds(300, 210, 130, 30);

        jButton8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton8.setText("Save ");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8);
        jButton8.setBounds(150, 210, 130, 30);

        jButton10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton10.setText("SUBMIT");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton10);
        jButton10.setBounds(210, 180, 150, 30);
        jButton10.setVisible(false);

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton11);
        jButton11.setBounds(790, 60, 20, 30);
        jButton11.setVisible(false);

        jLabel18.setBackground(new java.awt.Color(255, 255, 51));
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Title");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(130, 10, 50, 30);

        jTextField14.setForeground(new java.awt.Color(0, 102, 51));
        jPanel1.add(jTextField14);
        jTextField14.setBounds(90, 60, 160, 30);
        jTextField14.setVisible(false);

        jLabel19.setBackground(new java.awt.Color(255, 255, 51));
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Position");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(260, 160, 70, 30);
        jLabel19.setVisible(false);

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("Select Account No.");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(470, 10, 210, 30);

        jTextField16.setForeground(new java.awt.Color(0, 102, 51));
        jPanel1.add(jTextField16);
        jTextField16.setBounds(470, 10, 210, 30);
        jTextField16.setVisible(false);
        jPanel1.add(jPasswordField4);
        jPasswordField4.setBounds(330, 80, 180, 30);
        jPasswordField4 .setVisible(false);
        jPanel1.add(jPasswordField5);
        jPasswordField5.setBounds(330, 120, 180, 30);
        jPasswordField5.setVisible(false);

        jLabel20.setBackground(new java.awt.Color(255, 255, 51));
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Former employment");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(550, 110, 120, 30);
        jLabel20.setVisible(false);

        jLabel21.setBackground(new java.awt.Color(255, 255, 51));
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Enter New Password:");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(180, 80, 140, 30);
        jLabel21.setVisible(false);

        jComboBox3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(330, 10, 180, 30);
        jComboBox3.setVisible(false);

        jLabel22.setBackground(new java.awt.Color(255, 255, 51));
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Re-Enter New Password:");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(180, 120, 140, 30);
        jLabel22.setVisible(false);

        jButton14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton14.setText("CANCEL");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton14);
        jButton14.setBounds(360, 180, 150, 30);
        jButton14.setVisible(false);

        jLabel23.setBackground(new java.awt.Color(255, 255, 51));
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText(" User ID");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(180, 50, 90, 30);
        jLabel23.setVisible(false);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox4);
        jComboBox4.setBounds(330, 50, 180, 30);
        jComboBox4 .setVisible(false);

        AdminMainPanel.add(jPanel1);
        jPanel1.setBounds(0, 30, 850, 610);
        jPanel1.setVisible(false);

        staffPanel.add(AdminMainPanel);
        AdminMainPanel.setBounds(180, 0, 850, 640);
        AdminMainPanel.setVisible(false);

        AccountNumberPanel.setBackground(java.awt.SystemColor.activeCaption);
        AccountNumberPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        AccountNumberPanel.setForeground(java.awt.SystemColor.control);
        AccountNumberPanel.setLayout(null);

        AdminAccountTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        AdminAccountTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AdminAccountTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(AdminAccountTable);

        AccountNumberPanel.add(jScrollPane2);
        jScrollPane2.setBounds(0, 40, 830, 440);

        jButton13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton13.setText("Create New Account");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        AccountNumberPanel.add(jButton13);
        jButton13.setBounds(220, 480, 220, 40);

        jButton26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton26.setText("Print");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        AccountNumberPanel.add(jButton26);
        jButton26.setBounds(0, 480, 110, 40);

        jButton27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton27.setText("Export Excel");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        AccountNumberPanel.add(jButton27);
        jButton27.setBounds(110, 480, 110, 40);

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Search By Any Column Field");
        AccountNumberPanel.add(jLabel25);
        jLabel25.setBounds(440, 480, 220, 40);
        AccountNumberPanel.add(jTextField21);
        jTextField21.setBounds(640, 480, 190, 40);

        staffPanel.add(AccountNumberPanel);
        AccountNumberPanel.setBounds(180, 0, 850, 550);
        AccountNumberPanel.setVisible(false);

        accessRights.setBackground(java.awt.SystemColor.activeCaption);
        accessRights.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accessRights.setLayout(null);

        jComboBox5.setBackground(new java.awt.Color(0, 204, 153));
        jComboBox5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        accessRights.add(jComboBox5);
        jComboBox5.setBounds(540, 80, 190, 30);

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setForeground(java.awt.SystemColor.control);
        jLabel24.setText("Access Rights");
        jLabel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accessRights.add(jLabel24);
        jLabel24.setBounds(10, 50, 180, 30);

        jComboBox8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        accessRights.add(jComboBox8);
        jComboBox8.setBounds(280, 10, 180, 30);

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setForeground(java.awt.SystemColor.control);
        jLabel29.setText("Posting Rights");
        jLabel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accessRights.add(jLabel29);
        jLabel29.setBounds(540, 50, 190, 30);

        jComboBox6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        accessRights.add(jComboBox6);
        jComboBox6.setBounds(10, 80, 180, 30);
        jComboBox6.setSelectedItem("");

        jButton16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton16.setText("Revoke Posting Rights");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        accessRights.add(jButton16);
        jButton16.setBounds(540, 110, 190, 30);

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setForeground(java.awt.SystemColor.control);
        jLabel31.setText("Select User Group");
        jLabel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accessRights.add(jLabel31);
        jLabel31.setBounds(120, 10, 160, 30);

        jLabel32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel32.setForeground(java.awt.SystemColor.control);
        jLabel32.setText(" Posting Rights");
        jLabel32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accessRights.add(jLabel32);
        jLabel32.setBounds(190, 50, 170, 30);

        jComboBox7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        accessRights.add(jComboBox7);
        jComboBox7.setBounds(190, 80, 170, 30);

        jLabel34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel34.setForeground(java.awt.SystemColor.control);
        jLabel34.setText("Access Rights");
        jLabel34.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accessRights.add(jLabel34);
        jLabel34.setBounds(360, 50, 180, 30);

        jComboBox11.setBackground(new java.awt.Color(0, 204, 204));
        jComboBox11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });
        accessRights.add(jComboBox11);
        jComboBox11.setBounds(360, 80, 180, 30);

        jButton18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton18.setText("Grant Posting Rights");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        accessRights.add(jButton18);
        jButton18.setBounds(190, 110, 170, 30);

        jButton19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton19.setText("Grant Access Rights");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        accessRights.add(jButton19);
        jButton19.setBounds(10, 110, 180, 30);

        jButton17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton17.setText("Revoke Access Rights");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        accessRights.add(jButton17);
        jButton17.setBounds(360, 110, 180, 30);
        accessRights.add(jTextField15);
        jTextField15.setBounds(280, 10, 160, 30);
        jTextField15.setVisible(false);

        jTextField17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        accessRights.add(jTextField17);
        jTextField17.setBounds(10, 80, 180, 30);
        jTextField17.setVisible(false);

        jTextField18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        accessRights.add(jTextField18);
        jTextField18.setBounds(190, 80, 170, 30);
        jTextField18.setVisible(false);

        jTextField19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        accessRights.add(jTextField19);
        jTextField19.setBounds(360, 80, 180, 30);
        jTextField19.setVisible(false);

        jTextField20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        accessRights.add(jTextField20);
        jTextField20.setBounds(540, 80, 190, 30);
        jTextField20.setVisible(false);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        accessRights.add(jButton1);
        jButton1.setBounds(800, 10, 40, 40);

        jButton20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton20.setText("Start Day");
        jButton20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        accessRights.add(jButton20);
        jButton20.setBounds(510, 340, 150, 50);
        jButton20.setEnabled(false);

        jButton21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton21.setText("Update System Variables");
        jButton21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        accessRights.add(jButton21);
        jButton21.setBounds(340, 340, 170, 50);
        jButton21.setEnabled(false);

        jButton22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton22.setText("Update Accounts");
        jButton22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        accessRights.add(jButton22);
        jButton22.setBounds(210, 340, 130, 50);
        jButton22.setEnabled(false);

        jButton24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton24.setText("Update Loans");
        jButton24.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        accessRights.add(jButton24);
        jButton24.setBounds(30, 340, 180, 50);
        jButton24.setEnabled(false);

        jComboBox9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVATE", "DEACTIVATE" }));
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });
        accessRights.add(jComboBox9);
        jComboBox9.setBounds(30, 210, 140, 40);

        jButton28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton28.setText("APPLY");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        accessRights.add(jButton28);
        jButton28.setBounds(440, 210, 100, 40);
        jButton28.setVisible(false);

        jButton29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton29.setText("APPLY");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        accessRights.add(jButton29);
        jButton29.setBounds(650, 150, 80, 40);
        jButton29.setVisible(false);

        jTextField23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField23.setText("Txn Type");
        jTextField23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField23FocusGained(evt);
            }
        });
        jTextField23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField23ActionPerformed(evt);
            }
        });
        accessRights.add(jTextField23);
        jTextField23.setBounds(300, 150, 130, 40);
        jTextField23.setVisible(false);
        if(jTextField23.getText().equalsIgnoreCase("Txn Type")){
            jTextField23.setForeground(Color.GRAY);
        }else if(jTextField23.hasFocus()){

            jTextField23.setForeground(Color.BLACK);
        }

        jComboBox10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        accessRights.add(jComboBox10);
        jComboBox10.setBounds(170, 210, 270, 40);
        jComboBox10.setVisible(false);

        jTextField24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField24.setText("Txn Code");
        jTextField24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField24FocusGained(evt);
            }
        });
        accessRights.add(jTextField24);
        jTextField24.setBounds(430, 150, 90, 40);
        jTextField24.setVisible(false);
        if(jTextField24.getText().equalsIgnoreCase("Txn Code")){
            jTextField24.setForeground(Color.GRAY);
        }else{

            jTextField23.setForeground(Color.BLACK);
        }

        jTextField25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField25.setText("Description");
        jTextField25.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField25FocusGained(evt);
            }
        });
        accessRights.add(jTextField25);
        jTextField25.setBounds(520, 150, 130, 40);
        jTextField25.setVisible(false);
        if(jTextField25.getText().equalsIgnoreCase("Description")){
            jTextField25.setForeground(Color.GRAY);
        }else{

            jTextField23.setForeground(Color.BLACK);
        }

        jComboBox12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CREATE TXN", "UPDATE TXN" }));
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });
        accessRights.add(jComboBox12);
        jComboBox12.setBounds(30, 150, 140, 40);

        jComboBox13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        accessRights.add(jComboBox13);
        jComboBox13.setBounds(170, 150, 130, 40);
        jComboBox13.setVisible(false);

        jCheckBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox1.setText("DELETE TXN");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        accessRights.add(jCheckBox1);
        jCheckBox1.setBounds(30, 280, 140, 40);

        jComboBox14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        accessRights.add(jComboBox14);
        jComboBox14.setBounds(170, 280, 270, 40);
        jComboBox14.setVisible(false);

        jButton30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton30.setText("APPLY");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        accessRights.add(jButton30);
        jButton30.setBounds(440, 280, 100, 40);
        jButton30.setVisible(false);

        staffPanel.add(accessRights);
        accessRights.setBounds(180, 0, 850, 550);

        getContentPane().add(staffPanel);
        staffPanel.setBounds(0, 0, 1070, 700);

        jButton56.setBackground(new java.awt.Color(255, 204, 204));
        jButton56.setText("Blue");
        getContentPane().add(jButton56);
        jButton56.setBounds(1510, 190, 53, 23);

        jButton52.setBackground(new java.awt.Color(0, 204, 102));
        jButton52.setText("Blue");
        getContentPane().add(jButton52);
        jButton52.setBounds(1450, 220, 53, 23);

        jButton54.setBackground(new java.awt.Color(0, 204, 204));
        jButton54.setText("Blue");
        getContentPane().add(jButton54);
        jButton54.setBounds(1520, 240, 53, 23);

        jButton42.setBackground(new java.awt.Color(204, 204, 204));
        jButton42.setText("Blue");
        getContentPane().add(jButton42);
        jButton42.setBounds(1600, 220, 53, 23);

        jButton41.setBackground(new java.awt.Color(204, 165, 165));
        jButton41.setText("Blue");
        getContentPane().add(jButton41);
        jButton41.setBounds(1600, 250, 53, 23);

        jButton40.setBackground(new java.awt.Color(0, 153, 255));
        jButton40.setText("Blue");
        getContentPane().add(jButton40);
        jButton40.setBounds(1600, 280, 53, 23);

        jButton53.setBackground(new java.awt.Color(204, 204, 0));
        jButton53.setText("Blue");
        getContentPane().add(jButton53);
        jButton53.setBounds(1520, 280, 53, 23);

        jButton39.setBackground(new java.awt.Color(204, 255, 204));
        jButton39.setText("Blue");
        getContentPane().add(jButton39);
        jButton39.setBounds(1590, 300, 53, 23);

        jButton38.setBackground(new java.awt.Color(152, 198, 94));
        jButton38.setText("Blue");
        getContentPane().add(jButton38);
        jButton38.setBounds(1600, 330, 53, 23);

        jButton43.setBackground(new java.awt.Color(255, 204, 204));
        jButton43.setText("jButton5");
        getContentPane().add(jButton43);
        jButton43.setBounds(1460, 310, 73, 23);

        jButton44.setBackground(new java.awt.Color(204, 204, 255));
        jButton44.setText("Blue");
        getContentPane().add(jButton44);
        jButton44.setBounds(1410, 290, 53, 23);

        jButton46.setBackground(new java.awt.Color(255, 204, 153));
        jButton46.setText("Blue");
        getContentPane().add(jButton46);
        jButton46.setBounds(1350, 280, 53, 23);

        jButton47.setBackground(new java.awt.Color(255, 255, 204));
        jButton47.setText("Blue");
        getContentPane().add(jButton47);
        jButton47.setBounds(1350, 250, 53, 23);

        jButton48.setBackground(new java.awt.Color(0, 204, 255));
        jButton48.setText("Blue");
        getContentPane().add(jButton48);
        jButton48.setBounds(1370, 230, 53, 23);

        jButton45.setBackground(new java.awt.Color(201, 222, 223));
        jButton45.setText("Blue");
        getContentPane().add(jButton45);
        jButton45.setBounds(1380, 320, 53, 23);

        jButton35.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton35.setText("jButton5");
        getContentPane().add(jButton35);
        jButton35.setBounds(1420, 390, 73, 23);

        jButton36.setText("jButton8");
        getContentPane().add(jButton36);
        jButton36.setBounds(1550, 390, 73, 23);

        jButton34.setBackground(new java.awt.Color(51, 0, 51));
        jButton34.setText("jButton5");
        getContentPane().add(jButton34);
        jButton34.setBounds(1430, 460, 73, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTree1MouseClicked

    private void jTree1TreeExpanded(javax.swing.event.TreeExpansionEvent evt) {//GEN-FIRST:event_jTree1TreeExpanded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTree1TreeExpanded

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTree1ValueChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
jTextField10.setText(sdf.format(new Date(System.currentTimeMillis()-2500))); 
          db.adminNames(jComboBox1);
        Integer cv=77;
fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "createUpdateAdmin.txt"),cv.toString());
        jLabel23.setVisible(false); 
        jComboBox4.setVisible(false); 
        jButton14 .setVisible(false); 
        jButton9.setEnabled(false);
        jPanel1.setVisible(true);
//            db.adminNames(jComboBox1);
            jLabel18.setVisible(true);
            jComboBox2.setVisible(true);
            jLabel13.setVisible(true);
            jButton4.setVisible(true);
    
       jLabel16.setVisible(false);   
jComboBox3.setVisible(false); 
jLabel21.setVisible(false); 
jPasswordField4.setVisible(false); 
jLabel22.setVisible(false); 
jPasswordField5.setVisible(false); 

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

jComboBox3.setEditable(true);
jComboBox4.setEditable(true); 

jComboBox3.setSelectedItem(dbq.getUserName(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt"))));

jComboBox4.setSelectedItem(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt")));

jButton12.setEnabled(false);
jButton15.setEnabled(false);
jButton3.setEnabled(false);
  jButton31.setEnabled(false);      
        jLabel23.setVisible(true); 
        jComboBox4.setVisible(true); 
        jButton4.setVisible(true); 
        jButton14 .setVisible(true); 
        jButton5.setEnabled(false);
       jButton10 .setVisible(true); 
        jPanel1.setVisible(true); 
        jButton4.setVisible(false);
jTextField16.setVisible(false);
jTextField13.setVisible(false);
jLabel19.setVisible(false);
jTextField11.setVisible(false);
jLabel16.setVisible(false);
jComboBox1.setVisible(false);
 jLabel12.setVisible(false);
jButton6.setVisible(false);
jTextField10.setVisible(false);
jLabel17 .setVisible(false);
jButton11.setVisible(false);
jTextField12.setVisible(false);
jLabel15.setVisible(false);
jTextField9 .setVisible(false);
jLabel14.setVisible(false);
jLabel11.setVisible(false);
jTextField14.setVisible(false);   
    jLabel20  .setVisible(false);  
    jLabel18.setVisible(false);  
    jComboBox2.setVisible(false);
    jLabel13.setVisible(false);
    jButton4.setVisible(false);
     jLabel16.setVisible(true);   
jComboBox3.setVisible(true); 
jLabel21.setVisible(true); 
jPasswordField4.setVisible(true); 
jLabel22.setVisible(true); 
jPasswordField5.setVisible(true);    
jButton8.setVisible(false);
    jButton7 .setVisible(false);
    jButton2.setVisible(false);

//       db.adminNames(jComboBox3); 
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
       List<String> data= new ArrayList();
        if(jTextField12.getText().isEmpty()||jTextField10.getText().isEmpty()){
        JOptionPane.showMessageDialog(this, "Please fillin all the fields and continue");
        return;
        }else{
        
        data.add(jComboBox2.getSelectedItem().toString());
        data.add(jTextField16.getText());
        data.add(jTextField14.getText());
        data.add(jTextField9.getText());
        data.add(jTextField12.getText());
        data.add(jTextField10.getText());
        data.add(jComboBox1.getSelectedItem().toString());
        data.add(jTextField11.getText());
        data.add(jTextField13.getText());
     if(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "createUpdateAdmin.txt"))==77){   
       if( !dbq.createAdmin(data)){
       JOptionPane.showMessageDialog(this, "User Admin was successfully created");
            jTextField16.setVisible(false);         
        jLabel19.setVisible(false);
        jLabel18.setVisible(false);
        jLabel20 .setVisible(false);       
        jButton12.setVisible(true);
         jComboBox2.setVisible(false);
          jTextField14.setVisible(false);
           jTextField9.setVisible(false);
            jTextField12.setVisible(false);
             jTextField10.setVisible(false);
           jTextField11.setVisible(false);
           jTextField13.setVisible(false);
           jLabel11.setVisible(false);
jLabel14.setVisible(false);
jLabel15.setVisible(false);
jLabel17.setVisible(false);
jLabel12.setVisible(false);
jLabel16.setVisible(false);
jLabel13.setVisible(false);
jButton6.setVisible(false);            
           
        
    jButton8.setVisible(false);  
jButton7.setVisible(false);  
jButton2.setVisible(false);  
jComboBox1.setVisible(false);  
jButton10.setVisible(false);  
jButton11.setVisible(false);   
JOptionPane.showMessageDialog(this, "The following User ID was created:"+"  "+dbq.getNewID(data.get(1))+" "+"Please Note it down!!!");
 

CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
        
       } else{
       
       JOptionPane.showMessageDialog(this, "Creation of Admin failed");
       
       }}else if(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "createUpdateAdmin.txt"))==777){
       
       if( !dbq.updateAdmin(data)){
       JOptionPane.showMessageDialog(this, "User Admin was successfully Edited");
      

        CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
        
       } else{
       
       JOptionPane.showMessageDialog(this, "Editing of Admin failed");
       
       }    
           
       
       }
        }
        
        
       
        
        
          
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
      Integer z1=120;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
     Integer z1=121;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
     jButton9.setVisible(true);
         jComboBox2.setSelectedIndex(0);
          jTextField14.setText("");
           jTextField9.setText("");
            jTextField12.setText("");
             jTextField10.setText("");
           jTextField11.setText("");
           jTextField13.setText("");
           jButton4.setVisible(true);
           jTextField16.setVisible(false);
           jComboBox1.setSelectedItem("select");
      dbq.fillMeWithAllAccountsC(AdminAccountTable);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();  
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
  int i=1;
     while(i<=5){
     
     JOptionPane.showMessageDialog(this, "SYSTEM LOG OUT ERROR!!!!!!!!!");
     }
    }//GEN-LAST:event_formWindowClosing

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
jButton8.setVisible(true);
        jButton7.setVisible(true);
        jButton2.setVisible(true);
     
fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "createUpdateAdmin.txt"),"777");
        List balues=dbq.editUsers(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt")));
        jButton3.setEnabled(false);
        jButton15.setEnabled(false);
        jButton31.setEnabled(false);
        jComboBox2.setEnabled(true);
         jComboBox2.setEditable(true);
          jComboBox2.setSelectedItem(balues.get(1));
jTextField16.setText(balues.get(0).toString());
jTextField14.setText(balues.get(2).toString());
jTextField9.setText(balues.get(3).toString());
jTextField12.setText(balues.get(4).toString());
jTextField10.setText(balues.get(5).toString());
 jComboBox1.setEnabled(true);
          jComboBox1.setSelectedItem(balues.get(6));

jTextField11.setText(balues.get(7).toString());
jTextField13.setText(balues.get(8).toString());
        jButton5.setEnabled(false);
        jButton10.setVisible(false);
        jLabel16.setVisible(false);
    jButton4.setVisible(false);
jTextField16.setVisible(true);
jTextField13.setVisible(true);
jLabel19.setVisible(true);
jTextField11.setVisible(true);
jLabel20.setVisible(true);
jComboBox1.setVisible(true);
 jLabel12.setVisible(true);
jButton6.setVisible(true);
jTextField10.setVisible(true);
jLabel17 .setVisible(true);
jButton11.setVisible(true);
jTextField12.setVisible(true);
jLabel15.setVisible(true);
jTextField9 .setVisible(true);
jLabel14.setVisible(true);
jLabel11.setVisible(true);
jTextField14.setVisible(true);
    jPanel1.setVisible(true);
        jLabel23.setVisible(false); 
        jComboBox4.setVisible(false); 
        jButton14 .setVisible(false); 
        jButton9.setEnabled(false);
        jPanel1.setVisible(true);
          
            jLabel18.setVisible(true);
            jComboBox2.setVisible(true);
            jLabel13.setVisible(true);
         
    
       jLabel16.setVisible(false);   
jComboBox3.setVisible(false); 
jLabel21.setVisible(false); 
jPasswordField4.setVisible(false); 
jLabel22.setVisible(false); 
jPasswordField5.setVisible(false); 
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
List<String>passChangeDetails=new ArrayList();
  
        char [] jpa2= jPasswordField4.getPassword();
        String jpassword2=String.valueOf(jpa2);
      passChangeDetails.add(jpassword2);
        char [] jpa3= jPasswordField5.getPassword();
        String jpassword3=String.valueOf(jpa3);
         passChangeDetails.add(jpassword3);
         if(jpassword2.isEmpty()||jpassword3.isEmpty()){
          JOptionPane.showMessageDialog(this, "Please Enter a Valid Password");
          return;
         }else{
   if( jpassword2.equals(jpassword3)){
 passChangeDetails.add(jComboBox4.getSelectedItem().toString());
dbq.createNewPassWord(passChangeDetails);

        }else{
          JOptionPane.showMessageDialog(this, "New passwords don't match");  
          return;
        }}
           CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_jButton10ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        
        String todayStored= fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeDate.txt"));   
             
        String today=sdf.format(new Date(System.currentTimeMillis()));
        
        if(!todayStored.equalsIgnoreCase(today)){
        jButton24.setEnabled(true);
       jButton22.setEnabled(false);
       jButton21.setEnabled(false);
       jButton20.setEnabled(false);

        jButton24.setText("Update Loans");
        }else { 
            
            
            jButton24.setEnabled(false);
    
        
        }
       
        if(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "secondOpenDay.txt"))==87){
                jButton21.setEnabled(false);
       jButton22.setEnabled(true);
       jButton24.setEnabled(false);
       jButton20.setEnabled(false);
        
        }
         if(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "secondOpenDay.txt"))==88){
                jButton24.setEnabled(false);
       jButton22.setEnabled(false);
       jButton21.setEnabled(true);
       jButton20.setEnabled(false);
        
        }
         
         
         if(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "secondOpenDay.txt"))==89){
                jButton21.setEnabled(false);
       jButton22.setEnabled(false);
       jButton24.setEnabled(false);
       jButton20.setEnabled(true);
        
        }
        Resource rsc= new Resource(userId);   
         
        rsc.allComboPostingRights(jComboBox7);
       rsc.allComboAccessRights(jComboBox6);
        updateRoleCombo(jComboBox2) ;
 updateRoleCombo(jComboBox1) ;
 updateRoleCombo(jComboBox8) ;
db.adminNames(jComboBox1);
if(fios.intFileReader(fios.createFileName("accountManagement","adminAccounts", "manageUser.txt"))==77){
 AccountNumberPanel.setVisible(false);
        AdminMainPanel.setVisible(true);
        accessRights.setVisible(false);
}
dbq.fillMeWithAllAdminDetails(adminMainTable);
if(  fios.intFileReader(fios.createFileName("accountManagement","adminAccounts", "backToAdminWindow.txt"))==17){
Integer cctv=77;
fios.intFileWriterReplace(fios.createFileName("accountManagement","adminAccounts", "backToAdminWindow.txt"), cctv.toString());  
 AccountNumberPanel.setVisible(true);
        AdminMainPanel.setVisible(false);
        accessRights.setVisible(false);
jButton4.setVisible(false);
jTextField16.setVisible(true);
jTextField13.setVisible(true);
jLabel19.setVisible(true);
jTextField11.setVisible(true);
jLabel16.setVisible(true);
jComboBox1.setVisible(true);
jLabel12.setVisible(true);
jButton6.setVisible(true);
jTextField10.setVisible(true);
jLabel17 .setVisible(true);
jButton11.setVisible(true);
jTextField12.setVisible(true);
jLabel15.setVisible(true);
jTextField9 .setVisible(true);
jLabel14.setVisible(true);
jLabel11.setVisible(true);
jTextField14.setVisible(true);
 jTextField11.setText("Student");
    jTextField13.setText("Internee");
        }
        
        
        dbq.fillMeWithAllAccountsC(AdminAccountTable);
        
        
        HeaderRenderer header = new HeaderRenderer(adminMainTable.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton9.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton40.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton41.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton42.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton44.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton45.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton47.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton48.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton46.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton47.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton46.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton52.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;  

}
     
        
        
     sortTable(adminMainTable,jTextField22);   

        int n=0;

adminMainTable.setShowHorizontalLines(true);
adminMainTable.setShowGrid(true);
//        adminMainTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<adminMainTable.getColumnModel().getColumnCount()){
        adminMainTable.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        adminMainTable.getColumnModel().getColumn(n).setMinWidth(0);
        adminMainTable.getColumnModel().getColumn(n).setMaxWidth(500);
        adminMainTable.getColumnModel().getColumn(n).setPreferredWidth(150);

        }
        if(n==1){
        adminMainTable.getColumnModel().getColumn(n).setMinWidth(0);
        adminMainTable.getColumnModel().getColumn(n).setMaxWidth(500);
        adminMainTable.getColumnModel().getColumn(n).setPreferredWidth(350);
        }
        if(n==2){
        adminMainTable.getColumnModel().getColumn(n).setMinWidth(0);
        adminMainTable.getColumnModel().getColumn(n).setMaxWidth(500);
        adminMainTable.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==3){
        adminMainTable.getColumnModel().getColumn(n).setMinWidth(0);
        adminMainTable.getColumnModel().getColumn(n).setMaxWidth(500);
        adminMainTable.getColumnModel().getColumn(n).setPreferredWidth(300);
        }
        if(n==4){
        adminMainTable.getColumnModel().getColumn(n).setMinWidth(0);
        adminMainTable.getColumnModel().getColumn(n).setMaxWidth(500);
        adminMainTable.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        n++;

        }
        adminMainTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
//if(col==2){
// this.setHorizontalAlignment(RIGHT);
//
//}
//        if(col==4){
//        Number c = (Number)parseDouble(value.toString());
//        String text = NumberFormat.fmt(c );
//        this.setText(text);
//       this.setHorizontalAlignment(RIGHT);
//        }else{
//        this.setText(value.toString());
//        }

        this.setText(value.toString());
        if (row%2==0) {
        setBackground(jButton36.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton35.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });    
        
     HeaderRenderer header2 = new HeaderRenderer(AdminAccountTable.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton9.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton40.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton41.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton42.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton44.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton45.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton47.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton48.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton46.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton47.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton46.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton52.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;  

}
     
        
        
     sortTable(AdminAccountTable,jTextField21);   

        int n2=0;

AdminAccountTable.setShowHorizontalLines(true);
AdminAccountTable.setShowGrid(true);
//        adminMainTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n2<AdminAccountTable.getColumnModel().getColumnCount()){
        AdminAccountTable.getColumnModel().getColumn(n2).setHeaderRenderer(header2);

        if(n2==0){
        AdminAccountTable.getColumnModel().getColumn(n2).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n2).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n2).setPreferredWidth(150);

        }
        if(n2==1){
        AdminAccountTable.getColumnModel().getColumn(n2).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n2).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n2).setPreferredWidth(350);
        }
        if(n2==2){
        AdminAccountTable.getColumnModel().getColumn(n2).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n2).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n2).setPreferredWidth(250);
        }
        if(n2==3){
        AdminAccountTable.getColumnModel().getColumn(n2).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n2).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n2).setPreferredWidth(300);
        }
        if(n==4){
        AdminAccountTable.getColumnModel().getColumn(n2).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n2).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n2).setPreferredWidth(250);
        }
        n2++;

        }
        AdminAccountTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
//if(col==2){
// this.setHorizontalAlignment(RIGHT);
//
//}
//        if(col==4){
//        Number c = (Number)parseDouble(value.toString());
//        String text = NumberFormat.fmt(c );
//        this.setText(text);
//       this.setHorizontalAlignment(RIGHT);
//        }else{
//        this.setText(value.toString());
//        }

        this.setText(value.toString());
        if (row%2==0) {
        setBackground(jButton36.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton35.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });      
    }//GEN-LAST:event_formWindowOpened
private void updateRoleCombo(JComboBox box){
        int v=0; ArrayList <String> userRoles=new ArrayList();
       
        String [] roles=fios.stringFileReader(fios.createFileName("resources", "roles", "role1.txt")).split("[;]");     
        
        while(v<=roles.length-1){if(v==roles.length-1){userRoles.add(roles[v].replace(";", ""));}else{ userRoles.add(roles[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userRoles);
       
        box.setModel(modelcombo); 
}
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
         Integer cc=7;
        fios.intFileWriterReplace(fios.createFileName("accountManagement","adminAccounts", "fromAdminWindow.txt"), cc.toString());
         OperationsModule fo15 = new OperationsModule(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void AdminAccountTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdminAccountTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminAccountTableMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
     
       
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
//    dbq.getUserId(jComboBox4,jComboBox3.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();  
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        AccountNumberPanel.setVisible(true);
        AdminMainPanel.setVisible(false);
        accessRights.setVisible(false);
          HeaderRenderer header = new HeaderRenderer(AdminAccountTable.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton9.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton40.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton41.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton42.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton44.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton45.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton47.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton48.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton46.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton47.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton46.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton52.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;  

}
     
        
        
     sortTable(AdminAccountTable,jTextField21);   

        int n=0;

AdminAccountTable.setShowHorizontalLines(true);
AdminAccountTable.setShowGrid(true);
//        adminMainTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<AdminAccountTable.getColumnModel().getColumnCount()){
        AdminAccountTable.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        AdminAccountTable.getColumnModel().getColumn(n).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n).setPreferredWidth(150);

        }
        if(n==1){
        AdminAccountTable.getColumnModel().getColumn(n).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n).setPreferredWidth(350);
        }
        if(n==2){
        AdminAccountTable.getColumnModel().getColumn(n).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==3){
        AdminAccountTable.getColumnModel().getColumn(n).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n).setPreferredWidth(300);
        }
        if(n==4){
        AdminAccountTable.getColumnModel().getColumn(n).setMinWidth(0);
        AdminAccountTable.getColumnModel().getColumn(n).setMaxWidth(500);
        AdminAccountTable.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        n++;

        }
        AdminAccountTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
//if(col==2){
// this.setHorizontalAlignment(RIGHT);
//
//}
//        if(col==4){
//        Number c = (Number)parseDouble(value.toString());
//        String text = NumberFormat.fmt(c );
//        this.setText(text);
//       this.setHorizontalAlignment(RIGHT);
//        }else{
//        this.setText(value.toString());
//        }

        this.setText(value.toString());
        if (row%2==0) {
        setBackground(jButton36.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton35.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });    
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      jButton9.setEnabled(false);
              jButton12.setEnabled(false);
                  jButton15.setEnabled(false); 
                   jButton31.setEnabled(false);
        jButton12.setEnabled(false);
     
       
        jButton5.setEnabled(false);
  
        jButton4.setVisible(false);
jTextField16.setVisible(false);
jTextField13.setVisible(false);
jLabel19.setVisible(false);
jTextField11.setVisible(false);
jLabel16.setVisible(false);
jComboBox1.setVisible(false);
 jLabel12.setVisible(false);
jButton6.setVisible(false);
jTextField10.setVisible(false);
jLabel17 .setVisible(false);
jButton11.setVisible(false);
jTextField12.setVisible(false);
jLabel15.setVisible(false);
jTextField9 .setVisible(false);
jLabel14.setVisible(false);
jLabel11.setVisible(false);
jTextField14.setVisible(false);   
    jLabel20  .setVisible(false);  
    jLabel18.setVisible(false);  
    jComboBox2.setVisible(false);
    jLabel13.setVisible(false);
    jButton4.setVisible(false);
      
jButton8.setVisible(false);
    jButton7 .setVisible(false);
    jButton2.setVisible(false);
    
     Object[] optionsSF = {"Continue",  "Cancel"};
    int nSF = JOptionPane.showOptionDialog(this,  "Are you sure you want to delete"+" "+ dbq.getUserName(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt")))+" "+" from the list of Admins?",
    "DECIDE TO DELETE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSF, optionsSF[0]);
    if(nSF==JOptionPane.YES_OPTION){
        
       if(!dbq.deleteUserId(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt")))){
        JOptionPane.showMessageDialog(this, "The user was successfully deleted");
       }else{
         JOptionPane.showMessageDialog(this, "Deletion of the user failed");
       }
        CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
    else if (nSF==JOptionPane.NO_OPTION){ 
        
          CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
      jButton9.setEnabled(false);
              jButton12.setEnabled(false);
                  jButton15.setEnabled(true);       
        jButton12.setEnabled(false);
     
       
        jButton5.setEnabled(false);
   jButton3.setVisible(false);
        jButton4.setVisible(false);
jTextField16.setVisible(false);
jTextField13.setVisible(false);
jLabel19.setVisible(false);
jTextField11.setVisible(false);
jLabel16.setVisible(false);
jComboBox1.setVisible(false);
 jLabel12.setVisible(false);
jButton6.setVisible(false);
jTextField10.setVisible(false);
jLabel17 .setVisible(false);
jButton11.setVisible(false);
jTextField12.setVisible(false);
jLabel15.setVisible(false);
jTextField9 .setVisible(false);
jLabel14.setVisible(false);
jLabel11.setVisible(false);
jTextField14.setVisible(false);   
    jLabel20  .setVisible(false);  
    jLabel18.setVisible(false);  
    jComboBox2.setVisible(false);
    jLabel13.setVisible(false);
    jButton4.setVisible(false);
      
jButton8.setVisible(false);
    jButton7 .setVisible(false);
    jButton2.setVisible(false);
    
      Object[] optionsSF = {"Continue",  "Cancel"};
      int nSF=0;
      if(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "rights"+fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt"))+".txt"))==98){
    nSF = JOptionPane.showOptionDialog(this,  "Are you sure you want to grant full officer rights"+" "+ dbq.getUserName(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt")))+" "+" from the list of Admins?",
    "DECIDE TO DELETE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSF, optionsSF[0]);
      }else{
     nSF = JOptionPane.showOptionDialog(this,  "Are you sure you want to restrict full officer rights"+" "+ dbq.getUserName(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt")))+" "+" from the list of Admins?",
    "DECIDE TO DELETE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSF, optionsSF[0]); 
      }
    
    if(nSF==JOptionPane.YES_OPTION){
        
       if(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "rights"+fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt"))+".txt"))==98){
        JOptionPane.showMessageDialog(this, "The user was successfully granted full officer rights");
        Integer bc=75;
        fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "rights"+fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt"))+".txt"),bc.toString());
       }else{
         JOptionPane.showMessageDialog(this, "The user was successfully restricted from full officer rights");
        Integer bc=98;
        fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "rights"+fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt"))+".txt"),bc.toString()); 
       }
        CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
    else if (nSF==JOptionPane.NO_OPTION){ 
        
          CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//   userId  f.setUserID(userId);
        this.dispose();
    }
    
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
jTextField15.setVisible(true);
jTextField15.setEditable(false);
jTextField15.setText(jComboBox8.getSelectedItem().toString());
  Resource rsc= new Resource(userId);
        switch(jComboBox8.getSelectedItem().toString()){
       
       case "Manager":
   rsc. displayAccessRights(jComboBox11,"Manager");
       rsc. displayPostingRights(jComboBox5,"Manager");
    break;
     case "DateStarter":
   rsc. displayAccessRights(jComboBox11,"DateStarter");
       rsc. displayPostingRights(jComboBox5,"DateStarter");
    break;
case "Supervisor":
     rsc. displayAccessRights(jComboBox11,"Supervisor");  
        rsc. displayPostingRights(jComboBox5,"Supervisor");  
    break;
case "Accountant":
     rsc. displayAccessRights(jComboBox11,"Accountant"); 
     
      rsc. displayPostingRights(jComboBox5,"Accountant"); 
    break;
case "Cashier":
  rsc. displayAccessRights(jComboBox11,"Cashier");  
          rsc. displayPostingRights(jComboBox5,"Cashier"); 
    break;
case "Loans Officer":
     rsc. displayAccessRights(jComboBox11,"Loans Officer");  
       rsc. displayPostingRights(jComboBox5,"Loans Officer");  
    break;
case "System Admin":
     rsc. displayAccessRights(jComboBox11,"System Admin"); 
      rsc. displayPostingRights(jComboBox5,"System Admin"); 
    break;
 default:
     
     rsc.displayAccessRights(jComboBox11, "googobazimariaterisabazirake2015");
      rsc. displayPostingRights(jComboBox5,"System Admin"); 
           break;
   
   
   }
     jComboBox8.setVisible(false);
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
 if(!jTextField15.isVisible()){
       
       JOptionPane.showMessageDialog(this, "Please Select At Least One User Group To Grant Access Rights");
    
       }else{ 
     if(!jTextField17.isVisible()){
     
         JOptionPane.showMessageDialog(this, "Please Select At Least One  Access Rights To Grant To User");
     }else{
     
       Resource rsc= new Resource(userId);
        switch(jTextField15.getText()){
            
        case "Manager":
            if(rsc.accessRightAlreadyExists(jComboBox6.getSelectedItem().toString(), "Manager")){
                JOptionPane.showMessageDialog(this, "Access Right Already Exists!!!!");
          
            }else{
            if(rsc.grantAccessRights(jComboBox6.getSelectedItem().toString(), "Manager")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            
            }
    break;
    case "DateStarter":
            if(rsc.accessRightAlreadyExists(jComboBox6.getSelectedItem().toString(), "DateStarter")){
                JOptionPane.showMessageDialog(this, "Access Right Already Exists!!!!");
          
            }else{
            if(rsc.grantAccessRights(jComboBox6.getSelectedItem().toString(), "DateStarter")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            
            }
    break;
case "Supervisor":
     if(rsc.accessRightAlreadyExists(jComboBox6.getSelectedItem().toString(), "Supervisor")){
                JOptionPane.showMessageDialog(this, "Access Right Already Exists!!!!");
              
            }else{
         if(rsc.grantAccessRights(jComboBox6.getSelectedItem().toString(), "Supervisor")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
         
            
            }
    break;
case "Accountant":
     if(rsc.accessRightAlreadyExists(jComboBox6.getSelectedItem().toString(), "Accountant")){
                JOptionPane.showMessageDialog(this, "Access Right Already Exists!!!!");
             
            }else{
        
               if(rsc.grantAccessRights(jComboBox6.getSelectedItem().toString(), "Accountant")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            }
    break;
case "Cashier":
      if(rsc.accessRightAlreadyExists(jComboBox6.getSelectedItem().toString(), "Cashier")){
                JOptionPane.showMessageDialog(this, "Access Right Already Exists!!!!");
              
            }else{
      
               if(rsc.grantAccessRights(jComboBox6.getSelectedItem().toString(), "Cashier")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            }
    break;
case "Loans Officer":
     if(rsc.accessRightAlreadyExists(jComboBox6.getSelectedItem().toString(), "Loans Officer")){
                JOptionPane.showMessageDialog(this, "Access Right Already Exists!!!!");
               
            }else{
             if(rsc.grantAccessRights(jComboBox6.getSelectedItem().toString(), "Loans Officer")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
     
            
            }  
    break;
case "System Admin":
     if(rsc.accessRightAlreadyExists(jComboBox6.getSelectedItem().toString(), "System Admin")){
                JOptionPane.showMessageDialog(this, "Access Right Already Exists!!!!");
               
            }else{
          
             if(rsc.grantAccessRights(jComboBox6.getSelectedItem().toString(), "System Admin")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            } 
    break;
        }
      
      }}
 
            jComboBox8.setVisible(true);
            jTextField15.setVisible(false);
            jTextField17.setVisible(false);
            jComboBox6.setVisible(true);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
  Resource rsc= new Resource(userId);
        if(!jTextField15.isVisible()){
       
       JOptionPane.showMessageDialog(this, "Please Select At Least One User Group To Revoke Access Rights");
      
       }else{ 
          if(!jTextField19.isVisible()){
     
         JOptionPane.showMessageDialog(this, "Please Select At Least One  Access Rights To Revoke From User");
     }else{
        switch(jTextField15.getText()){
        case "Manager":
            if(rsc.revokeAccessRights(jComboBox11.getSelectedItem().toString(), "Manager")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
    break;
     case "DateStarter":
            if(rsc.revokeAccessRights(jComboBox11.getSelectedItem().toString(), "DateStarter")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
    break;
    
case "Supervisor":
    
        if(rsc.revokeAccessRights(jComboBox11.getSelectedItem().toString(), "Supervisor")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
     
    break;
case "Accountant":
    
      if(rsc.revokeAccessRights(jComboBox11.getSelectedItem().toString(), "Accountant")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }

    break;
case "Cashier":
    
       if(rsc.revokeAccessRights(jComboBox11.getSelectedItem().toString(), "Cashier")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }

    break;
case "Loans Officer":
    
        if(rsc.revokeAccessRights(jComboBox11.getSelectedItem().toString(), "Loans Officer")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
 
    break;
case "System Admin":
    
        if(rsc.revokeAccessRights(jComboBox11.getSelectedItem().toString(), "System Admin")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
    break;
      
      
      }}}
    jComboBox8.setVisible(true);
         jTextField15.setVisible(false);
        
            jTextField19.setVisible(false);
            jComboBox11.setVisible(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
         Resource rsc= new Resource(userId);  
    if(!jTextField15.isVisible()){
       
       JOptionPane.showMessageDialog(this, "Please Select At Least One User Group To Grant Access Rights");
     
       }else{ 
          if(!jTextField18.isVisible()){
     
         JOptionPane.showMessageDialog(this, "Please Select At Least One  Posting Rights To Grant To User");
     }else{
        switch(jTextField15.getText()){
        case "Manager":
            if(rsc.postingRightAlreadyExists(jComboBox7.getSelectedItem().toString(), "Manager")){
                JOptionPane.showMessageDialog(this, "Posting Right Already Exists!!!!");
             
            }else{
            if(rsc.grantpostingRights(jComboBox7.getSelectedItem().toString(), "Manager")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            
            }
    break;
case "Supervisor":
     if(rsc.postingRightAlreadyExists(jComboBox7.getSelectedItem().toString(), "Supervisor")){
                JOptionPane.showMessageDialog(this, "Posting Right Already Exists!!!!");
               
            }else{
         if(rsc.grantpostingRights(jComboBox7.getSelectedItem().toString(), "Supervisor")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
         
            
            }
    break;
case "Accountant":
     if(rsc.postingRightAlreadyExists(jComboBox7.getSelectedItem().toString(), "Accountant")){
                JOptionPane.showMessageDialog(this, "Posting Right Already Exists!!!!");
               
            }else{
        
               if(rsc.grantpostingRights(jComboBox7.getSelectedItem().toString(), "Accountant")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            }
    break;
case "Cashier":
      if(rsc.postingRightAlreadyExists(jComboBox7.getSelectedItem().toString(), "Cashier")){
                JOptionPane.showMessageDialog(this, "Posting Right Already Exists!!!!");
              
            }else{
      
               if(rsc.grantpostingRights(jComboBox7.getSelectedItem().toString(), "Cashier")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            }
    break;
case "Loans Officer":
     if(rsc.postingRightAlreadyExists(jComboBox7.getSelectedItem().toString(), "Loans Officer")){
                JOptionPane.showMessageDialog(this, "Posting Right Already Exists!!!!");
              
            }else{
             if(rsc.grantpostingRights(jComboBox7.getSelectedItem().toString(), "Loans Officer")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
     
            
            }  
    break;
case "System Admin":
     if(rsc.postingRightAlreadyExists(jComboBox7.getSelectedItem().toString(), "System Admin")){
                JOptionPane.showMessageDialog(this, "Posting Right Already Exists!!!!");
             
            }else{
          
             if(rsc.grantpostingRights(jComboBox7.getSelectedItem().toString(), "System Admin")){
            
            JOptionPane.showMessageDialog(this, "Granted Successfully");
            }
            } 
    break;
      
      
      }}}
     jComboBox8.setVisible(true);
         jTextField15.setVisible(false);
            jTextField18.setVisible(false);
            jComboBox7.setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
  Resource rsc= new Resource(userId);
        if(!jTextField15.isVisible()){
       
       JOptionPane.showMessageDialog(this, "Please Select At Least One User Group To Revoke Posting Rights");
       return;
       }else{ 
         if(!jTextField20.isVisible()){
     
         JOptionPane.showMessageDialog(this, "Please Select At Least One  Posting Rights To Revoke From User");
     }else{
        switch(jTextField15.getText()){
        case "Manager":
            if(rsc.revokepostingRights(jComboBox5.getSelectedItem().toString(), "Manager")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
    break;
case "Supervisor":
    
        if(rsc.revokepostingRights(jComboBox5.getSelectedItem().toString(), "Supervisor")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
     
    break;
case "Accountant":
         if(rsc.revokepostingRights(jComboBox5.getSelectedItem().toString(), "Accountant")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
    
    
     break;
case "Cashier":
        if(rsc.revokepostingRights(jComboBox5.getSelectedItem().toString(), "Cashier")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
  
    break;
case "Loans Officer":
    
      if(rsc.revokepostingRights(jComboBox5.getSelectedItem().toString(), "Loans Officer")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
       break;
case "System Admin":
    
      if(rsc.revokepostingRights(jComboBox5.getSelectedItem().toString(), "System Admin")){
                JOptionPane.showMessageDialog(this, "Revoked successfully");
            
            }else{
            
            
            JOptionPane.showMessageDialog(this, "Revoke Failed");
            
            
            }
    break;
      
      
      }}}

  jComboBox8.setVisible(true);
         jTextField15.setVisible(false);
            jTextField20.setVisible(false);
            jComboBox5.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
    jTextField17.setVisible(true);
    jTextField17.setEditable(false);
    jTextField17.setText(jComboBox6.getSelectedItem().toString());
    jComboBox6.setVisible(false);
     jComboBox8.setVisible(false);
    jTextField15.setVisible(true);
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
      jTextField18.setVisible(true);
    jTextField18.setEditable(false);
    jTextField18.setText(jComboBox7.getSelectedItem().toString());
    jComboBox7.setVisible(false);
    jComboBox8.setVisible(false);
    jTextField15.setVisible(true);
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox11ActionPerformed
       jTextField19.setVisible(true);
    jTextField19.setEditable(false);
    jTextField19.setText(jComboBox11.getSelectedItem().toString());
    jComboBox11.setVisible(false);
//    jComboBox8.setVisible(false);
//    jTextField15.setVisible(true);
    }//GEN-LAST:event_jComboBox11ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
     jTextField20.setVisible(true);
    jTextField20.setEditable(false);
    jTextField20.setText(jComboBox5.getSelectedItem().toString());
    jComboBox5.setVisible(false);
//    jComboBox8.setVisible(false);
//    jTextField15.setVisible(true);
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
//    SwingWorker<Void,Void> backUpTheDataBasePmmsLoans1 = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        dbBackup.backUpTheDataBase("pmms_loans1");
//          try {
//    wait(1000);
//    } catch (InterruptedException ex) {
//    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
//    }
//
//
//        return null;   
//        } };
//
//        backUpTheDataBasePmmsLoans1.execute();
        
        
        
//        
//         SwingWorker<Void,Void> updateInterest = new SwingWorker(){     
//        @Override
//        protected Object doInBackground() throws Exception {
//            
//       loan.updateInterestRepaymentsInterest(CreateNewStaff.this); 
// 
//        
//        return null;
//        } };
//        updateInterest.execute();
//        
        
        
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "ManagerLoggedIn.txt"), "49");   

        updateLoans initialUpdate=new updateLoans(userId);

        fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "secondOpenDay.txt"), "89"); 

     

        String todayStored= fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeDate.txt"));   

        String today=sdf.format(new Date(System.currentTimeMillis()));

        if(todayStored.equalsIgnoreCase(today)){
            
        jButton21.setEnabled(false);
        
        jButton22.setEnabled(true);

        JOptionPane.showMessageDialog(this, "System updates were successfully done");

        fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "activeDate.txt"),today);

        CreateNewStaff f = new CreateNewStaff(userId);
        
        f.setVisible(true);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        f.setSize(screen.getSize());
        
        f.pack();

        this.dispose(); 
        } else{

        SwingWorker<Void,Void> processAtPerfo = new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {
        List theAccountLoansWriteOff=  loan.updateLoanDueWriteOff();
        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "InstalmentInArrears.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
        initialUpdate.processSmsRemindersForLoanPaymentArrears(CreateNewStaff.this);
        }       
        //                   if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "BirthDayDue.txt"))==7){
        //  
        //                  initialUpdate.processSmsBirthDay();
        //  
        //}
        if(!theAccountLoansWriteOff.isEmpty()){
        initialUpdate.dueWriteOff(theAccountLoansWriteOff);
        }
        if(cal.get(GregorianCalendar.YEAR)>fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "timeYear.txt"))){
        initialUpdate.createIndividualPostingNames();
        fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "timeYear.txt"),cal.get(GregorianCalendar.YEAR)+""); 
        }else  if(cal.get(GregorianCalendar.MONTH)>fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "timeMonth.txt"))){
        initialUpdate.createIndividualPostingNames();
        fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "timeMonth.txt"),cal.get(GregorianCalendar.MONTH)+""); 
        }
        return null;
        }};
        
        processAtPerfo.execute();
   
        String companyName=fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "shortCompanyName.txt"));
        
        if(companyName.split("[;]", 2)[0].equalsIgnoreCase("HEF")){
        
        SwingWorker<Void,Void> customerShares = new SwingWorker(){     
        @Override
        protected Object doInBackground() throws Exception {
            
        List listOfCustomerAccounts=  dbq.accountNumbersCustomers(); 
        
        initialUpdate.processReturnOnInvestments(listOfCustomerAccounts,CreateNewStaff.this);
        
        return null;
        } };
        customerShares.execute();
        }
        fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "activeDate.txt"),today);  

        JOptionPane.showMessageDialog(this, "System updates were successfully done");
        
        jButton21.setEnabled(false);
        
        jButton22.setEnabled(true);
        
        }

         if(fios.intFileReader(fios.createFileName("accountManagement", "createAccounts", "automaticDepreciation"))==23){
        
       initialUpdate.processAccumulatedDepreciation(this);
        
         }
        
        CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose(); 



    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed

//           
//             SwingWorker<Void,Void> backUpTheDataBasePmmsLoans = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        dbBackup.backUpTheDataBase("pmms_loans");
//              try {
//    wait(1000);
//    } catch (InterruptedException ex) {
//    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
//    }
//
//        return null;   
//        } };
//
//
//        backUpTheDataBasePmmsLoans.execute();
        
         
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "ManagerLoggedIn.txt"), "49");
        
            

        updateLoans initialUpdate=new updateLoans(userId);
//           try {
//    wait(1000);
//    } catch (InterruptedException ex) {
//    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
//    }

      
        
        String todayStored= fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeDate2.txt"));   
             
        String todayb=sdf.format(new Date(System.currentTimeMillis()));      
         
        if(todayStored.equalsIgnoreCase(todayb)){     
        
        JOptionPane.showMessageDialog(this, "Accounts updates were successfully done");
        
        fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "activeDate2.txt"),todayb); 
        
        fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "secondOpenDay.txt"), "88");
        
        CreateNewStaff fb = new CreateNewStaff(userId);
        fb.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fb.setSize(screen.getSize());
        fb.pack();
        //        f.setUserID(userId);
        this.dispose(); 
           
        } else{
            
//              SwingWorker<Void,Void> updateTheLoansNow = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
            loan.updateDailyReport();
//               dbq.initialiseDailyStats();
               
//                 return null;   
//        } };
//
//
//        updateTheLoansNow.execute();
//        
            
        SwingWorker<Void,Void> processAtPerforming = new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {
            
        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "TopUpLoan.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
        
            initialUpdate.processSmsRemindersForLoanPaymentTomorrow(CreateNewStaff.this);

        } 

        List theAccountsportfolio=  loan.updateOutStandingAcof();

        List theAccountsperformingportfolio=  loan.updatePerformingLoanPoert();

        if(!theAccountsportfolio.isEmpty()){

        initialUpdate.updateLoanPort(theAccountsportfolio);

        }

        if(!theAccountsperformingportfolio.isEmpty()){

        initialUpdate.updateLoanPortPerfroming(theAccountsperformingportfolio);
        }




        return null;
        }
        };
        processAtPerforming.execute();
   
        if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWindowChecked.txt"))==7){    

if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "NumberProvisionedWriteOff.txt"))>0){    
        
//    SwingWorker<Void,Void> processProvisioninWriteOff = new SwingWorker(){
//    @Override
//    protected Object doInBackground() throws Exception {

    List theAccountsportfolioAtRisk=  loan.updateOutStandingAcof3();

    if(!theAccountsportfolioAtRisk.isEmpty()){

    if( initialUpdate.processProvisionWritteOff(theAccountsportfolioAtRisk)){

    postinAccrualsn1.completeProvisioning();
    
    }
    }

//    return null;
//    }
//    };
//    
//   processProvisioninWriteOff.execute();
   
}
}

    JOptionPane.showMessageDialog(this, "Accounts updates were successfully done"); 
    fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "activeDate2.txt"),todayb);  
    fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "secondOpenDay.txt"), "88");
    CreateNewStaff f = new CreateNewStaff(userId);
    f.setVisible(true);
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    f.setSize(screen.getSize());
    f.pack();
    //        f.setUserID(userId);
    this.dispose(); 

    }
        
      
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed

        if(dbq.testTable("bsanca01128000210")){
        if(parseInt(loan.getTheRenewalDetails(this).get(0).toString())==1){
        
         new  BatchPosting((Component)this,userId).renewLoansNow(this);
        
        }
        }else{
        JOptionPane.showMessageDialog(this, "Please first set the renewal loan account 01128000210");
        return;
        }
        
//        
//        
//            SwingWorker<Void,Void> backUpTheDataBasePmms1 = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        dbBackup.backUpTheDataBase("pmms1");
//  try {
//    wait(1000);
//    } catch (InterruptedException ex) {
//    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
//    }
//
//        return null;   
//        } };
//
//        backUpTheDataBasePmms1.execute();
//        
        
        
    
           
        
        
        JOptionPane.showMessageDialog(this, "Start of day successfully done");
        
        
         
    
   
        CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
        
   fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "secondOpenDay.txt"), "0");
   fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "open_day.txt"),sdf.format(new Date(System.currentTimeMillis())));
//fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "trn_sequeanceNumber.txt"),"0001");

dbq.createNewSequenceNumber();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed

        
//          
//             SwingWorker<Void,Void> backUpTheDataBasePmms = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        dbBackup.backUpTheDataBase("pmms");
//        
//          try {
//    wait(1000);
//    } catch (InterruptedException ex) {
//    Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
//    }
//
//
//        return null;   
//        } };
//
//
//        backUpTheDataBasePmms.execute();
          
       
        
        
        
        
        
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "changeValueStartTrial.txt"), "77");
        
        updateLoans initialUpdate=new updateLoans(userId);
//                    initialUpdate. processTheActualPenaltyLoan();
// initialUpdate.updateLoanPenalties();

 fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "ManagerLoggedIn.txt"), "49");
 
       
             String todayStored= fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeDate3.txt"));   
          
             Date stored= fmt.convertTdate(todayStored); 
             
           long Olddate=stored.getTime();
           
        String todayx=sdf.format(new Date(System.currentTimeMillis()));
//        
//        JOptionPane.showMessageDialog(this, "SystemDate  "+System.currentTimeMillis());
//        
//         JOptionPane.showMessageDialog(this, "OldDate  "+Olddate);
         
          if(System.currentTimeMillis()>Olddate){
              
            fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "activeDate3New.txt"), fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeDate3.txt")));
        
        
       
            fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "secondOpenDay.txt"), "87");  
      
             if(!todayStored.equalsIgnoreCase(todayx)){
              
        SwingWorker<Void,Void> processAtRisk = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                
                
     
                 
              if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "InstalmentDue.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
  
                  initialUpdate.processSmsRemindersForLoanPaymentToday(CreateNewStaff.this);
  
}                 

            List theAccountsportfolioAtRisk=  loan.updateLoanPoertAtRisk(CreateNewStaff.this);
            
                if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesOnArrears.txt"))==1){//A decision on whther the charges will be on the instalment, principal, interest or the wholw loan amount
                
                    if(fios.intFileReader( fios.createFileName("persistence", "loanArrears", "setChargesOnTheXy.txt"))==1){
                   
                        if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesOnArrearsOthersY.txt"))==1){//Decesion on whether to set charges as either a percentage, fraction or single figure was made
           
                            loan.updateOutStandingAco();//Puts the accounts considered,i.e the ones disbursed in the file
            
            if(!fios.stringFileReader(fios.createFileName("loanRepayment", "updateArrears", "accountsConsidered")).equalsIgnoreCase("1")){

 SwingWorker<Void,Void> updateLoanPenalties = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

//         initialUpdate.updateLoanPenalties(CreateNewStaff.this);
        
//   initialUpdate. processTheActualPenaltyLoan(CreateNewStaff.this); 
   
        return null;   
        } };


        updateLoanPenalties.execute();
               
  
                  
                    
                    fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "activeDate3.txt"),todayx);
                    
                
                
                
                if(!theAccountsportfolioAtRisk.isEmpty()){
                    
                    initialUpdate.arrears(theAccountsportfolioAtRisk);
                    
                      SwingWorker<Void,Void> updateLoanPortAtRisk = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        initialUpdate.updateLoanPortAtRisk(theAccountsportfolioAtRisk);

        return null;   
        } };


        updateLoanPortAtRisk.execute();
                    
                }   
            }
                } }}
                
       

 return null;   
        } };


        processAtRisk.execute();       
    
                
                
                
        
//                 JOptionPane.showMessageDialog(CreateNewStaff.this, "INNNNNNNNNNNNNN");
                
            fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "activeDate3.txt"),todayx);
        } 
            
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
//
//SwingWorker<Void,Void> accrueInterest = new SwingWorker() {
//@Override
//protected Object doInBackground() throws Exception {
//
//List potentialAccounts=loan.updateOutStandingAcof();
//
//
////fios.stringFileWriter(fios.createFileName("test", "testit", potentialAccounts.size()+"trdyr.txt"),potentialAccounts.size()+"ddddddddd");
//
//initialUpdate.accrueInterestProcessNow(potentialAccounts);
//
//
//return null;    
//
//}
//};
//
//
//accrueInterest.execute();
//
//}
       
        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useAccumulatedInterest.txt"))==1){//If the entity allows the use of accumulated interest where by it charges an interest on the remaining principal when the instalment due is not cleared of the principal payment
          boolean completed=false;
        List potentialAccounts7=loan.updateOutStandingAcof();//"SELECT applicant_account_number FROM  new_loan_appstore WHERE loan_cycle_status ="+"'"+"Disbursed"+"'";

        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "accumulateOnce.txt"))==12){///If the entity charges the accumulated interest once, meaning only the first time when the princimpal falls due and is not cleared. This implies that if in the subsquent instalment due dates the princimpal amount is still outstanding, the entity will NOT charge any further interest on the outstaning principal amount

        for(Object accountNumber:potentialAccounts7){//Iterate through the ledger ids picked one by one and process for accumulated interest if the ledger has pricimpal that is outstanding for the first time

        SwingWorker<Void,Void> accumulatedInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        initialUpdate.processAccumulatedInterestNormal(accountNumber.toString(),"Once");//Move to the next step

        return null;   
        } };


        accumulatedInterest.execute();
        }
completed=true;
        }

        else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "recycleAccumulatedInterest.txt"))==12){

        for(Object accountNumber:potentialAccounts7){

        SwingWorker<Void,Void> accumulatedInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        initialUpdate.processAccumulatedInterestNormal(accountNumber.toString(),"Recycle");


        return null;   
        } };


        accumulatedInterest.execute();

        }
completed=true;

        }
          
        
     
            
           
            
        SwingWorker<Void,Void> processTheActualAccumulatedInterestaccumulatedInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        it();//When the acculation of the interest is completed in the store, proceed to further processing

        return null;   
        } };


        processTheActualAccumulatedInterestaccumulatedInterest.execute();

        

        }

        JOptionPane.showMessageDialog(this, "Loan updates were successfully done");


        CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        //        f.setUserID(userId);
        this.dispose(); 
    }  else{
        
        JOptionPane.showMessageDialog(CreateNewStaff.this, "This date has been locked out of processing, Please Open a new day ahead!!");
        
        }
 
    }//GEN-LAST:event_jButton24ActionPerformed
private synchronized void it(){
 updateLoans initialUpdate=new updateLoans(userId);
            try {
        wait(12000);
        } catch (InterruptedException ex) {
        Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
        }
SwingWorker<Void,Void> processTheActualAccumulatedInterestaccumulatedInterest = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {

            initialUpdate.processTheActualAccumulatedInterest();//When the acculation of the interest is completed in the store, proceed to further processing

            return null;   
            } };


            processTheActualAccumulatedInterestaccumulatedInterest.execute();


}
    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
       try {
             adminMainTable.print();
         } catch (PrinterException ex) {
             Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
      List<List>  d=new ArrayList();  List c=new ArrayList();
        
//        if(signal.equalsIgnoreCase("CustomerStatment")){
        ObjectTableModel original = (ObjectTableModel)adminMainTable.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
    d.add(original.getRow(targetRow));
    }
}
        
        
//        }else{
         
            
//        ListDataModel original = (ListDataModel)adminMainTable.getModel();
//        
//for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}
//
//d.add(c);
//
//for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
//    if(!original.getRow(targetRow).isEmpty()){
//    d.add(original.getRow(targetRow));
//    }
//}
//}
   String theCounter=getCounter();     
        
writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
      
                 try {
             AdminAccountTable.print();
         } catch (PrinterException ex) {
             Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
    
            
             List<List>  d=new ArrayList();  List c=new ArrayList();
        
//        if(signal.equalsIgnoreCase("CustomerStatment")){
        ObjectTableModel original = (ObjectTableModel)AdminAccountTable.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
    d.add(original.getRow(targetRow));
    }
}
        
        
//        }else{
         
            
//        ListDataModel original = (ListDataModel)adminMainTable.getModel();
//        
//for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}
//
//d.add(c);
//
//for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
//    if(!original.getRow(targetRow).isEmpty()){
//    d.add(original.getRow(targetRow));
//    }
//}
//}


   String theCounter=getCounter();     
        
writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jTextField23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23ActionPerformed

    private void jTextField23FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField23FocusGained
      jTextField23.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField23FocusGained

    private void jTextField24FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField24FocusGained
   jTextField24.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField24FocusGained

    private void jTextField25FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField25FocusGained
    jTextField25.setForeground(Color.BLACK);
    }//GEN-LAST:event_jTextField25FocusGained

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
  
        if((jTextField23.getText().equalsIgnoreCase("Txn Type")||jTextField23.getText().equalsIgnoreCase(""))){
   
   JOptionPane.showMessageDialog(this, "Please first put the Txn Type!!");
   }else{
      if((jTextField24.getText().equalsIgnoreCase("Txn Code")||jTextField24.getText().equalsIgnoreCase(""))){
   JOptionPane.showMessageDialog(this, "Please first put the Txn Code!!");
   
   }else{   
            if((jTextField25.getText().equalsIgnoreCase("Description")||jTextField25.getText().equalsIgnoreCase(""))){
   JOptionPane.showMessageDialog(this, "Please first put the Description!!");
   
   }else{ 
            switch(jComboBox12.getSelectedItem().toString()){    
                case "CREATE TXN":     
        List txnTypes=new ArrayList();
        
        txnTypes.add(jTextField23.getText());
        
        txnTypes.add(jTextField24.getText());
        
        txnTypes.add(jTextField25.getText());
        
        txnTypes.add("OUT");       

        dbq.createBatchTxnPostingType(txnTypes);

        JOptionPane.showMessageDialog(this, "The Txn Type was successfully created!!"); 
        break;
                case "UPDATE TXN":
                    
               List txnTypesw=new ArrayList();
               
        txnTypesw.add(jComboBox13.getSelectedItem().toString());  

        txnTypesw.add(jTextField23.getText());

        txnTypesw.add(jTextField25.getText());
        
         txnTypesw.add(jTextField24.getText());


        dbq.updateBatchTxnPostingType(txnTypesw);      
     JOptionPane.showMessageDialog(this, "The Txn Type was successfully updated!!");                  
                    break;

            }
        CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        //        f.setUserID(userId);
        this.dispose(); 
    
        
   }}}
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
   switch(jComboBox9.getSelectedItem().toString()){
    
        case "ACTIVATE":
        jComboBox10 .setVisible(true);
        jButton28.setVisible(true);
dbq.feelInActiveTxnTypes(jComboBox10);
        break;
        case "DEACTIVATE":

        jComboBox10 .setVisible(true);
        jButton28.setVisible(true);
dbq.feelActiveTxnTypes(jComboBox10);
        break;
        }
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
     switch(jComboBox12.getSelectedItem().toString()){
    

        case "CREATE TXN":
        jTextField23.setVisible(true);
     jTextField24.setVisible(true);
     jTextField25.setVisible(true);
     jButton29     .setVisible(true);   
jComboBox13.setVisible(false);
        break;
        case "UPDATE TXN":
 
jComboBox13.setVisible(true);
dbq.feelInAllTxnTypes(jComboBox13);
        break;
        }
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
         switch(jComboBox9.getSelectedItem().toString()){
    
        case "ACTIVATE":
//        jComboBox10 .setVisible(true);
//        jButton28.setVisible(true);
dbq.activateTxnType(jComboBox10.getSelectedItem().toString());
JOptionPane.showMessageDialog(this, "The Txn Type was successfully activated!!"); 
     CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
        break;
        case "DEACTIVATE":
//
//        jComboBox10 .setVisible(true);
//        jButton28.setVisible(true);
            
dbq.deactivateTxnType(jComboBox10.getSelectedItem().toString());
JOptionPane.showMessageDialog(this, "The Txn Type was successfully deactivated!!"); 
     CreateNewStaff fv = new CreateNewStaff(userId);
        fv.setVisible(true);
        Dimension screenv = Toolkit.getDefaultToolkit().getScreenSize();
        fv.setSize(screenv.getSize());
        fv.pack();
//        f.setUserID(userId);
        this.dispose(); 
        break;
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
jTextField23.setVisible(true);
     jTextField24.setVisible(true);
     jTextField24.setText(jComboBox13.getSelectedItem().toString());
     jTextField25.setVisible(true);
     jButton29     .setVisible(true);   
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
      if(jCheckBox1.isSelected()){
      jComboBox14 .setVisible(true);
      jButton30 .setVisible(true);
   dbq.feelInAllTxnTypes(jComboBox14);   
      }else if(!jCheckBox1.isSelected()){
      jComboBox14 .setVisible(false);
      jButton30 .setVisible(false);
      
      
      }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed

  dbq.deleteTheTxnType(jComboBox14.getSelectedItem().toString());
JOptionPane.showMessageDialog(this, "The Txn Type was successfully deleted!!"); 
     CreateNewStaff fv = new CreateNewStaff(userId);
        fv.setVisible(true);
        Dimension screenv = Toolkit.getDefaultToolkit().getScreenSize();
        fv.setSize(screenv.getSize());
        fv.pack();
//        f.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
  List balues=dbq.editUsers(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "userIdi.txt")));
  
  jButton5.setEnabled(false);
  jButton9.setEnabled(false);
  jButton12.setEnabled(false);
  jButton3.setEnabled(false);
  jButton15.setEnabled(false);
  
   PayRollModule fv = new PayRollModule(userId,balues.get(0).toString());
        fv.setVisible(true);
        Dimension screenv = Toolkit.getDefaultToolkit().getScreenSize();
        fv.setSize(screenv.getSize());
        fv.pack();
  
  
  
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
       CreateNewStaff f = new CreateNewStaff(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();  
    }//GEN-LAST:event_jButton32ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(CreateNewStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(CreateNewStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(CreateNewStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(CreateNewStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new CreateNewStaff().setVisible(true);
//            }
//        });
//    }
private void   updateAdminDetails(String accountNumber){

    List<String> bioDate=dbq.basicBioDate(accountNumber);
    jLabel16.setVisible(false);
    jButton4.setVisible(false);
jTextField16.setVisible(true);
jTextField13.setVisible(true);
jLabel19.setVisible(true);
jTextField11.setVisible(true);
jLabel20.setVisible(true);
jComboBox1.setVisible(true);
 jLabel12.setVisible(true);
jButton6.setVisible(true);
jTextField10.setVisible(true);
jLabel17 .setVisible(true);
jButton11.setVisible(true);
jTextField12.setVisible(true);
jLabel15.setVisible(true);
jTextField9 .setVisible(true);
jLabel14.setVisible(true);
jLabel11.setVisible(true);
jTextField14.setVisible(true);
    jPanel1.setVisible(true);
jTextField16.setText(accountNumber);
jTextField14.setText(bioDate.get(1));
jTextField9.setText(bioDate.get(2));
jTextField12.setText(bioDate.get(4));
 jTextField11.setText("Student");
    jTextField13.setText("Internee");
}


private void sortTable(JTable table,JTextField textField){

   
    
TableRowSorter<TableModel> rowSorter  = new TableRowSorter<>(table.getModel());
                     
                      table.setRowSorter(rowSorter);
                     textField.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
    

}

 public String getCounter(){
String thecounter="";
fios.forceFileExistanceHundred(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"));

thecounter =""+(parseInt(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt")))+1);

fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"), thecounter);
return thecounter;
} 
 
  private void generateFile(String fileName){
  JOptionPane.showMessageDialog(this, fileName);  
   
     if (Desktop.isDesktopSupported()) {
      try {
          // no application registered for PDFs
          
          File myFile = new File(fios.createFileName1("Excel", "Files", fileName+".xlsx"));
        
          Desktop.getDesktop().open(myFile);
      } catch (IOException ex) {
          Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
      }
     }
    
  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AccountNumberPanel;
    private javax.swing.JTable AdminAccountTable;
    private javax.swing.JPanel AdminMainPanel;
    private javax.swing.JPanel accessRights;
    private javax.swing.JTable adminMainTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPasswordField jPasswordField4;
    private javax.swing.JPasswordField jPasswordField5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTree jTree1;
    private javax.swing.JPanel staffPanel;
    // End of variables declaration//GEN-END:variables

 
  
 
 
    @Override
    public void mouseClicked(MouseEvent me) {
      
       if (me.getClickCount()==2){
         Resource rsc= new Resource(userId);    
            
           if(me.getSource() == AdminAccountTable){  
       
                    int selectedRow =AdminAccountTable.getSelectedRow();
                    int selectedColumn =AdminAccountTable.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = AdminAccountTable.getModel().getValueAt( AdminAccountTable.convertRowIndexToModel(selectedRow), 2);
        AdminMainPanel.setVisible(true);  

AccountNumberPanel.setVisible(false);  
if(AdminMainPanel.isVisible()){
    updateAdminDetails(cvalue.toString());
   
}
     
          }
   
         
  
            
         
                
           }

             
           
           
    
    if(me.getSource()==jTree1){
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
    String window =node.toString();
    switch (window){
        case "Loan SetUp":
    Object[] optionsSF = {"Continue",  "Cancel"};
    int nSF = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSF, optionsSF[0]);
    if(nSF==JOptionPane.YES_OPTION){
         Integer cctv=7;
fios.intFileWriterReplace(fios.createFileName("accountManagement","adminAccounts", "manageUser.txt"), cctv.toString());  
        loanManagementSetup f = new loanManagementSetup(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
    else if (nSF==JOptionPane.NO_OPTION){ this.setVisible(true);}
    break;
    case "Manage Users":
       
        Integer cctv=77;
fios.intFileWriterReplace(fios.createFileName("accountManagement","adminAccounts", "manageUser.txt"), cctv.toString());  
        Object[] optionsxx = {"Continue",  "Cancel"};
        int nxx = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsxx, optionsxx[0]);
        if(nxx==JOptionPane.YES_OPTION){
         AccountNumberPanel.setVisible(false);
        AdminMainPanel.setVisible(true);
        accessRights.setVisible(false);
        }
        else if (nxx==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;
   case "Manage System":
      
       Integer cctvg=7;
fios.intFileWriterReplace(fios.createFileName("accountManagement","adminAccounts", "manageUser.txt"), cctvg.toString()); 
       Object[] optionsx = {"Continue",  "Cancel"};
       int nx = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
       "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsx, optionsx[0]);
       if(nx==JOptionPane.YES_OPTION){
          AccountNumberPanel.setVisible(false);
        AdminMainPanel.setVisible(false);
        accessRights.setVisible(true);
      rsc.allComboPostingRights(jComboBox7);  
       }
       else if (nx==JOptionPane.NO_OPTION){ this.setVisible(true);}
       break;
        case "Log Out":
    Object[] optionsSFx = {"Continue",  "Cancel"};
    int nSFx = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSFx, optionsSFx[0]);
    if(nSFx==JOptionPane.YES_OPTION){
         Integer cctvgg=7;
fios.intFileWriterReplace(fios.createFileName("accountManagement","adminAccounts", "manageUser.txt"), cctvgg.toString()); 

  JFrame f = new Login ();
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
    }
    else if (nSFx==JOptionPane.NO_OPTION){ this.setVisible(true);}
    
    
    
    break;
            
        case "PayrollSetUp":
    Object[] optionsSFp = {"Continue",  "Cancel"};
    int nSFp = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSFp, optionsSFp[0]);
    if(nSFp==JOptionPane.YES_OPTION){
  
fios.intFileWriterReplace(fios.createFileName("accountManagement","adminAccounts", "manageUser.txt"),"7");  
        PayrollSetUp f = new PayrollSetUp(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
    else if (nSFp==JOptionPane.NO_OPTION){ this.setVisible(true);}
    break;    
            
    }
    
    
    
    
    }}
    }

    @Override
    public void mousePressed(MouseEvent me) {
       if(me.getSource() == adminMainTable){  
       
                    int selectedRow =adminMainTable.getSelectedRow();
                    int selectedColumn =adminMainTable.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = adminMainTable.getModel().getValueAt( adminMainTable.convertRowIndexToModel(selectedRow), 0);
              Object cvalue3 = adminMainTable.getModel().getValueAt( adminMainTable.convertRowIndexToModel(selectedRow), 2);
      jButton12.setEnabled(true);
      jButton9.setEnabled(true);
      jButton3.setEnabled(true);
     jButton31.setEnabled(true);
      jButton5.setEnabled(false);
      
      if(cvalue3.toString().equalsIgnoreCase("Loans Officer")){
       jButton15.setEnabled(true);
      if(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "rights"+cvalue.toString()+".txt"))==98){
      
      jButton15.setText("Grant Officer Rights");
      
      }else{
      jButton15.setText("Restrict Officer Rights");
      
      }
                   
                   
  
    
    
          }
           else {
              jButton15.setEnabled(false);      
                   }
         
  
            fios.stringFileWriter(fios.createFileName("logOut", "completeShutDown", "userIdi.txt"), cvalue.toString()); 
         
                
           }}
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    public void updateText(String text) {
     switch( fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"))){
        case 120:jTextField12.setText(text);break;
        case 121:jTextField10.setText(text);break;
      
    }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        

    }
}
