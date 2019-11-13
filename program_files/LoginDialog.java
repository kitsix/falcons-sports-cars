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
JLabel                  usernameLabel;
JLabel                  passwordLabel;
JPanel                  myMainPanel;
JPanel                  buttonP;
JButton                 registerButton;
JButton                 loginButton;


LoginDialog()
{
    System.out.println("login dialog created hello");
    buttonP = new JPanel(new FlowLayout());
    
    buildBasicGui(); // method call
    
    registerButton = new JButton("register");
    loginButton = new JButton("login");
    
    registerButton.setActionCommand("REGISTER BUTTON");
    loginButton.setActionCommand("LOGIN BUTTON");
    
    registerButton.addActionListener(this);
    loginButton.addActionListener(this);
    
    buttonP.add(registerButton);
    buttonP.add(loginButton);
    add (buttonP, BorderLayout.SOUTH);
    
    setSize(300, 300);
    setVisible(true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);
    
} // end of constructor
//---------------------------------------------------------------------------------------------------------------

void buildBasicGui()
{
    myMainPanel = new JPanel();
    
    GroupLayout layout = new GroupLayout(myMainPanel);
    
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    
    usernameLabel = new JLabel("username");
    passwordLabel = new JLabel("password");
    
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
    //setupMainDialog();
    
} // end of buildbasicGui
//----------------------------------------------------------------------------------------------------------------

public void actionPerformed (ActionEvent e)
{
    String username = usernameTF.getText().trim();
    String password = new String(passwordTF.getPassword());
    
    
    
    if (e.getActionCommand().equals("REGISTER BUTTON"))
    {
        System.out.println("registering");

        
        
    }
    
    else if (e.getActionCommand().equals("LOGIN BUTTON"))
    {
        System.out.println("goodbye");
    } // end of else if
    
} 
}