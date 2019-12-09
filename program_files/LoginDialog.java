import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JDialog;

class LoginDialog  extends  JDialog
implements ActionListener
{
    String                  role;
    JTextField              usernameTF, queryTF;
    JPasswordField          passwordTF;
    JLabel                  usernameLabel, passwordLabel, queryLabel;
    JPanel                  myMainPanel, buttonP;
    JButton                 loginButton, exitButton, submitButton;
    ConnectionHandler       connectionHandler;
    Frame                   mainFrame;
    String                  tempUserName;
    boolean                 loggedIn;
    JTextField idTF, notesTF, empIdTF, firstNameTF, lastNameTF, emailTF, streetTF, zipTF, cityTF, stateTF, phoneTF;



    LoginDialog(Frame frame, String name){

        this.mainFrame = frame;
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

    LoginDialog(Frame frame, ConnectionHandler connectionHandler){

        this.connectionHandler = connectionHandler;
        this.mainFrame = frame;
        
        buildBasicGui(); 

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
}

    void buildBasicGui(){
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
        else if(e.getActionCommand().equals("ADD CUSTOMER")){
            System.out.println("hey we're adding a customer (:");

            String query = "CALL add_customer(" +
                idTF.getText() + "," +
                firstNameTF.getText() + "," +
                lastNameTF.getText() + "," +
                emailTF.getText() + "," +
                streetTF.getText() + "," +
                zipTF.getText() + "," +
                cityTF.getText() + "," +
                stateTF.getText() + "," +
                phoneTF.getText() + "," +
                notesTF.getText() + "," +
                empIdTF.getText() + ");";
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
                    role = resultSet.getObject(1).toString();
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
                if(role.equals("floor") || role.equals("internet")){
                    this.mainFrame.customerVisitsMenuItem.setVisible(true);
                    this.mainFrame.testDriveMenuItem.setVisible(true);
                    this.mainFrame.topFiveVehiclesMenuItem.setVisible(true);
                }
                else if(role.equals("manager")){
                    this.mainFrame.customerVisitsMenuItem.setVisible(true);
                    this.mainFrame.testDriveMenuItem.setVisible(true);
                    this.mainFrame.topFiveVehiclesMenuItem.setVisible(true);
                    this.mainFrame.salesMenuItem.setVisible(true);
                    this.mainFrame.employeeInformationMenuItem.setVisible(true);

                }
                dispose();
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
    return role;
}
}