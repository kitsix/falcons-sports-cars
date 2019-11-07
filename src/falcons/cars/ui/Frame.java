package falcons.cars.ui;

import falcons.cars.connector.*;
import falcons.cars.ui.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;

// You will need Connector/J installed on your system and you will need to specify the installation path when you run the program.
// I do this through command line as follows -- the general format is as follows:

// "java -cp ___________________;. Frame" (where "___________________" is your path to Connector/J)

// Example command line commands to compile and run for my setup are as follows:
// javac *.java
// java -cp D:\MySQL\Installation\Connector_J_8_0\mysql-connector-java-8.0.17.jar;. Frame
//
// -------------------------------------------------------------------------------------------------------------------------------------
//
// This class is responsible for:
// 1: providing the baseline GUI that allows a user to:
//      - login/logout of the DBMS
//      - execute queries (which they must enter into the QueryFrame's TextArea instance)
//      - query results are displayed in QueryResultsFrame instances which can all be disposed via the "Close Query Results" JButton
// 2: correctly managing the ConnectionHandler class (making sure that connections are opened/closed when appropriate)
// 3: correctly managing instances of the QueryFrame and QueryResultsFrame classes 
// 4: correctly transferring information (such as ResultSet objects) from the ConnectionHandler to the various other classes
//
// Currently only the following buttons are functional:
// login, logout, query, clear, closeQueryResults, and exit.
public class Frame extends JFrame
			implements ActionListener, WindowListener
{
    protected String username;
	protected String password;

	boolean loggedIn;
    
    JLabel usernameLabel;    
    JTextField usernameField;
    JPanel usernamePanel;
    
    JLabel passwordLabel;
    JPasswordField passwordField;
    JPanel passwordPanel;
    
    JPanel fieldPanel;
    
	JButton login;
	JButton logout;
	JButton register;
    JButton query;
    JButton clear;
    JButton closeQueryResults;
	JButton exit;
    
    JPanel buttonPanel;

    JButton customerVisitInfo;
    JButton testDrivenInfo;
    JButton topFiveVehiclesInfo;
    JButton totalSalesInfo;
    JButton salesPeopleInfo; 
    
    JPanel queryButtonPanel;
    
    JPanel mainPanel;
	GroupLayout layout;
    
    QueryFrame queryFrame; 
    ConnectionHandler connectionHandler;

    Vector<QueryResultsFrame> queryResultsFrameVector;    
    int queryResultsCount;

    Frame()
    {
		loggedIn = false;
        
        this.addWindowListener(this);

		Container contentPane = getContentPane();
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField("java_test_user", 25); // DEFAULTING TO A VALUE FOR TESTING         
        usernamePanel = new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField("pass", 25); // DEFAULTING TO A VALUE FOR TESTING       
        passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        fieldPanel = new JPanel();        
        fieldPanel.add(usernamePanel);
        fieldPanel.add(passwordPanel);

		login = new JButton("Login");
		login.addActionListener(this);
		login.setActionCommand("LOGIN");

		logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setActionCommand("LOGOUT");

		register = new JButton("Register");
		register.addActionListener(this);
		register.setActionCommand("REGISTER");
        
        query = new JButton("Query");
        query.addActionListener(this);
        query.setActionCommand("DISPLAY_QUERY_FRAME");
        
        clear = new JButton("Clear");
        clear.addActionListener(this);
        clear.setActionCommand("CLEAR");
        
        closeQueryResults = new JButton("Close Query Results");
        closeQueryResults.addActionListener(this);
        closeQueryResults.setActionCommand("CLOSE_QUERY_RESULTS_FRAMES");
        
        exit = new JButton("Exit");
        exit.addActionListener(this);
        exit.setActionCommand("EXIT");

        buttonPanel = new JPanel();
        buttonPanel.setAlignmentY(LEFT_ALIGNMENT);
        buttonPanel.add(login);
        buttonPanel.add(logout);
        buttonPanel.add(register);
        buttonPanel.add(query);
        buttonPanel.add(closeQueryResults);
        buttonPanel.add(exit);

		customerVisitInfo = new JButton("Customer Visit Info");
		customerVisitInfo.addActionListener(this);
		// customerVisitInfo.setActionCommand("EXIT");

        testDrivenInfo = new JButton("Test-Driven Vehicles Info");
        testDrivenInfo.addActionListener(this);
        // testDrivenInfo.setActionCommand("EXIT");

        topFiveVehiclesInfo = new JButton("Top 5 Vehicles");
        topFiveVehiclesInfo.addActionListener(this);
        // topFiveVehiclesInfo.setActionCommand("EXIT");

        totalSalesInfo = new JButton("Total Sales Per Dealership");
        totalSalesInfo.addActionListener(this);
        // totalSalesInfo.setActionCommand("EXIT");

        salesPeopleInfo = new JButton("Sales Employee Info");
        salesPeopleInfo.addActionListener(this);
        // salesPeopleInfo.setActionCommand("EXIT");

        queryButtonPanel = new JPanel();
        queryButtonPanel.add(customerVisitInfo);
        queryButtonPanel.add(testDrivenInfo);
        queryButtonPanel.add(topFiveVehiclesInfo);
        queryButtonPanel.add(totalSalesInfo);
        queryButtonPanel.add(salesPeopleInfo);

		getRootPane().setDefaultButton(login);
        
        mainPanel = new JPanel();

		layout = new GroupLayout(mainPanel);

		mainPanel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

		hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(fieldPanel)
            .addComponent(buttonPanel)
            .addComponent(queryButtonPanel));
            
		layout.setHorizontalGroup(hGroup);
        
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

		vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(fieldPanel));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(buttonPanel));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(queryButtonPanel));

		layout.setVerticalGroup(vGroup);

        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        queryFrame = new QueryFrame(this);
        connectionHandler = new ConnectionHandler();
        queryResultsFrameVector = new Vector<QueryResultsFrame>();
        
        queryResultsCount = 0;

		login.setEnabled(true);
		logout.setEnabled(false);
		register.setEnabled(false);
        query.setEnabled(false);

        Toolkit tk;
		Dimension d;

		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();

        int frameWidth = 840;
        int frameHeight = 200;

		setSize(frameWidth, frameHeight);
		setLocation(d.width/2 - frameWidth/2, d.height/8 - frameHeight/2);
		setTitle("Frame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);        
        setResizable(false);
    }

	public void login()
	{
		System.out.println("Frame: LOGIN");
        
        boolean loginSucceeded = true;
        
        if (!loggedIn || connectionHandler.conn == null)
        {    
            try
            {
                // connectionHandler.setConnectionProperties("java_test_user", "pass", "127.0.0.1", 3306, "java_db_test", "MySQL"); // This is for my local database.
                // connectionHandler.setConnectionProperties("admin", "Hossain123", "db-falcon-sports-cars.cginpqx3xobn.us-east-1.rds.amazonaws.com", 3306, "", "MySQL"); // This is for the AWS RDS.
                // connectionHandler.setConnectionProperties(usernameField.getText(), new String(passwordField.getPassword()), "db-falcon-sports-cars.cginpqx3xobn.us-east-1.rds.amazonaws.com", 3306, "", "MySQL"); // This is for the AWS RDS except that it gets the login credentials from the username and password fields.

                connectionHandler.setConnectionProperties(usernameField.getText(), new String(passwordField.getPassword()), "127.0.0.1", 3306, "java_db_test", "MySQL"); // Again, this is for my test setup.
                connectionHandler.createJdbcUrl();
                connectionHandler.establishConnection();
            }
            
            catch (Exception e)
            {
                loginSucceeded = false;
                
                JOptionPane.showMessageDialog(this, "Login failed!", "Alert", JOptionPane.ERROR_MESSAGE);
                
                e.printStackTrace();
            }
            
            if (loginSucceeded)
            {            
                loggedIn = true;
                        
                login.setEnabled(false);
                logout.setEnabled(true);
                register.setEnabled(true);
                query.setEnabled(true);
            }
        }

        else
			JOptionPane.showMessageDialog(this, "You are already logged in!", "Alert", JOptionPane.INFORMATION_MESSAGE);
	}

 	public void logout()
	{
		System.out.println("Frame: LOGOUT");
        
        boolean logoutSucceeded = true;
        
        if (loggedIn || connectionHandler.conn != null)
        {    
            try
            {
                connectionHandler.closeConnection();
                connectionHandler.resetConnectionProperties();
            }
            
            catch (Exception e)
            {
                logoutSucceeded = false;
                
                JOptionPane.showMessageDialog(this, "Logout failed!", "Alert", JOptionPane.ERROR_MESSAGE);
                
                e.printStackTrace();
            }
            
            if (logoutSucceeded)
            {  
                loggedIn = false;
                
                queryFrame.setVisible(false);
                
                login.setEnabled(true);
                logout.setEnabled(false);
                register.setEnabled(false);
                query.setEnabled(false);
            }
        }

        else
			JOptionPane.showMessageDialog(this, "You are already logged out!", "Alert", JOptionPane.INFORMATION_MESSAGE);

	}

	public void register()
	{
		System.out.println("Frame: REGISTER");
	}
    
    public void displayQueryFrame()
	{
		System.out.println("Frame: DISPLAY_QUERY_FRAME");

		if (loggedIn)
		{
			queryFrame.setVisible(true);
		}
	}
    
    public void performQuery()
    {
        try
        {
            System.out.println("Frame: PERFORM_QUERY");
            
            String query = queryFrame.getQuery();
            
            ResultSet resultSet = connectionHandler.performQuery(query);
            
            QueryResultsFrame queryResultsFrame = new QueryResultsFrame(this, query, resultSet);  
            
            queryResultsCount += 1;
            
            queryResultsFrameVector.addElement(queryResultsFrame);        
            queryResultsFrame.setTitle("Query Results Frame #" + queryResultsCount);
        }
            
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

    public void clear()
	{
		System.out.println("Frame: CLEAR");

        usernameField.setText("");
        passwordField.setText("");
	}

    public void exit()
    {
		System.out.println("Frame: EXIT");
        
        if (loggedIn || connectionHandler.conn != null)
		{
			logout();
		}

		System.exit(0);
    }

    public void removeQueryResultsFrame(QueryResultsFrame queryResultsFrameToRemove)
    {
        System.out.println("Frame: QUERY_RESULTS_FRAME_CLOSING");
        
        queryResultsFrameVector.removeElement(queryResultsFrameToRemove);
    }
    
    public void closeQueryResultsFrames()
    {
        System.out.println("Frame: QUERY_RESULTS_FRAME_CLOSING");
        
        for (QueryResultsFrame x : queryResultsFrameVector)
            x.dispose();
        
        queryResultsCount = 0;        
        queryResultsFrameVector.removeAllElements();        
    }

	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();

		try
		{
            if (cmd.equals("LOGIN"))
				login();

			else if (cmd.equals("LOGOUT"))
				logout();

			else if (cmd.equals("REGISTER"))
				register();

            else if (cmd.equals("DISPLAY_QUERY_FRAME"))
				displayQueryFrame();
            
            else if (cmd.equals("PERFORM_QUERY"))
				performQuery();

        	else if (cmd.equals("EXIT"))
            	exit();
            
            else if (cmd.equals("QUERY_RESULTS_FRAME_CLOSING"))
                removeQueryResultsFrame((QueryResultsFrame)e.getSource());
            
            else if (cmd.equals("CLOSE_QUERY_RESULTS_FRAMES"))
                closeQueryResultsFrames();
		}

		catch (Exception x)
		{
			x.printStackTrace();

			System.out.println("Frame: actionPerformed(): Exception");
		}
	}

    public void windowActivated(WindowEvent e)
	{
	}

	public void windowClosed(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{        
		System.out.println("Frame: windowClosing");

		if (loggedIn || connectionHandler.conn != null)
		{
			logout();
		}
        
        System.exit(0);
	}

	public void windowDeactivated(WindowEvent e)
	{
	}

	public void windowDeiconified(WindowEvent e)
	{
	}

	public void windowIconified(WindowEvent e)
	{
	}

	public void windowOpened(WindowEvent e)
	{
	}

	public static void main(String[] x)
	{
		new Frame();
	}
}