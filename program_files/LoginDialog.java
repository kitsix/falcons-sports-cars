import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.GroupLayout.*;
import java.lang.Object;

class LoginDialog  extends  JDialog
implements ActionListener
{
    JTextField              usernameTF;
    JPasswordField          passwordTF;
    JLabel                  usernameLabel, passwordLabel;
    JPanel                  myMainPanel, buttonP;
    JButton                 registerButton, loginButton, exitButton;
    ConnectionHandler       connectionHandler;

    LoginDialog(JFrame frame){

        connectionHandler = new ConnectionHandler();
        
        buildBasicGui(); 
        
        registerButton = new JButton("Register");
        registerButton.setActionCommand("REGISTER BUTTON");
        registerButton.addActionListener(this);

        loginButton = new JButton("Login");
        loginButton.setActionCommand("LOGIN BUTTON");
        loginButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(this);
        
        buttonP = new JPanel(new FlowLayout());
        buttonP.add(registerButton);
        buttonP.add(loginButton);
        buttonP.add(exitButton);
        add (buttonP, BorderLayout.SOUTH);
        
        setSize(300, 300);
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
    
    } // end of buildbasicGui

    public void actionPerformed (ActionEvent e){
        String username = usernameTF.getText().trim();
        String password = new String(passwordTF.getPassword());
        
        if (e.getActionCommand().equals("REGISTER BUTTON")){
            System.out.println("registering");
        }
        
        else if (e.getActionCommand().equals("LOGIN BUTTON")){
            login(this.connectionHandler);
        }
        else if (e.getActionCommand().equals("EXIT")){
            this.dispose();
        }
    } 

    public void login(ConnectionHandler connectionHandler){
        boolean loggedIn=false;
        boolean loginSucceeded = true;
            
            if (!loggedIn || connectionHandler.conn == null){    
                try{
                    // connectionHandler.setConnectionProperties("java_test_user", "pass", "127.0.0.1", 3306, "java_db_test", "MySQL"); // This is for my local database.
                    // connectionHandler.setConnectionProperties("admin", "Hossain123", "db-falcon-sports-cars.cginpqx3xobn.us-east-1.rds.amazonaws.com", 3306, "", "MySQL"); // This is for the AWS RDS.
                    // connectionHandler.setConnectionProperties(usernameField.getText(), new String(passwordField.getPassword()), "db-falcon-sports-cars.cginpqx3xobn.us-east-1.rds.amazonaws.com", 3306, "", "MySQL"); // This is for the AWS RDS except that it gets the login credentials from the username and password fields.
                    //connectionHandler.setConnectionProperties(usernameTF.getText(), new String(passwordTF.getPassword()), "127.0.0.1", 3306, "4410_db_schema", "MySQL"); // Again, this is for my test setup.
                    connectionHandler.setConnectionProperties("root", "littlewhale", "localhost", 3306, "falconcars", "MySQL"); // Again, this is for my test setup.
                    connectionHandler.createJdbcUrl();
                    connectionHandler.establishConnection();
                }
                
                catch (Exception e){
                    loginSucceeded = false;
                    JOptionPane.showMessageDialog(this, "Login failed!", "Alert", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                
                if (loginSucceeded){            
                    loggedIn = true;
                    dispose();
                }
                else
                JOptionPane.showMessageDialog(this, "You are already logged in!", "Alert", JOptionPane.INFORMATION_MESSAGE);
            }
    }
    public ConnectionHandler getConnectionHandler(){
        return this.connectionHandler;
    }
}