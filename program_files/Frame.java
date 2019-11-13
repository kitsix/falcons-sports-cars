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
class Frame extends JFrame
			implements ActionListener, WindowListener
{ 
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
    LoginDialog loginDialog;

    Vector<QueryResultsFrame> queryResultsFrameVector;    
    int queryResultsCount;

    boolean loggedIn;

    Frame()
    {
		loggedIn = false;
        
        this.addWindowListener(this);

		Container contentPane = getContentPane();

        fieldPanel = new JPanel();        

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
		customerVisitInfo.setActionCommand("CUSTOMER_VISIT_INFO");

        testDrivenInfo = new JButton("Test-Driven Vehicles Info");
        testDrivenInfo.addActionListener(this);
        testDrivenInfo.setActionCommand("TEST_DRIVEN_VEHICLES");

        topFiveVehiclesInfo = new JButton("Top 5 Vehicles");
        topFiveVehiclesInfo.addActionListener(this);
        topFiveVehiclesInfo.setActionCommand("TOP_FIVE_VEHICLES");

        totalSalesInfo = new JButton("Total Sales Per Dealership");
        totalSalesInfo.addActionListener(this);
        totalSalesInfo.setActionCommand("TOTAL_DEALERSHIP_SALES");

        salesPeopleInfo = new JButton("Sales Employee Info");
        salesPeopleInfo.addActionListener(this);
        salesPeopleInfo.setActionCommand("SALES_EMPS_INFO");

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
        queryResultsFrameVector = new Vector<QueryResultsFrame>();
        
        queryResultsCount = 0;

        JMenuBar menuBar = new JMenuBar();
        //ImageIcon exitIcon = new ImageIcon("src/resources/exit.png");
        //TBA (:

        JMenu fileMenu = new JMenu("Hello");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit"/*, exitIcon*/);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((event) -> System.exit(0));

        fileMenu.add(eMenuItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
        this.setVisible(true);

		login.setEnabled(true);
		logout.setEnabled(false);
		register.setEnabled(false);
        query.setEnabled(false);
        customerVisitInfo.setEnabled(false);
        testDrivenInfo.setEnabled(false);
        topFiveVehiclesInfo.setEnabled(false);
        totalSalesInfo.setEnabled(false);
        salesPeopleInfo.setEnabled(false);

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
                customerVisitInfo.setEnabled(false);
                testDrivenInfo.setEnabled(false);
                topFiveVehiclesInfo.setEnabled(false);
                totalSalesInfo.setEnabled(false);
                salesPeopleInfo.setEnabled(false);
            }
        }

        else
			JOptionPane.showMessageDialog(this, "You are already logged out!", "Alert", JOptionPane.INFORMATION_MESSAGE);

	}

	public void register()
	{
		System.out.println("Frame: REGISTER");
        loginDialog = new LoginDialog();
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
    
    public void performQueryAndDisplayResults(String query)
    {
        try
        {            
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

        //usernameField.setText("");
        //passwordField.setText("");
	}

    public void exit()
    {
		System.out.println("Frame: EXIT");
        
        // if (loggedIn || connectionHandler.conn != null)
		// {
		// 	logout();
		// }

		System.exit(0);

        //navigationBar = new NavigationBar();
    }

    public void removeQueryResultsFrame(QueryResultsFrame queryResultsFrameToRemove)
    {
        System.out.println("Frame: QUERY_RESULTS_FRAME_CLOSING");
        
        queryResultsFrameVector.removeElement(queryResultsFrameToRemove);
    }
    
    public void closeQueryResultsFrames()
    {
        System.out.println("Frame: QUERY_RESULTS_FRAME_CLOSING");

        loginDialog = new LoginDialog();

        
        // for (QueryResultsFrame x : queryResultsFrameVector)
        //     x.dispose();
        
        // queryResultsCount = 0;        
        // queryResultsFrameVector.removeAllElements();        
    }
    
    public void getCustomerVisitInfo()
    {
        System.out.println("Frame: CUSTOMER_VISIT_INFO");
        
        String customerID = (JOptionPane.showInputDialog(this, "Enter the customer's ID:")).trim();
                                
        if (!customerID.equals(""))
        {
            try
            {
                String query = "SELECT CI.id, CI.first_name, CI.last_name, CI.customer_notes, CTDV.dealership_number, D.street, D.zip, CTDV.stock_number, CTDV.datetime " +
                                "FROM customers_info CI, customers_test_driven_vehicles CTDV, dealerships D "  + 
                                "WHERE CI.id = \'" + customerID + "\' AND CI.id = CTDV.id AND CTDV.dealership_number = D.dealership_number";
                
                performQueryAndDisplayResults(query);
            }
                
            catch (Exception e)
            {
                e.printStackTrace();
            }        
        }
        
        else
            JOptionPane.showMessageDialog(this, "You cannot enter an empty string for the customer's ID!", "Alert", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void getTestDrivenVehicles()
    {
        System.out.println("Frame: TEST_DRIVEN_VEHICLES");
        
        String response = (JOptionPane.showInputDialog(this, "Enter the make and model separated by a space:")).trim();
                
        String components[] = response.split(" ");
        String make = components[0];
        String model = components[components.length - 1];
        
        if (!(make.equals("") && model.equals("")))
        {
            String query = "SELECT D.dealership_number, C.id, P.first_name, P.last_name, TD.stock_number, V.make, V.model, TD.datetime " +
                            "FROM customers C, people P, sales_emps E, test_drives TD, vehicles V, dealerships D " +
                            "WHERE V.make = \'" + make + "\' AND V.model = \'" + model + "\' AND V.stock_number = TD.stock_number AND " +
                            "TD.customer_id = C.id AND C.id = P.id AND C.assigned_emp_id = E.id AND E.dealership_number = D.dealership_number";
            
            performQueryAndDisplayResults(query);
        }
        
        else
            JOptionPane.showMessageDialog(this, "You cannot enter an empty string for the make and model!", "Alert", JOptionPane.INFORMATION_MESSAGE);
    }
        
    public void getTopFiveVehicles()
    {
        System.out.println("Frame: TOP_FIVE_VEHICLES");                                

        try
        {
            String query = "SELECT COUNT(*) AS num_sold_vehicles, V.make, V.model, V.year, V.new " +
                            "FROM vehicles V, purchase_vehicle PV " +
                            "WHERE PV.stock_number = V.stock_number " +
                            "GROUP BY V.make, V.model, V.year, V.new " +
                            "ORDER BY num_sold_vehicles DESC";
            
            performQueryAndDisplayResults(query);
        }
            
        catch (Exception e)
        {
            e.printStackTrace();
        }        
    }
        
    public void getTotalDealershipSales()
    {
        System.out.println("Frame: TOTAL_DEALERSHIP_SALES");                                

        try
        {
            String query = "SELECT D.dealership_number, SUM(V.price) AS total_amount_sold " +
                            "FROM vehicles V, purchase_vehicle PV, dealerships D " +
                            "WHERE PV.stock_number = V.stock_number AND V.dealership_number = D.dealership_number AND MONTH(V.sale_datetime) = MONTH(NOW()) " +
                            "GROUP BY D.dealership_number " +
                            "ORDER BY total_amount_sold DESC";
            
            performQueryAndDisplayResults(query);
        }
            
        catch (Exception e)
        {
            e.printStackTrace();
        }        
    }
        
    public void getSalesEmpsInfo()
    {
        System.out.println("Frame: SALES_EMPS_INFO");                                

        try
        {
            String query = "SELECT SEI.id, SEI.dealership_number, SEI.first_name, SEI.last_name, SEI.email, SEI.street, SEI.zip, SEI.phone_number, TDSE.test_drives, SSE.sales, CSE.commissions_total " +
                            "FROM sales_emps_info SEI, test_drives_per_sales_emp_current_year TDSE, sales_per_sales_emp_current_year SSE, commissions_per_sales_emp_current_year CSE " +
                            "WHERE SEI.id = TDSE.id AND SEI.id = SSE.id AND SEI.id = CSE.id " +
                            "ORDER BY CSE.commissions_total DESC";
            
            performQueryAndDisplayResults(query);
        }
            
        catch (Exception e)
        {
            e.printStackTrace();
        }        
    }

	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();

		try
		{
            if (cmd.equals("LOGIN"))
				System.out.println("hi login button was pressed.");

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
            
            else if (cmd.equals("CUSTOMER_VISIT_INFO"))
                getCustomerVisitInfo();
            
            else if (cmd.equals("TEST_DRIVEN_VEHICLES"))
                getTestDrivenVehicles();
            
            else if (cmd.equals("TOP_FIVE_VEHICLES"))
                getTopFiveVehicles();
            
            else if (cmd.equals("TOTAL_DEALERSHIP_SALES"))
                getTotalDealershipSales();
            
            else if (cmd.equals("SALES_EMPS_INFO"))
                getSalesEmpsInfo();
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

		// if (loggedIn || connectionHandler.conn != null)
		// {
		// 	logout();
		// }
        
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