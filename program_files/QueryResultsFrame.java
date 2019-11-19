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
import javax.swing.table.*;



// This is a simple class for displaying query results.
// Note that each instance of this class is stored in a the queryResultsFrameVector data member of the Frame class.
// This is to allow a user to dispose of all currently open QueryResultsFrame with a single click of the "Close Query Results" button.
class QueryResultsFrame extends JFrame
	implements WindowListener
{
	Frame 				host;
    JLabel 				queryLabel, queryResultsTableLabel;
    JTextArea 			queryArea;
    JScrollPane 		queryAreaScrollPane = null;
    JTable 				queryResultsTable;
    JScrollPane 		queryResultsTableScrollPane = null;
    JPanel 				mainPanel;
    GroupLayout 		layout;

    QueryResultsFrame(Frame host, PreparedStatement pStatement, ResultSet resultSet){        
		
		this.host = host;
		this.addWindowListener(this);    
		Container contentPane = getContentPane();
		boolean printImage = false;

        try{   
            queryArea = new JTextArea(pStatement.toString());
			queryArea.setEditable(false);
			//queryAreaScrollPane = new JScrollPane(queryArea);      
     
            Vector columnNames = new Vector<Object>();
            Vector rows = new Vector<Object>();
            
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            
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
						//imagePanel.repaint();
						ImageFrame test = new ImageFrame(this);
						test.add(imagePanel);
						imagePanel.repaint(); 
					}}
			}

			DefaultTableModel model = new DefaultTableModel(rows, columnNames)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
            
            queryResultsTable = new JTable(model);
			queryResultsTableScrollPane = new JScrollPane(queryResultsTable);
			
			queryResultsTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent me) {
					if(me.getClickCount() == 1) {
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
			//.addComponent(queryAreaScrollPane)
            .addComponent(queryResultsTableScrollPane));

		layout.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

		//vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(queryAreaScrollPane));
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
		setLocationRelativeTo(this.host);      
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
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
}