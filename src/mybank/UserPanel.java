package mybank;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author kulsh
 */
public class UserPanel extends javax.swing.JFrame {

    /**
     * Creates new form UserPanel
     */
    
    Connection con;
    Statement stmt;
    ResultSet rs;
    String query, ac_id;
    DefaultTableModel tbmTrans;
    Date today = new Date();
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
    String date = new SimpleDateFormat("yyyy-MM-dd").format(today);
    Random rnd = new Random();
    String user_id;
    
    Date d = new Date();
    SimpleDateFormat current_time = new SimpleDateFormat("hh:mm:ss") ;    
    
    //A/c Info
    void acInfo() {
        try {
            query = "SELECT * FROM accounts where ac_id = "+ ac_id;
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                //Select For A/c Info
                id.setText(rs.getString("ac_id"));
                name.setText(rs.getString("name"));
                acNo.setText(rs.getString("ac_no"));
                gen.setText(rs.getString("gender"));
                phone.setText(rs.getString("phone_no"));
                address.setText(rs.getString("address"));
                adhar.setText(rs.getString("aadhar_no"));
                dob.setText(rs.getString("dob"));
                balance.setText(rs.getString("amount"));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("acInfo Error !");
        }
        
    }
    
    //Update A/c Details
    void update() {
        try {
            query = "update accounts set name = '"+name.getText()+"', gender = '"+gen.getText()+"', phone_no = '"+phone.getText()+"', address = '"+address.getText()+"', dob = '"+dob.getText()+"' where ac_id = "+id.getText();       
            //System.out.println(query);
            
            if(stmt.executeUpdate(query)==1) {
                message.setText("Your New Record has been sucessfully Updated !");
            } else {
                message.setText("Some Error In Update A/c Details !");
            }
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "select * from accounts where ac_id = "+ac_id;
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("update Error !");
        }
        
    }
    
    //For Users A/c Info
    void userInfo() {
       try {
           // user_id value comes from Main Window
            query = "SELECT * FROM users where user_id = " + user_id;
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                userid.setText(rs.getString("user_id"));
                ac_id = rs.getString("ac_id");
                password.setText(rs.getString("user_password"));
            }
            
            query = "select * from accounts where ac_id = "+ ac_id;
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("UserInfo Error !");
        }
    }
    
    //Transaction Info
    void transaction() {
        try {
            query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id where a.ac_id = "+ac_id;
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                String tbData[] = {rs.getString("amount"), rs.getString("transaction_type"), rs.getString("transaction_date"), rs.getString("transaction_time")}; 
                tbmTrans = (DefaultTableModel)transTable.getModel();
                tbmTrans.addRow(tbData);
            }
        }
        catch(Exception ex) {
            System.out.println(ex);
            System.out.println("transaction Error !");
        }
    }
    
    //Deleted A/c Info
