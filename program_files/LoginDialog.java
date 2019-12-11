import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class LoginDialog  extends  JDialog
implements ActionListener
{
    String                  role;
    JTextField              usernameTF, queryTF;
    JPasswordField          passwordTF;
    JLabel                  usernameLabel, passwordLabel, queryLabel;
    JPanel                  myMainPanel, buttonP;
    JButton                 loginButton, exitButton, submitButton, execute;
    ConnectionHandler       connectionHandler;
    Frame                   mainFrame;
    String                  tempUserName;
    boolean                 loggedIn;
    JTextField idTF, notesTF, empIdTF, firstNameTF, lastNameTF, emailTF, streetTF, zipTF, cityTF, stateTF, phoneTF, roleTF, dealershipNumTF;



    //warning dialog constructor

    LoginDialog(Frame host, String title, int num){  

		this.mainFrame = host;

		JLabel warning = new JLabel("       Warning! These changes will not be saved!");


		execute = new JButton("OK");
		execute.addActionListener(this);
		execute.setActionCommand("EXIT");
		getRootPane().setDefaultButton(execute);


		JPanel buttonPanel = new JPanel();
		buttonPanel.add(execute);

		add(warning, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        Toolkit tk;
		Dimension d;

		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();

        int queryFrameWidth = d.width/2;
        int queryFrameHeight = d.height/2;

		setSize(queryFrameWidth, queryFrameHeight);
		setLocationRelativeTo(this.mainFrame);
		setTitle("Query Frame");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
	}




    // constructor for editing dialogs
    LoginDialog(Frame frame, String name){

        this.mainFrame = frame;
        
        if(name.equals("Add A New Customer")){
            buildAddCustomerGui(frame, name);
        }
        else if(name.equals("Add A New Employee")){
            buildAddEmployeeGui(frame, name);
            System.out.println("adding a new employee...");
        }
}


    // query dialog constructor
    LoginDialog(Frame frame){

        this.mainFrame = frame;
        
        queryGui(); 

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("SUBMIT BUTTON");
        submitButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(this);
        
        buttonP = new JPanel(new FlowLayout());
        buttonP.add(submitButton);
        buttonP.add(exitButton);
        add (buttonP, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(loginButton);
        
        setSize(300, 300);
        setVisible(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false); 
}

    // constructor for a dialog to log in
    LoginDialog(Frame frame, ConnectionHandler connectionHandler){

        this.connectionHandler = connectionHandler;
        this.mainFrame = frame;
        
        buildLoginGui(frame); 
}


    void buildAddEmployeeGui(Frame frame, String name){

        myMainPanel = new JPanel();

        JLabel empIdLabel, usernameLabel, passwordLabel, roleLabel, dealershipNumLabel, firstNameLabel, lastNameLabel, emailLabel, streetLabel, zipLabel, stateLabel, phoneLabel, cityLabel;
        
        GroupLayout layout = new GroupLayout(myMainPanel);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        empIdLabel = new JLabel("Customer ID: ");
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        roleLabel = new JLabel("Role: ");
        dealershipNumLabel = new JLabel("Dealership Number: ");
        firstNameLabel = new JLabel("First Name: ");
        lastNameLabel = new JLabel("Last Name: ");
        emailLabel = new JLabel("Email: ");
        streetLabel = new JLabel("Street: ");
        zipLabel = new JLabel("Zip Code: ");
        cityLabel = new JLabel("City: ");
        stateLabel = new JLabel("State: ");
        phoneLabel = new JLabel("Phone Number: ");
        
        empIdTF = new JTextField(20);
        usernameTF = new JTextField(20);
        passwordTF = new JPasswordField(20);
        roleTF = new JTextField(20);
        dealershipNumTF = new JTextField(20);
        firstNameTF = new JTextField(20);
        lastNameTF = new JTextField(20);
        emailTF = new JTextField(20);
        streetTF = new JTextField(20);
        zipTF = new JTextField(20);
        cityTF = new JTextField(20);
        stateTF = new JTextField(20);
        phoneTF = new JTextField(20);

        notesTF = new JTextField(20);
        
        myMainPanel.setLayout (layout);
        
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        
        hGroup.addGroup(layout.createParallelGroup().addComponent(empIdLabel).addComponent(usernameLabel).addComponent(passwordLabel).addComponent(roleLabel).addComponent(dealershipNumLabel).addComponent(firstNameLabel).addComponent(lastNameLabel).addComponent(emailLabel).addComponent(streetLabel).addComponent(cityLabel).addComponent(zipLabel).addComponent(stateLabel).addComponent(phoneLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(empIdTF).addComponent(usernameTF).addComponent(passwordTF).addComponent(roleTF).addComponent(dealershipNumTF).addComponent(firstNameTF).addComponent(lastNameTF).addComponent(emailTF).addComponent(streetTF).addComponent(cityTF).addComponent(zipTF).addComponent(stateTF).addComponent(phoneTF));

        layout.setHorizontalGroup(hGroup);
        
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(empIdLabel).addComponent(empIdTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel).addComponent(usernameTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel).addComponent(passwordTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(roleLabel).addComponent(roleTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(dealershipNumLabel).addComponent(dealershipNumTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(firstNameLabel).addComponent(firstNameTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lastNameLabel).addComponent(lastNameTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(emailLabel).addComponent(emailTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(streetLabel).addComponent(streetTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(cityLabel).addComponent(cityTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(zipLabel).addComponent(zipTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(stateLabel).addComponent(stateTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(phoneLabel).addComponent(phoneTF));

        layout.setVerticalGroup(vGroup);
        
        add (myMainPanel, BorderLayout.CENTER);
        setTitle(name);

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("ADD EMPLOYEE");
        submitButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(this);
        
        buttonP = new JPanel(new FlowLayout());
        buttonP.add(submitButton);
        buttonP.add(exitButton);
        add (buttonP, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(loginButton);
        
        setSize(300, 500);
        setVisible(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false); 

    }


    void buildAddCustomerGui(Frame frame, String name){

        myMainPanel = new JPanel();

        JLabel idLabel, notesLabel, empIdLabel, firstNameLabel, lastNameLabel, emailLabel, streetLabel, zipLabel, stateLabel, phoneLabel, cityLabel;
        
        GroupLayout layout = new GroupLayout(myMainPanel);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        idLabel = new JLabel("Customer ID: ");
        firstNameLabel = new JLabel("First Name: ");
        lastNameLabel = new JLabel("Last Name: ");
        emailLabel = new JLabel("Email: ");
        streetLabel = new JLabel("Street: ");
        zipLabel = new JLabel("Zip Code: ");
        cityLabel = new JLabel("City: ");
        stateLabel = new JLabel("State: ");
        phoneLabel = new JLabel("Phone Number: ");
        notesLabel = new JLabel("Customer Notes: ");
        empIdLabel = new JLabel("Assigned Employee ID: ");
        
        idTF = new JTextField(20);
        firstNameTF = new JTextField(20);
        lastNameTF = new JTextField(20);
        emailTF = new JTextField(20);
        streetTF = new JTextField(20);
        zipTF = new JTextField(20);
        cityTF = new JTextField(20);
        stateTF = new JTextField(20);
        phoneTF = new JTextField(20);

        notesTF = new JTextField(20);
        empIdTF = new JTextField(20);
        
        myMainPanel.setLayout (layout);
        
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        
        hGroup.addGroup(layout.createParallelGroup().addComponent(idLabel).addComponent(notesLabel).addComponent(empIdLabel).addComponent(firstNameLabel).addComponent(lastNameLabel).addComponent(emailLabel).addComponent(streetLabel).addComponent(cityLabel).addComponent(zipLabel).addComponent(stateLabel).addComponent(phoneLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(idTF).addComponent(notesTF).addComponent(empIdTF).addComponent(firstNameTF).addComponent(lastNameTF).addComponent(emailTF).addComponent(streetTF).addComponent(cityTF).addComponent(zipTF).addComponent(stateTF).addComponent(phoneTF));

        layout.setHorizontalGroup(hGroup);
        
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(idLabel).addComponent(idTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(firstNameLabel).addComponent(firstNameTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lastNameLabel).addComponent(lastNameTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(emailLabel).addComponent(emailTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(streetLabel).addComponent(streetTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(cityLabel).addComponent(cityTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(zipLabel).addComponent(zipTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(stateLabel).addComponent(stateTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(phoneLabel).addComponent(phoneTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(notesLabel).addComponent(notesTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(empIdLabel).addComponent(empIdTF));

        layout.setVerticalGroup(vGroup);
        
        add (myMainPanel, BorderLayout.CENTER);
        setTitle(name);

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("ADD CUSTOMER");
        submitButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(this);
        
        buttonP = new JPanel(new FlowLayout());
        buttonP.add(submitButton);
        buttonP.add(exitButton);
        add (buttonP, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(loginButton);
        
        setSize(300, 500);
        setVisible(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false); 
    }

    void buildLoginGui(Frame frame){
        loginButton = new JButton("Login");
        loginButton.setActionCommand("LOGIN BUTTON");
        loginButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(this);
        
        buttonP = new JPanel(new FlowLayout());
        buttonP.add(loginButton);
        buttonP.add(exitButton);
        add (buttonP, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(loginButton);
        
        setSize(300, 200);
        setVisible(true);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);


        myMainPanel = new JPanel();
        
        GroupLayout layout = new GroupLayout(myMainPanel);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        
        usernameTF = new JTextField(10);
        passwordTF = new JPasswordField(12);
        
        myMainPanel.setLayout (layout);
        
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        
        hGroup.addGroup(layout.createParallelGroup().addComponent(usernameLabel).addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(usernameTF).addComponent(passwordTF));
        
        layout.setHorizontalGroup(hGroup);
        
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel).addComponent(usernameTF));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel).addComponent(passwordTF));
        layout.setVerticalGroup(vGroup);
        
        add (myMainPanel, BorderLayout.CENTER);
        setTitle("Login");

    
    } // end of buildbasicGui

    void queryGui(){
        
        myMainPanel = new JPanel();
        
        GroupLayout layout = new GroupLayout(myMainPanel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        queryLabel = new JLabel("Username");
        queryTF = new JTextField(10);
        
        myMainPanel.setLayout (layout);
        
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        
        hGroup.addGroup(layout.createParallelGroup().addComponent(queryLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(queryTF));
        layout.setHorizontalGroup(hGroup);
        
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(queryLabel).addComponent(queryTF));
        layout.setVerticalGroup(vGroup);
        
        add (myMainPanel, BorderLayout.CENTER);
    
    } // end of buildbasicGui

    public void actionPerformed (ActionEvent e){    
        if (e.getActionCommand().equals("LOGIN BUTTON")){
            login(this.connectionHandler);
        }
        else if (e.getActionCommand().equals("EXIT")){
            this.dispose();
        }
        else if(e.getActionCommand().equals("ADD EMPLOYEE")){

            System.out.println("hey we're adding an employee (:");

            try{

            Connection con = this.mainFrame.connectionHandler.getConnection();
            CallableStatement cs = con.prepareCall("{CALL add_sales_emp(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            cs.setInt(1, Integer.parseInt(empIdTF.getText()));
            cs.setString(2, usernameTF.getText());
            cs.setString(3, passwordTF.getPassword().toString());
            cs.setString(4, roleTF.getText());
            cs.setInt(5, Integer.parseInt(dealershipNumTF.getText()));
            cs.setString(6, "'" + firstNameTF.getText() + "''");
            cs.setString(7, "'" + lastNameTF.getText() + "''");
            cs.setString(8, "'" + emailTF.getText() + "''");
            cs.setString(9, "'" + streetTF.getText() + "''");
            cs.setString(10, "'" + zipTF.getText() + "''");
            cs.setString(11, "'" + cityTF.getText() + "''");
            cs.setString(12, "'" + stateTF.getText() + "''");
            cs.setInt(13, Integer.parseInt(phoneTF.getText()));
            cs.executeUpdate();
            }
            catch(Exception ex){
                ex.printStackTrace();
                System.out.println("MEH");
            }

        }
        else if(e.getActionCommand().equals("ADD CUSTOMER")){
            System.out.println("hey we're adding a customer (:");


            try{

            if(this.mainFrame.connectionHandler == null){
                System.out.println("))))):");
            }
            
            Connection con = this.mainFrame.connectionHandler.getConnection();
            CallableStatement cs = con.prepareCall("{CALL add_customer(?,?,?,?,?,?,?,?,?,?,?)}");

            //CallableStatement cs = this.connectionHandler.conn.prepareCall("{CALL add_customer(?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1, Integer.parseInt(idTF.getText()));
            cs.setString(2, "'" + firstNameTF.getText() + "''");
            cs.setString(3, "'" + lastNameTF.getText() + "''");
            cs.setString(4, "'" + emailTF.getText() + "''");
            cs.setString(5, "'" + streetTF.getText() + "''");
            cs.setString(6, "'" + zipTF.getText() + "''");
            cs.setString(7, "'" + cityTF.getText() + "''");
            cs.setString(8, "'" + stateTF.getText() + "''");
            cs.setInt(9, Integer.parseInt(phoneTF.getText()));
            cs.setString(10, "'" + notesTF.getText() + "''");
            cs.setInt(11, Integer.parseInt(empIdTF.getText()));

            cs.executeUpdate();

            }
            catch(Exception ex){
                ex.printStackTrace();
                System.out.println("MEH");
            }

        }

    } 

public void login(ConnectionHandler connectionHandler){
    boolean loggedIn=false;
    boolean loginSucceeded = true;
    ResultSet resultSet = null;
    PreparedStatement pstatement = null;
        
        if (!loggedIn || this.connectionHandler.conn == null){    
            try{
                pstatement = this.connectionHandler.getConnection().prepareStatement("SELECT role FROM sales_emps S WHERE S.username = ? AND S.password = ?");
                pstatement.clearParameters();
                pstatement.setString(1, "" + usernameTF.getText() + "");
                pstatement.setString(2, new String(passwordTF.getPassword()));

                resultSet = this.connectionHandler.performQuery(pstatement);

                //process query Results
                //unsure if this is the best way to check if anything was returned
                if(!resultSet.next()) {
                    loginSucceeded = false;
                }
                else {
                    this.role = resultSet.getObject(1).toString();
                }
            }
            
            catch (Exception e){
                loginSucceeded = false;
                JOptionPane.showMessageDialog(this, "Login failed!", "Alert", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            
            if (loginSucceeded){            
                this.mainFrame.loginMenu.setText(usernameTF.getText() + " ");
                this.mainFrame.loginButton.setText("Logout");
                this.mainFrame.loginButton.setActionCommand("LOGOUT");
                System.out.println(role);
                if(this.role.equals("floor") || this.role.equals("internet")){
                    this.mainFrame.customerVisitsMenuItem.setVisible(true);
                    this.mainFrame.testDriveMenuItem.setVisible(true);
                    this.mainFrame.topFiveVehiclesMenuItem.setVisible(true);
                    this.mainFrame.testButton.setVisible(true);
                    this.mainFrame.inventoryButton.setVisible(true);
                }
                else if(this.role.equals("manager")){
                    this.mainFrame.customerVisitsMenuItem.setVisible(true);
                    this.mainFrame.testDriveMenuItem.setVisible(true);
                    this.mainFrame.topFiveVehiclesMenuItem.setVisible(true);
                    this.mainFrame.salesMenuItem.setVisible(true);
                    this.mainFrame.employeeInformationMenuItem.setVisible(true);
                    this.mainFrame.testButton.setVisible(true);
                    this.mainFrame.inventoryButton.setVisible(true);
                    this.mainFrame.role = "manager";

                }
                dispose();
                System.out.println("ROLE: " + this.mainFrame.role);
            }
            else {
                JOptionPane.showMessageDialog(this, "Login failed!", "Alert", JOptionPane.ERROR_MESSAGE);
            }
        }
}
public ConnectionHandler getConnectionHandler(){
    return this.connectionHandler;
}

public String getRole() {
    return this.role;
}
}