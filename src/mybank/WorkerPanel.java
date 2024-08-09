package mybank;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class WorkerPanel extends javax.swing.JFrame {

    /**
     * Creates new form WorkerPanel
     */
    Connection con;
    Statement stmt;
    ResultSet rs;
    String query;
    DefaultTableModel tbm, tbmdelete;
    Random rnd = new Random();
    String user_id;
    
    
    
    //For select A/c's Info
    void select() {
        try {
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
            
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("select Error !");
        }
    }
    
    //A/c Info
    void acInfo() {
        try {
            query = "SELECT * FROM accounts";
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                //Select For A/c Info
                select();
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
                message.setText("Some Error !");
            }
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "select * from accounts";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("update Error !");
        }
        
    }
    
    //Searching A/c With ID and A/c No
    void search() {
        try {
            //System.out.println(searchingBox.getSelectedItem());
            message.setText("");
            
            if(searchingBox.getSelectedItem() == "ID") {
                query = "select * from accounts where ac_id = "+Ffeld.getText();
            } else {
                query = "select * from accounts where ac_no = '"+Ffeld.getText()+"'";
            }
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                select();
            }
            query = "select * from accounts";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("search Error !");
        }
            
                                
    }
    
    //For Users A/c Info
    void userInfo() {
       try {
            query = "SELECT * FROM users where super_user is not null and user_id = "+ user_id;
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                userid.setText(rs.getString("user_id"));
                username.setText(rs.getString("super_user"));
                password.setText(rs.getString("user_password"));
            }
            query = "select * from accounts";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("searching  User Error !");
        }
    }
    
    //Transaction Info
    void transaction() {
        try {
            query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id;";
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                String tbData[] = {rs.getString("a.ac_id"), rs.getString("name"), rs.getString("transaction_type"), rs.getString("transaction_date"), rs.getString("phone_no"), rs.getString("amount"), rs.getString("transaction_time")};
                tbm = (DefaultTableModel)transTable.getModel();
                tbm.addRow(tbData);
            }
        }
        catch(Exception ex) {
            System.out.println(ex);
            System.out.println("transaction Error !");
        }
    }
    
    //Deleted A/c Info
    void deletedAc() {
        try {
            query = "select * from delete_ac";
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                
                String tbData[] = {rs.getString("ac_id"),rs.getString("ac_no"),rs.getString("name"),rs.getString("gender"),rs.getString("phone_no"),rs.getString("address"),rs.getString("aadhar_no"),rs.getString("dob"),rs.getString("amount")};
                tbmdelete = (DefaultTableModel)deletedAcTable.getModel();
                tbmdelete.addRow(tbData);
            }
        }
        catch(Exception ex) {
            System.out.println(ex);
            System.out.println("deleted A/c Error !");
        }
    }
    
    //Create A/c
    void createAc() {
        try {
            query = "insert into accounts (ac_id, ac_no, name, gender, phone_no, address, aadhar_no, dob, amount) values ("+rnd.nextInt(1000)+", 'AC10039', '"+name1.getText()+"', '"+gen1.getText()+"', '"+phone1.getText()+"', '"+address1.getText()
                    +"','"+adhar1.getText()+"', '"+dob1.getText()+"', "+amount.getText()+")";
            
            //System.out.println(query);
            if(stmt.executeUpdate(query)==1) {
                message1.setText("A/c Is Created !"); 
            }
            else {
                message1.setText("A/c Isn't Created Please Try Again !");
            }
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "select * from accounts";
            rs = stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Create A/c Error ! ");
        }
    }public WorkerPanel() {
        initComponents();
        
        //Set Full Screen Frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?verifySer verCertificate=false&useSSL=true","root","");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            acInfo();
        }
        catch(Exception ex) {
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

        jPanel6 = new javax.swing.JPanel();
        users = new javax.swing.JButton();
        details = new javax.swing.JButton();
        transcation = new javax.swing.JButton();
        deletedAc = new javax.swing.JButton();
        createAc = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        AcDetails = new javax.swing.JPanel();
        acDetailsPanel = new javax.swing.JPanel();
        first = new javax.swing.JButton();
        back = new javax.swing.JButton();
        update = new javax.swing.JButton();
        last = new javax.swing.JButton();
        next = new javax.swing.JButton();
        searchingBox = new javax.swing.JComboBox<>();
        Ffeld = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        adhar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        dob = new javax.swing.JTextField();
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
        Heading2 = new javax.swing.JLabel();
        message = new javax.swing.JLabel();
        UserInfo = new javax.swing.JPanel();
        userInfoPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        update1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        userid = new javax.swing.JLabel();
        Heading6 = new javax.swing.JLabel();
        message2 = new javax.swing.JLabel();
        Transaction = new javax.swing.JPanel();
        transactionPanel = new javax.swing.JPanel();
        searchingBox2 = new javax.swing.JComboBox<>();
        feild2 = new javax.swing.JTextField();
        search2 = new javax.swing.JButton();
        Heading3 = new javax.swing.JLabel();
        TransactionDetails = new javax.swing.JScrollPane();
        transTable = new javax.swing.JTable();
        DeletedAc = new javax.swing.JPanel();
        deleteAcPanel = new javax.swing.JPanel();
        searchingBox3 = new javax.swing.JComboBox<>();
        feild3 = new javax.swing.JTextField();
        search3 = new javax.swing.JButton();
        Heading4 = new javax.swing.JLabel();
        DeletedAcDetails = new javax.swing.JScrollPane();
        deletedAcTable = new javax.swing.JTable();
        CreateAc = new javax.swing.JPanel();
        createAcPanel = new javax.swing.JPanel();
        Heading5 = new javax.swing.JLabel();
        message1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        name1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        gen1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        phone1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        address1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        adhar1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        dob1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        createAc2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(730, 730));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(120, 120, 120));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setPreferredSize(new java.awt.Dimension(730, 730));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        users.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        users.setText("You");
        users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersActionPerformed(evt);
            }
        });
        jPanel6.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 160, 50));

        details.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        details.setText("A/c Details");
        details.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsActionPerformed(evt);
            }
        });
        jPanel6.add(details, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 160, 50));

        transcation.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        transcation.setText("Transaction");
        transcation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transcationActionPerformed(evt);
            }
        });
        jPanel6.add(transcation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 160, 50));

        deletedAc.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        deletedAc.setText("Deleted A/c");
        deletedAc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletedAcActionPerformed(evt);
            }
        });
        jPanel6.add(deletedAc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 160, 50));

        createAc.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        createAc.setText("Create A/c");
        createAc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAcActionPerformed(evt);
            }
        });
        jPanel6.add(createAc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 160, 50));

        exit.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jPanel6.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 160, 50));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setForeground(new java.awt.Color(0, 102, 51));

        AcDetails.setPreferredSize(new java.awt.Dimension(730, 730));

        acDetailsPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        first.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        first.setText("First");
        first.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstActionPerformed(evt);
            }
        });

        back.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        back.setText("<<");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        update.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        last.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        last.setText("Last");
        last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastActionPerformed(evt);
            }
        });

        next.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        next.setText(">>");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        searchingBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "A/c No." }));

        search.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        search.setText("Search");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel5.setText("Phone No.: ");

        phone.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N
        phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel9.setText("Address:");

        address.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel10.setText("Adhar No.:");

        adhar.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel6.setText("D.O.B.:");

        dob.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

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

        Heading2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading2.setText("A/c Details");

        message.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 10)); // NOI18N

        javax.swing.GroupLayout acDetailsPanelLayout = new javax.swing.GroupLayout(acDetailsPanel);
        acDetailsPanel.setLayout(acDetailsPanelLayout);
        acDetailsPanelLayout.setHorizontalGroup(
            acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acDetailsPanelLayout.createSequentialGroup()
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acDetailsPanelLayout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(acDetailsPanelLayout.createSequentialGroup()
                                .addComponent(searchingBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Ffeld, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(acDetailsPanelLayout.createSequentialGroup()
                                .addComponent(first, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(last, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(acDetailsPanelLayout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(acDetailsPanelLayout.createSequentialGroup()
                                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(acDetailsPanelLayout.createSequentialGroup()
                                        .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel6))
                                        .addGap(22, 22, 22)
                                        .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(address, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(phone)
                                            .addComponent(adhar)
                                            .addComponent(dob)))
                                    .addGroup(acDetailsPanelLayout.createSequentialGroup()
                                        .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4))
                                        .addGap(45, 45, 45)
                                        .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(gen)
                                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(174, 174, 174))
                            .addGroup(acDetailsPanelLayout.createSequentialGroup()
                                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(acNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(balance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(59, 59, 59))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, acDetailsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, acDetailsPanelLayout.createSequentialGroup()
                        .addComponent(Heading2)
                        .addGap(299, 299, 299))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, acDetailsPanelLayout.createSequentialGroup()
                        .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(213, 213, 213))))
        );
        acDetailsPanelLayout.setVerticalGroup(
            acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, acDetailsPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(Heading2)
                .addGap(18, 18, 18)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(acDetailsPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(acDetailsPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(acNo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adhar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(first)
                    .addComponent(back)
                    .addComponent(next)
                    .addComponent(last)
                    .addComponent(update))
                .addGap(18, 18, 18)
                .addGroup(acDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search)
                    .addComponent(Ffeld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchingBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout AcDetailsLayout = new javax.swing.GroupLayout(AcDetails);
        AcDetails.setLayout(AcDetailsLayout);
        AcDetailsLayout.setHorizontalGroup(
            AcDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcDetailsLayout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(acDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(453, Short.MAX_VALUE))
        );
        AcDetailsLayout.setVerticalGroup(
            AcDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcDetailsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(acDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(358, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", AcDetails);

        UserInfo.setPreferredSize(new java.awt.Dimension(730, 730));

        userInfoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        jLabel17.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel17.setText("User Name:");

        username.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel8.setText(" User ID:");

        userid.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N

        Heading6.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading6.setText("Users Info");

        message2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 10)); // NOI18N

        javax.swing.GroupLayout userInfoPanelLayout = new javax.swing.GroupLayout(userInfoPanel);
        userInfoPanel.setLayout(userInfoPanelLayout);
        userInfoPanelLayout.setHorizontalGroup(
            userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userInfoPanelLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(update1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(userInfoPanelLayout.createSequentialGroup()
                        .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(userInfoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(46, 46, 46)
                                .addComponent(password))
                            .addGroup(userInfoPanelLayout.createSequentialGroup()
                                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel8))
                                .addGap(37, 37, 37)
                                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(username)
                                    .addComponent(userid, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(63, Short.MAX_VALUE))))
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userInfoPanelLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(Heading6))
                    .addComponent(message2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        userInfoPanelLayout.setVerticalGroup(
            userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userInfoPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(Heading6)
                .addGap(48, 48, 48)
                .addComponent(message2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userid, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(update1)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout UserInfoLayout = new javax.swing.GroupLayout(UserInfo);
        UserInfo.setLayout(UserInfoLayout);
        UserInfoLayout.setHorizontalGroup(
            UserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserInfoLayout.createSequentialGroup()
                .addGap(402, 402, 402)
                .addComponent(userInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(594, Short.MAX_VALUE))
        );
        UserInfoLayout.setVerticalGroup(
            UserInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserInfoLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(userInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(545, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab6", UserInfo);

        transactionPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        searchingBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "ID", "A/c", "Name", "Aadhar", "Phone No.", "Date" }));
        searchingBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchingBox2ActionPerformed(evt);
            }
        });

        feild2.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        search2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        search2.setText("Search");
        search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search2ActionPerformed(evt);
            }
        });

        Heading3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        Heading3.setText("Transaction");

        transTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Transaction Type", "Date", "Phone No.", "Amount", "Time"
            }
        ));
        transTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        transTable.setUpdateSelectionOnSort(false);
        TransactionDetails.setViewportView(transTable);

        javax.swing.GroupLayout transactionPanelLayout = new javax.swing.GroupLayout(transactionPanel);
        transactionPanel.setLayout(transactionPanelLayout);
        transactionPanelLayout.setHorizontalGroup(
            transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionPanelLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transactionPanelLayout.createSequentialGroup()
                        .addComponent(TransactionDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transactionPanelLayout.createSequentialGroup()
                        .addComponent(searchingBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(feild2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(search2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(237, 237, 237))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transactionPanelLayout.createSequentialGroup()
                        .addComponent(Heading3)
                        .addGap(390, 390, 390))))
        );
        transactionPanelLayout.setVerticalGroup(
            transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transactionPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(Heading3)
                .addGap(39, 39, 39)
                .addGroup(transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(feild2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchingBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(TransactionDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout TransactionLayout = new javax.swing.GroupLayout(Transaction);
        Transaction.setLayout(TransactionLayout);
        TransactionLayout.setHorizontalGroup(
            TransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionLayout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(transactionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(366, Short.MAX_VALUE))
        );
        TransactionLayout.setVerticalGroup(
            TransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(transactionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(477, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", Transaction);

        deleteAcPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        searchingBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "ID", "A/c", "Name", "Aadhar", "Phone No." }));

        feild3.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        search3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        search3.setText("Search");
        search3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search3ActionPerformed(evt);
            }
        });

        Heading4.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 20)); // NOI18N
        Heading4.setText("Deleted A/c");

        deletedAcTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "A/c No.", "Name", "Gender", "Phone No", "Address", "Adhar No. ", "D.O.B.", "Amount"
            }
        ));
        deletedAcTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deletedAcTable.setUpdateSelectionOnSort(false);
        DeletedAcDetails.setViewportView(deletedAcTable);

        javax.swing.GroupLayout deleteAcPanelLayout = new javax.swing.GroupLayout(deleteAcPanel);
        deleteAcPanel.setLayout(deleteAcPanelLayout);
        deleteAcPanelLayout.setHorizontalGroup(
            deleteAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteAcPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(deleteAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteAcPanelLayout.createSequentialGroup()
                        .addComponent(Heading4)
                        .addGap(489, 489, 489))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteAcPanelLayout.createSequentialGroup()
                        .addComponent(searchingBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(feild3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(search3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(320, 320, 320))))
            .addGroup(deleteAcPanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(DeletedAcDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 1065, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 49, Short.MAX_VALUE))
        );
        deleteAcPanelLayout.setVerticalGroup(
            deleteAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteAcPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(Heading4)
                .addGap(27, 27, 27)
                .addGroup(deleteAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchingBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(feild3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(DeletedAcDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout DeletedAcLayout = new javax.swing.GroupLayout(DeletedAc);
        DeletedAc.setLayout(DeletedAcLayout);
        DeletedAcLayout.setHorizontalGroup(
            DeletedAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeletedAcLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(deleteAcPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(264, Short.MAX_VALUE))
        );
        DeletedAcLayout.setVerticalGroup(
            DeletedAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeletedAcLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(deleteAcPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(504, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", DeletedAc);

        createAcPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Heading5.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 20)); // NOI18N
        Heading5.setText("Create A/c");

        message1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel20.setText("Name: ");

        name1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel21.setText("Gender:");

        gen1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel22.setText("Phone No.: ");

        phone1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel23.setText("Address:");

        address1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel24.setText("Adhar No.:");

        adhar1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel25.setText("D.O.B.:");

        dob1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        jLabel26.setText("A/c Balance:");

        amount.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        createAc2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 12)); // NOI18N
        createAc2.setText("Create A/c");
        createAc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAc2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout createAcPanelLayout = new javax.swing.GroupLayout(createAcPanel);
        createAcPanel.setLayout(createAcPanelLayout);
        createAcPanelLayout.setHorizontalGroup(
            createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createAcPanelLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(createAcPanelLayout.createSequentialGroup()
                        .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(22, 22, 22)
                        .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gen1)
                            .addComponent(phone1)
                            .addComponent(address1)
                            .addComponent(adhar1)
                            .addComponent(dob1)
                            .addComponent(name1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(createAcPanelLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20)
                    .addGroup(createAcPanelLayout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(createAc2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAcPanelLayout.createSequentialGroup()
                .addContainerGap(156, Short.MAX_VALUE)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAcPanelLayout.createSequentialGroup()
                        .addComponent(Heading5)
                        .addGap(231, 231, 231))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAcPanelLayout.createSequentialGroup()
                        .addComponent(message1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140))))
        );
        createAcPanelLayout.setVerticalGroup(
            createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createAcPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(Heading5)
                .addGap(18, 18, 18)
                .addComponent(message1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gen1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adhar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dob1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(createAc2)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CreateAcLayout = new javax.swing.GroupLayout(CreateAc);
        CreateAc.setLayout(CreateAcLayout);
        CreateAcLayout.setHorizontalGroup(
            CreateAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateAcLayout.createSequentialGroup()
                .addGap(441, 441, 441)
                .addComponent(createAcPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(516, 516, 516))
        );
        CreateAcLayout.setVerticalGroup(
            CreateAcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateAcLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(createAcPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(448, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", CreateAc);

        jPanel6.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, -36, 1550, 1170));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1871, 1090));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void serach1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serach1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serach1ActionPerformed

    private void usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersActionPerformed
        userInfo();
        message.setText("");
        message2.setText("");
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_usersActionPerformed

    private void update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update1ActionPerformed
        try {
           query = "update users set user_password = '"+password.getText()+"' , super_user = '"+username.getText()+"' WHERE user_id = 120";
            //System.out.println(query);
            if(stmt.executeUpdate(query)==1) {
                message2.setText("Your New Record has been sucessfully Updated !");
            } else {
                message2.setText("Some Error !");
            }
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "select * from accounts";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("update Error !");
        }
    }//GEN-LAST:event_update1ActionPerformed

    private void createAc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAc2ActionPerformed
        createAc();
    }//GEN-LAST:event_createAc2ActionPerformed

    private void search3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search3ActionPerformed
        try {
            tbmdelete.setRowCount(0);
            if(searchingBox3.getSelectedItem() == "ID") {
                query = "select * from delete_ac where ac_id = "+feild3.getText();
            } else if(searchingBox3.getSelectedItem() == "A/c") {
                query = "select * from delete_ac WHERE ac_no = '"+feild3.getText()+"';";
            } else if(searchingBox3.getSelectedItem() == "Name") {
                query = "select * from delete_ac WHERE name = '"+feild3.getText()+"';";
            } else if(searchingBox3.getSelectedItem() == "Aadhar") {
                query = "select * from delete_ac WHERE aadhar_no = '"+feild3.getText()+"';";
            } else if(searchingBox3.getSelectedItem() == "Phone No.") {
                query = "select * from delete_ac WHERE phone_no = '"+feild3.getText()+"';";
            } else {
                query = "select * from delete_ac";
            }
            //System.out.println(query);
            rs = stmt.executeQuery(query);
            while(rs.next()) {

                String tbData[] = {rs.getString("ac_id"),rs.getString("ac_no"),rs.getString("name"),rs.getString("gender"),rs.getString("phone_no"),rs.getString("address"),rs.getString("aadhar_no"),rs.getString("dob"),rs.getString("amount")};
                tbmdelete = (DefaultTableModel)deletedAcTable.getModel();
                tbmdelete.addRow(tbData);
            }
            query = "select * from accounts";
            rs = stmt.executeQuery(query);
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_search3ActionPerformed

    private void search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search2ActionPerformed
        try {
            tbm.setRowCount(0);
            if(searchingBox2.getSelectedItem() == "ID") {
                query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id WHERE a.ac_id = "+feild2.getText();
            } else if(searchingBox2.getSelectedItem() == "A/c") {
                query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id WHERE a.ac_no = '"+feild2.getText()+"';";
            } else if(searchingBox2.getSelectedItem() == "Name") {
                query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id WHERE a.name = '"+feild2.getText()+"';";
            } else if(searchingBox2.getSelectedItem() == "Aadhar") {
                query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id WHERE a.aadhar_no = '"+feild2.getText()+"';";
            } else if(searchingBox2.getSelectedItem() == "Phone No.") {
                query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id WHERE a.phone_no = '"+feild2.getText()+"';";
            } else if(searchingBox2.getSelectedItem() == "Date") {
                query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id WHERE tr.transaction_date = '"+feild2.getText()+"';";
            } else {
                query = "SELECT * FROM transactions as tr INNER JOIN accounts as a ON a.ac_id = tr.ac_id;";
            }
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                String tbData[] = {rs.getString("a.ac_id"), rs.getString("name"), rs.getString("transaction_type"), rs.getString("transaction_date"), rs.getString("phone_no"), rs.getString("amount"),rs.getString("transaction_time")};
                tbm = (DefaultTableModel)transTable.getModel();
                tbm.addRow(tbData);
            }
            query = "select * from accounts";
            rs = stmt.executeQuery(query);
        }
        catch(Exception ex) {
            System.out.println(ex);
            System.out.println("transaction Error !");
        }
    }//GEN-LAST:event_search2ActionPerformed

    private void lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastActionPerformed
        try {
            rs.last();
            select();
            message.setText("");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_lastActionPerformed

    private void firstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstActionPerformed
        try {
            rs.first();
            select();
            message.setText("");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_firstActionPerformed

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        try {
            if(!rs.isLast()) {
                rs.next();
                select();
                message.setText("");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_nextActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        update();
    }//GEN-LAST:event_updateActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        try {
            if(!rs.isFirst()) {
                rs.previous();
                select();
            }
            message.setText("");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_backActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        search();
    }//GEN-LAST:event_searchActionPerformed

    private void createAcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAcActionPerformed
        
        message.setText("");
        message2.setText("");
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_createAcActionPerformed

    private void deletedAcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletedAcActionPerformed
        deletedAc();
        message.setText("");
        message2.setText("");
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_deletedAcActionPerformed

    private void transcationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transcationActionPerformed
        transaction();
        message.setText("");
        message2.setText("");
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_transcationActionPerformed

    private void detailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsActionPerformed
        acInfo();
        message.setText("");
        message2.setText("");
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_detailsActionPerformed

    private void phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneActionPerformed

    private void searchingBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchingBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchingBox2ActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        message.setText("");
        message2.setText("");
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
            java.util.logging.Logger.getLogger(WorkerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WorkerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WorkerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WorkerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WorkerPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AcDetails;
    private javax.swing.JPanel CreateAc;
    private javax.swing.JPanel DeletedAc;
    private javax.swing.JScrollPane DeletedAcDetails;
    private javax.swing.JTextField Ffeld;
    private javax.swing.JLabel Heading2;
    private javax.swing.JLabel Heading3;
    private javax.swing.JLabel Heading4;
    private javax.swing.JLabel Heading5;
    private javax.swing.JLabel Heading6;
    private javax.swing.JPanel Transaction;
    private javax.swing.JScrollPane TransactionDetails;
    private javax.swing.JPanel UserInfo;
    private javax.swing.JPanel acDetailsPanel;
    private javax.swing.JLabel acNo;
    private javax.swing.JTextField address;
    private javax.swing.JTextField address1;
    private javax.swing.JTextField adhar;
    private javax.swing.JTextField adhar1;
    private javax.swing.JTextField amount;
    private javax.swing.JButton back;
    private javax.swing.JLabel balance;
    private javax.swing.JButton createAc;
    private javax.swing.JButton createAc2;
    private javax.swing.JPanel createAcPanel;
    private javax.swing.JPanel deleteAcPanel;
    private javax.swing.JButton deletedAc;
    public javax.swing.JTable deletedAcTable;
    private javax.swing.JButton details;
    private javax.swing.JTextField dob;
    private javax.swing.JTextField dob1;
    private javax.swing.JButton exit;
    private javax.swing.JTextField feild2;
    private javax.swing.JTextField feild3;
    private javax.swing.JButton first;
    private javax.swing.JTextField gen;
    private javax.swing.JTextField gen1;
    private javax.swing.JLabel id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton last;
    private javax.swing.JLabel message;
    private javax.swing.JLabel message1;
    private javax.swing.JLabel message2;
    private javax.swing.JTextField name;
    private javax.swing.JTextField name1;
    private javax.swing.JButton next;
    private javax.swing.JTextField password;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField phone1;
    private javax.swing.JButton search;
    private javax.swing.JButton search2;
    private javax.swing.JButton search3;
    private javax.swing.JComboBox<String> searchingBox;
    private javax.swing.JComboBox<String> searchingBox2;
    private javax.swing.JComboBox<String> searchingBox3;
    public javax.swing.JTable transTable;
    private javax.swing.JPanel transactionPanel;
    private javax.swing.JButton transcation;
    private javax.swing.JButton update;
    private javax.swing.JButton update1;
    private javax.swing.JPanel userInfoPanel;
    private javax.swing.JLabel userid;
    private javax.swing.JTextField username;
    private javax.swing.JButton users;
    // End of variables declaration//GEN-END:variables
}
