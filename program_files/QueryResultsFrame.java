import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;

// This is a simple class for displaying query results.
// Note that each instance of this class is stored in a the queryResultsFrameVector data member of the Frame class.
// This is to allow a user to dispose of all currently open QueryResultsFrame with a single click of the "Close Query Results" button.
class QueryResultsFrame extends JFrame
	implements WindowListener
{
	Frame host;
    
    JLabel queryLabel;
    JTextArea queryArea;
    JScrollPane queryAreaScrollPane = null;
    
    JLabel queryResultsTableLabel;
    JTable queryResultsTable;
    JScrollPane queryResultsTableScrollPane = null;

    JPanel mainPanel;

    GroupLayout layout;

    QueryResultsFrame(Frame host, String query, ResultSet resultSet)
	{        
        this.host = host;
        this.addWindowListener(this);
    
		Container contentPane = getContentPane();

        try
        {   
            queryArea = new JTextArea(query);
            queryArea.setEditable(false);
            queryAreaScrollPane = new JScrollPane(queryArea);        
     
            Vector<Object> columnNames = new Vector<Object>();
            Vector<Object> rows = new Vector<Object>();
            
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                columnNames.addElement(resultSetMetaData.getColumnName(i));
            
            while (resultSet.next())
            {
                Vector<Object> currentRow = new Vector<Object>();

                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                    currentRow.addElement(resultSet.getObject(i));
                
                rows.addElement(currentRow);
            }
            
            queryResultsTable = new JTable(rows, columnNames);
            queryResultsTableScrollPane = new JScrollPane(queryResultsTable);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }

		mainPanel = new JPanel();

		layout = new GroupLayout(mainPanel);
		mainPanel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

		hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addComponent(queryAreaScrollPane)
            .addComponent(queryResultsTableScrollPane));

		layout.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

		vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(queryAreaScrollPane));

		vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(queryResultsTableScrollPane));

        layout.setVerticalGroup(vGroup);

        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        Toolkit tk;
		Dimension d;

		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
        
        int queryResultsFrameWidth = d.width/2;
        int queryResultsFrameHeight = d.height/2;

		setSize(queryResultsFrameWidth, queryResultsFrameHeight);
		setLocation((d.width - (d.width/2 + 4)), d.height/2 - queryResultsFrameHeight/2);        
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void windowActivated(WindowEvent e)
	{
	}

	public void windowClosed(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		System.out.println("QueryResultsFrame: windowClosing");
        
        host.actionPerformed(new ActionEvent(this, 0, "QUERY_RESULTS_FRAME_CLOSING"));
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
}