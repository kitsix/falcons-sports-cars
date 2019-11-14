import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JDialog;

class LoginDialog  extends  JDialog
implements ActionListener
{
    String role;
    JTextField              usernameTF;
    JPasswordField          passwordTF;
    JLabel                  usernameLabel, passwordLabel;
    JPanel                  myMainPanel, buttonP;
    JButton                 registerButton, loginButton, exitButton;
    ConnectionHandler       connectionHandler;
    JFrame                  mainFrame;

    LoginDialog(JFrame frame, ConnectionHandler connectionHandler){

        this.connectionHandler = connectionHandler;
        this.mainFrame = frame;
        
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
    ResultSet resultSet = null;
    PreparedStatement pstatement = null;
        
        if (!loggedIn || connectionHandler.conn == null){    
            try{
                pstatement = connectionHandler.getConnection().prepareStatement("SELECT role FROM sales_emps S WHERE S.username = ? AND S.password = ?");
                pstatement.clearParameters();
                pstatement.setString(1, "" + usernameTF.getText() + "");
                pstatement.setString(2, new String(passwordTF.getPassword()));

                resultSet = connectionHandler.performQuery(pstatement);

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
                loggedIn = true;
                System.out.println("Login successfull");
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