//    void deletedAc() {
//        try {
//            query = "select * from delete_ac";
//            rs = stmt.executeQuery(query);
//            while(rs.next()) {
//                
//                String tbData[] = {rs.getString("ac_id"),rs.getString("ac_no"),rs.getString("name"),rs.getString("gender"),rs.getString("phone_no"),rs.getString("address"),rs.getString("aadhar_no"),rs.getString("dob"),rs.getString("amount")};
//                tbmdelete = (DefaultTableModel)deletedAcTable.getModel();
//                tbmdelete.addRow(tbData);
//            }
//        }
//        catch(Exception ex) {
//            System.out.println(ex);
//            System.out.println("deleted A/c Error !");
//        }
//    }
    
    //Deposit
    void deposit() {
        
        try {
            query = "INSERT INTO transactions VALUES("+rnd.nextInt(1000)+", "+ac_id+", 'Deposit', "+depositAmount.getText()+", '"+date+"', '"+current_time.format(d)+"' )";
            stmt.executeUpdate(query);

            query = "UPDATE accounts SET amount = amount + "+depositAmount.getText()+" WHERE ac_id = "+ac_id;
            stmt.executeUpdate(query);
            
            query = "select * from accounts where ac_id = "+ ac_id;
            rs = stmt.executeQuery(query);
            message3.setText("Your Transaction Completed !"); 
        } catch(Exception ex) {
            System.out.println("Error : "+ex.getMessage());
            System.out.println("Deposit Error !");
        }
    }
    
    //WithDrawal
    void withdrawal() {
        try {
            query = "INSERT INTO transactions VALUES("+rnd.nextInt(1000)+", "+ac_id+", 'Withdrawal', "+withdrawalAmount.getText()+", '"+date+"', '"+current_time.format(d)+"' )";
                stmt.executeUpdate(query);
                
                query = "UPDATE accounts SET amount = amount - "+withdrawalAmount.getText()+" WHERE ac_id = "+ac_id;
                stmt.executeUpdate(query);
                
                query = "select * from accounts where ac_id = "+ ac_id;
                rs = stmt.executeQuery(query);
                message4.setText("Your Transaction Completed !");
        } catch(Exception ex) {
            System.out.println("Error : "+ex.getMessage());
            System.out.println("WithDrawal Error !");
        }
    }
   
    public UserPanel() {
        initComponents();
        
        //Set Full Screen Frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        try {
            //Connectivity to DataBase
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?verifySer verCertificate=false&useSSL=true","root","");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            //To Set Today Date
            depositDate.setText(date);
            withdrawalDate.setText(date);
            userInfo();
            
        } catch(Exception ex) {
            System.out.println("Error : "+ex.getMessage());
            System.out.println("Database Exception !");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        home = new javax.swing.JButton();
        details = new javax.swing.JButton();
        transcationDetails = new javax.swing.JButton();
        transaction = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Home = new javax.swing.JPanel();
        Heading = new javax.swing.JLabel();
        AcDetails = new javax.swing.JPanel();
        userPanel = new javax.swing.JPanel();
        Heading7 = new javax.swing.JLabel();
        message2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        userid = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        update1 = new javax.swing.JButton();
        acdetailsPanel = new javax.swing.JPanel();
        Heading2 = new javax.swing.JLabel();
        message = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        acNo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        balance = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        gen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        adhar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        dob = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        TransactionDetail = new javax.swing.JPanel();
        transactionPanel = new javax.swing.JPanel();
        Heading3 = new javax.swing.JLabel();
        searchingBox2 = new javax.swing.JComboBox<>();
        feild2 = new javax.swing.JTextField();
        search2 = new javax.swing.JButton();
        TransactionDetailsTable = new javax.swing.JScrollPane();
        transTable = new javax.swing.JTable();
        Transaction = new javax.swing.JPanel();
        depositPanel = new javax.swing.JPanel();
        Heading8 = new javax.swing.JLabel();
        message3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        depositDate = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        depositAmount = new javax.swing.JTextField();
        deposit = new javax.swing.JButton();
        withdrawalPanel = new javax.swing.JPanel();
        Heading9 = new javax.swing.JLabel();
        message4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        withdrawalDate = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        withdrawalAmount = new javax.swing.JTextField();
        withdrawal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MainPanel.setBackground(new java.awt.Color(120, 120, 120));
        MainPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MainPanel.setPreferredSize(new java.awt.Dimension(730, 730));
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        home.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        home.setText("Home");
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });
        MainPanel.add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 160, 50));

        details.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        details.setText("A/c Details");
        details.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsActionPerformed(evt);
            }
        });
        MainPanel.add(details, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 160, 50));

        transcationDetails.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        transcationDetails.setText("Transaction Details");
        transcationDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transcationDetailsActionPerformed(evt);
            }
        });
        MainPanel.add(transcationDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 160, 50));

        transaction.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        transaction.setText("Transaction");
        transaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transactionActionPerformed(evt);
            }
        });
        MainPanel.add(transaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 160, 50));

        exit.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        MainPanel.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 160, 50));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setForeground(new java.awt.Color(0, 102, 51));

        Heading.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading.setText("Welcome To Onion Bank");

        javax.swing.GroupLayout HomeLayout = new javax.swing.GroupLayout(Home);
        Home.setLayout(HomeLayout);
        HomeLayout.setHorizontalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addContainerGap(601, Short.MAX_VALUE)
                .addComponent(Heading)
                .addContainerGap(647, Short.MAX_VALUE))
        );
        HomeLayout.setVerticalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(Heading)
                .addGap(1060, 1060, 1060))
        );

        jTabbedPane1.addTab("tab1", Home);

        AcDetails.setPreferredSize(new java.awt.Dimension(730, 730));

        userPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Heading7.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading7.setText("Users Info");

        message2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 10)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel8.setText(" User ID:");

        userid.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel18.setText("password:");

        password.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        update1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        update1.setText("Update");
        update1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(Heading7))
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(message2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel8))
                        .addGap(22, 22, 22)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userid, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(password))))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(userPanelLayout.createSequentialGroup()
                    .addGap(177, 177, 177)
                    .addComponent(update1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(203, Short.MAX_VALUE)))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(Heading7)
                .addGap(18, 18, 18)
                .addComponent(message2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userid, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(userPanelLayout.createSequentialGroup()
                    .addGap(309, 309, 309)
                    .addComponent(update1)
                    .addContainerGap(35, Short.MAX_VALUE)))
        );

        acdetailsPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Heading2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading2.setText("A/c Details");

        message.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 10)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel1.setText(" ID:");

        id.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel3.setText("A/c No.:");

        acNo.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel7.setText("A/c Balance:");

        balance.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel2.setText("Name: ");

        name.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel4.setText("Gender:");

        gen.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel5.setText("Phone No.: ");

        phone.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel9.setText("Address:");

        address.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel10.setText("Adhar No.:");

        adhar.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel6.setText("D.O.B.:");

        dob.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        update.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout acdetailsPanelLayout = new javax.swing.GroupLayout(acdetailsPanel);
        acdetailsPanel.setLayout(acdetailsPanelLayout);
        acdetailsPanelLayout.setHorizontalGroup(
            acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acdetailsPanelLayout.createSequentialGroup()
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acdetailsPanelLayout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(Heading2))
                    .addGroup(acdetailsPanelLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(acdetailsPanelLayout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(acdetailsPanelLayout.createSequentialGroup()
                                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(acNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(balance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(acdetailsPanelLayout.createSequentialGroup()
                                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel6))
                                .addGap(22, 22, 22)
                                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(gen, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(phone)
                                    .addComponent(address)
                                    .addComponent(adhar)
                                    .addComponent(dob)
                                    .addComponent(name)))))
                    .addGroup(acdetailsPanelLayout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(202, Short.MAX_VALUE))
        );
        acdetailsPanelLayout.setVerticalGroup(
            acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, acdetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Heading2)
                .addGap(18, 18, 18)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acdetailsPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(acdetailsPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(acNo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adhar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acdetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(update)
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout AcDetailsLayout = new javax.swing.GroupLayout(AcDetails);
        AcDetails.setLayout(AcDetailsLayout);
        AcDetailsLayout.setHorizontalGroup(
            AcDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcDetailsLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(acdetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(232, 232, 232))
        );
        AcDetailsLayout.setVerticalGroup(
            AcDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcDetailsLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(AcDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(acdetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(413, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", AcDetails);

        transactionPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Heading3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading3.setText("Transaction Details");

        searchingBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Date" }));

        feild2.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        search2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        search2.setText("Search");
        search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search2ActionPerformed(evt);
            }
        });

        transTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Amount", "Transaction Type", "Date", "Time"
            }
        ));
        transTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        transTable.setUpdateSelectionOnSort(false);
        TransactionDetailsTable.setViewportView(transTable);

        javax.swing.GroupLayout transactionPanelLayout = new javax.swing.GroupLayout(transactionPanel);
        transactionPanel.setLayout(transactionPanelLayout);
        transactionPanelLayout.setHorizontalGroup(
            transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TransactionDetailsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(transactionPanelLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(searchingBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Heading3)
                            .addGroup(transactionPanelLayout.createSequentialGroup()
                                .addComponent(feild2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(search2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        transactionPanelLayout.setVerticalGroup(
            transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(Heading3)
                .addGap(44, 44, 44)
                .addGroup(transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchingBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(feild2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search2))
                .addGap(93, 93, 93)
                .addComponent(TransactionDetailsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout TransactionDetailLayout = new javax.swing.GroupLayout(TransactionDetail);
        TransactionDetail.setLayout(TransactionDetailLayout);
        TransactionDetailLayout.setHorizontalGroup(
            TransactionDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionDetailLayout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(transactionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(351, Short.MAX_VALUE))
        );
        TransactionDetailLayout.setVerticalGroup(
            TransactionDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionDetailLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(transactionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(444, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", TransactionDetail);

        depositPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Heading8.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading8.setText("Deposit");

        message3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 10)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel11.setText(" Amount:");

        depositDate.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel19.setText("Date:");

        depositAmount.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        deposit.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        deposit.setText("deposit");
        deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout depositPanelLayout = new javax.swing.GroupLayout(depositPanel);
        depositPanel.setLayout(depositPanelLayout);
        depositPanelLayout.setHorizontalGroup(
            depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, depositPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Heading8)
                .addGap(190, 190, 190))
            .addGroup(depositPanelLayout.createSequentialGroup()
                .addGroup(depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(depositPanelLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(message3, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(depositPanelLayout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(depositPanelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(depositDate, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(depositAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        depositPanelLayout.setVerticalGroup(
            depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(Heading8)
                .addGap(18, 18, 18)
                .addComponent(message3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(depositAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(depositDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addComponent(deposit)
                .addGap(60, 60, 60))
        );

        withdrawalPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Heading9.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading9.setText("WithDrawal");

        message4.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 10)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel12.setText(" Amount:");

        withdrawalDate.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel27.setText("Date:");

        withdrawalAmount.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        withdrawal.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        withdrawal.setText("withdrawal");
        withdrawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout withdrawalPanelLayout = new javax.swing.GroupLayout(withdrawalPanel);
        withdrawalPanel.setLayout(withdrawalPanelLayout);
        withdrawalPanelLayout.setHorizontalGroup(
            withdrawalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawalPanelLayout.createSequentialGroup()
                .addGroup(withdrawalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(withdrawalPanelLayout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(withdrawal, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(withdrawalPanelLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(withdrawalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(withdrawalPanelLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(47, 47, 47)
                                .addComponent(withdrawalDate, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(withdrawalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(withdrawalPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(withdrawalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(message4, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, withdrawalPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Heading9)
                .addGap(163, 163, 163))
        );
        withdrawalPanelLayout.setVerticalGroup(
            withdrawalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawalPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(Heading9)
                .addGap(18, 18, 18)
                .addComponent(message4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(withdrawalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(withdrawalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(withdrawalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(withdrawalDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(withdrawal)
                .addGap(60, 60, 60))
        );

        javax.swing.GroupLayout TransactionLayout = new javax.swing.GroupLayout(Transaction);
        Transaction.setLayout(TransactionLayout);
        TransactionLayout.setHorizontalGroup(
            TransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionLayout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(depositPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(withdrawalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(353, Short.MAX_VALUE))
        );
        TransactionLayout.setVerticalGroup(
            TransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionLayout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addGroup(TransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(withdrawalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(depositPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(593, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", Transaction);

        MainPanel.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, -36, 1550, 1170));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1871, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1871, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_homeActionPerformed

    private void detailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsActionPerformed
        userInfo();
        acInfo();
        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_detailsActionPerformed

    private void transcationDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transcationDetailsActionPerformed
        transaction();
        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");
        jTabbedPane1.setSelectedIndex(2);
        
    }//GEN-LAST:event_transcationDetailsActionPerformed

    private void transactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transactionActionPerformed
        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_transactionActionPerformed

    private void update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update1ActionPerformed

        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");

        try {
           query =  "update users set user_password = '"+password.getText()+"' WHERE ac_id = "+ ac_id;
            //System.out.println(query);
            if(stmt.executeUpdate(query)==1) {
                message2.setText("Your New Record has been sucessfully Updated !");
            } else {
                message2.setText("Some Error !");
            }
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "select * from accounts where ac_id = "+ ac_id;
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("update Error !");
        }
    }//GEN-LAST:event_update1ActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");
        update();
    }//GEN-LAST:event_updateActionPerformed

    private void search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search2ActionPerformed
       
        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");
        
        try {
            tbmTrans.setRowCount(0);
            if(searchingBox2.getSelectedItem() == "Date") {
                //Edit
                query = "SELECT a.ac_id, ac_no ,a.name,phone_no, transaction_type, transaction_date, transaction_time, tr.amount FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id WHERE tr.transaction_date = '"+feild2.getText()+"' AND a.ac_id = "+ ac_id;
            } else {
                query = "SELECT a.ac_id, ac_no ,a.name,phone_no, transaction_type, transaction_date, transaction_time, tr.amount FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id where a.ac_id = "+ ac_id;
            }
            //System.out.println(query);
            rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                String tbData[] = {rs.getString("amount"), rs.getString("transaction_type"), rs.getString("transaction_date"),rs.getString("transaction_time")};
                tbmTrans = (DefaultTableModel)transTable.getModel();
                tbmTrans.addRow(tbData);
            }
            //Edit
            query = "select * from accounts where ac_id = "+ ac_id;
            rs = stmt.executeQuery(query);
        }
        catch(Exception ex) {
            System.out.println(ex);
            System.out.println("transaction Error !");
        }
    }//GEN-LAST:event_search2ActionPerformed

    private void depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositActionPerformed
        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");
        deposit();
    }//GEN-LAST:event_depositActionPerformed

    private void withdrawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawalActionPerformed
        message.setText("");
        message2.setText("");
        message3.setText("");
        message4.setText("");
        withdrawal();
    }//GEN-LAST:event_withdrawalActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        message.setText("");
        message2.setText("");
        message3.setText("");
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AcDetails;
    private javax.swing.JLabel Heading;
    private javax.swing.JLabel Heading2;
    private javax.swing.JLabel Heading3;
    private javax.swing.JLabel Heading7;
    private javax.swing.JLabel Heading8;
    private javax.swing.JLabel Heading9;
    private javax.swing.JPanel Home;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel Transaction;
    private javax.swing.JPanel TransactionDetail;
    private javax.swing.JScrollPane TransactionDetailsTable;
    private javax.swing.JLabel acNo;
    private javax.swing.JPanel acdetailsPanel;
    private javax.swing.JTextField address;
    private javax.swing.JTextField adhar;
    private javax.swing.JLabel balance;
    private javax.swing.JButton deposit;
    private javax.swing.JTextField depositAmount;
    private javax.swing.JLabel depositDate;
    private javax.swing.JPanel depositPanel;
    private javax.swing.JButton details;
    private javax.swing.JTextField dob;
    private javax.swing.JButton exit;
    private javax.swing.JTextField feild2;
    private javax.swing.JTextField gen;
    private javax.swing.JButton home;
    private javax.swing.JLabel id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel message;
    private javax.swing.JLabel message2;
    private javax.swing.JLabel message3;
    private javax.swing.JLabel message4;
    private javax.swing.JTextField name;
    private javax.swing.JTextField password;
    private javax.swing.JTextField phone;
    private javax.swing.JButton search2;
    private javax.swing.JComboBox<String> searchingBox2;
    public javax.swing.JTable transTable;
    private javax.swing.JButton transaction;
    private javax.swing.JPanel transactionPanel;
    private javax.swing.JButton transcationDetails;
    private javax.swing.JButton update;
    private javax.swing.JButton update1;
    private javax.swing.JPanel userPanel;
    private javax.swing.JLabel userid;
    private javax.swing.JButton withdrawal;
    private javax.swing.JTextField withdrawalAmount;
    private javax.swing.JLabel withdrawalDate;
    private javax.swing.JPanel withdrawalPanel;
    // End of variables declaration//GEN-END:variables
}
