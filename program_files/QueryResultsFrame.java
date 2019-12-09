import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

// This is a simple class for displaying query results.
// Note that each instance of this class is stored in a the queryResultsFrameVector data member of the Frame class.
// This is to allow a user to dispose of all currently open QueryResultsFrame with a single click of the "Close Query Results" button.
class QueryResultsFrame extends JFrame
	implements WindowListener, ActionListener, TableModelListener
{
	Frame 				host;
	LoginDialog			dialog;
    JLabel 				queryLabel, queryResultsTableLabel;
    JScrollPane 		queryAreaScrollPane = null;
    JTable 				queryResultsTable;
    JScrollPane 		queryResultsTableScrollPane = null;
    JPanel 				mainPanel;
	GroupLayout 		layout;
	DefaultTableModel   model;
	String				tableName, queryName;

    QueryResultsFrame(Frame host, PreparedStatement pStatement, ResultSet resultSet, String queryName){        
		
		this.host = host;
		this.queryName = queryName;
		this.addWindowListener(this);    
		Container contentPane = getContentPane();
		boolean printImage = false;

        try{   
            Vector columnNames = new Vector<Object>();
            Vector rows = new Vector<Object>();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			tableName = resultSetMetaData.getTableName(1);
            
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++){
				columnNames.addElement(resultSetMetaData.getColumnName(i));
				if(resultSetMetaData.getColumnName(i).equals("image")){
					printImage = true;
					System.out.println("image att detected.");
				}
			}
            
            while (resultSet.next()){
                Vector<Object> currentRow = new Vector<Object>();

                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++){
					currentRow.addElement(resultSet.getObject(i));						
				}
				rows.addElement(currentRow);
				
				if(printImage == true)
					{
						Blob imageBlob = resultSet.getBlob("image");
						InputStream binaryStream = null;
						try {
								if (imageBlob != null && imageBlob.length() > 0) {
								binaryStream = imageBlob.getBinaryStream();
							}
						} catch (Exception ignore) {
						}
						if(binaryStream != null){
							System.out.println("THERE IS AN IMAGE");
			
						BufferedImage bimage = ImageIO.read(binaryStream);
						Image image = bimage;

						//ImageIcon icon = new ImageIcon(image);
						//rows.addElement(image);
			
						JPanel imagePanel = new JPanel(){
						@Override
						protected void paintComponent(Graphics g) {
							super.paintComponent(g);
							g.drawImage(image, 50, 50, 100, 100, this);
						}
					};
						ImageFrame test = new ImageFrame(this);
						test.add(imagePanel);
						imagePanel.repaint(); 
					}}
			}

			model = new DefaultTableModel(rows, columnNames)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
			}

			public String getText(int column){
				return getColumnName(column);
			}

			public String getID(int row){
				return getValueAt(row, 0).toString();
			}
		};


		// AbstractTableModel abstractModel = new AbstractTableModel() {
		// 	public String getColumnName(int col) {
		// 		return columnNames[col].toString();
		// 	}
		// 	public int getRowCount() { return rowData.length; }
		// 	public int getColumnCount() { return columnNames.length; }
		// 	public Object getValueAt(int row, int col) {
		// 		return rowData[row][col];
		// 	}
		// 	public boolean isCellEditable(int row, int col)
		// 		{ return true; }
		// 	public void setValueAt(Object value, int row, int col) {
		// 		rowData[row][col] = value;
		// 		fireTableCellUpdated(row, col);
		// 	}
		// }
            
            queryResultsTable = new JTable(model);
			queryResultsTableScrollPane = new JScrollPane(queryResultsTable);


			model.addTableModelListener(this);


			queryResultsTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent me) {
					if(me.getClickCount() == 1) {
						System.out.println("one click");
						int row = queryResultsTable.getSelectedRow();
						int col = queryResultsTable.getSelectedColumn();
						String link = queryResultsTable.getValueAt(row, col).toString();
						String test = queryResultsTable.getValueAt(row, col).toString();
						Object testImage = queryResultsTable.getValueAt(row, col);
						Desktop desktop = java.awt.Desktop.getDesktop();
						if(test.startsWith("[B"))
							System.out.println("hacked");
						if(link.startsWith("https")) {
							try {
								URI uri = new URI(link);
								desktop.browse(uri);
								
							}
							catch(URISyntaxException use) {
								use.printStackTrace();
							}
							catch(IOException ioe) {
								ioe.printStackTrace();
							}
						}
					}

					if(me.getClickCount() == 2){
						System.out.println("you are going to edit something now");
					}
				}
			});
        }
        
        catch (Exception e){
            e.printStackTrace();
        }

		mainPanel = new JPanel();
		layout = new GroupLayout(mainPanel);
		mainPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(queryResultsTableScrollPane));
		layout.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(queryResultsTableScrollPane));
        layout.setVerticalGroup(vGroup);

		JButton testButton;
		testButton = new JButton("Add");
		testButton.addActionListener(this);
		testButton.setActionCommand("GO");

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		deleteButton.setActionCommand("DELETE");

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(testButton);
		buttonPanel.add(deleteButton);

		contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        Toolkit tk;
		Dimension d;

		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
        
        int queryResultsFrameWidth = d.width/2;
        int queryResultsFrameHeight = d.height/2;

		setSize(queryResultsFrameWidth, queryResultsFrameHeight);
		setLocationRelativeTo(this.host);      
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setTitle(queryName);
	}

	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();

		if(cmd.equals("GO")){
			//dialog = new LoginDialog(this.host);
			dialog = new LoginDialog(this.host, "hi");
		}
		else if(cmd.equals("DELETE")){
			int row = queryResultsTable.getSelectedRow();
			String query = "DELETE FROM " + tableName + " WHERE id = " + queryResultsTable.getValueAt(row, 0);
			this.host.performUpdateQuery(query);
			model.removeRow(row);

		}
	}

	public void windowActivated(WindowEvent e){
	}

	public void windowClosed(WindowEvent e){
	}

	public void windowClosing(WindowEvent e){
		System.out.println("QueryResultsFrame: windowClosing");
        
        host.actionPerformed(new ActionEvent(this, 0, "QUERY_RESULTS_FRAME_CLOSING"));
	}

	public void windowDeactivated(WindowEvent e){
	}

	public void windowDeiconified(WindowEvent e){
	}

	public void windowIconified(WindowEvent e){
	}

	public void windowOpened(WindowEvent e){
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int col = e.getColumn();
		int row = e.getLastRow();
		String newValue = model.getValueAt(row, col).toString();
		String id = model.getValueAt(row, 0).toString();

		System.out.println("apply additional action");
		String temp = e.getSource().toString();
		System.out.println(model.getColumnName(col));
		System.out.println(id);	
		System.out.println(newValue);			
		System.out.println("this was edited..." + temp + "and it was this col:" + col);
		System.out.println("table name...." + tableName);

		if(model.getColumnName(col).equals("dealership_number")){
			String query = "UPDATE " + tableName + " " +
							"SET " + model.getColumnName(col) + " = " + newValue + " " +
							"WHERE " + "id" + "  = " + id;

		this.host.performUpdateQuery(query);
		}

	}
}