import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// This class is responsible for creating a GUI into which a user can enter an SQL query.
// Upon clicking the "Execute" button, the query will be sent back to the Frame class which passes the query along to the ConnectionHandler.
// The ConnectionHandler will actually execute the query and return the results Frame.
// The Frame will then display the query results via a new instance of the QueryResultsFrame.
class QueryFrame extends JFrame
	implements ActionListener, WindowListener{
	Frame host;
    JTextArea queryArea;
    JScrollPane queryAreaScrollPane;
	JButton execute, clear, cancel;
	GroupLayout layout;
	JPanel buttonPanel, fieldPanel, mainPanel;

    QueryFrame(Frame host){  

		System.out.println("in here");
		this.host = host;
		this.addWindowListener(this);

        queryArea = new JTextArea(""); // DEFAULTING TO A VALUE FOR TESTING
        queryAreaScrollPane = new JScrollPane(queryArea);

		execute = new JButton("Execute");
		execute.addActionListener(this);
		execute.setActionCommand("EXECUTE");
		// getRootPane().setDefaultButton(execute);

		clear = new JButton("Clear");
		clear.addActionListener(this);
		clear.setActionCommand("CLEAR");

		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		cancel.setActionCommand("CANCEL");

		buttonPanel = new JPanel();
		buttonPanel.add(execute);
		buttonPanel.add(clear);
		buttonPanel.add(cancel);

        add(queryAreaScrollPane, BorderLayout.CENTER);        
        add(buttonPanel, BorderLayout.SOUTH);

        queryArea.requestFocus();

        Toolkit tk;
		Dimension d;

		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();

        int queryFrameWidth = d.width/2;
        int queryFrameHeight = d.height/2;

		setSize(queryFrameWidth, queryFrameHeight);
		setLocationRelativeTo(this.host);
		setTitle("Query Frame");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(false);
	}

    public String getQuery(){
        return queryArea.getText();
    }

	public void execute(){
		System.out.println("QueryFrame: EXECUTE");

        host.actionPerformed(new ActionEvent(this, 0, "PERFORM_QUERY"));
	}

	public void clear(){
        System.out.println("QueryFrame: CLEAR");
        
		queryArea.setText("");
		queryArea.requestFocus();
	}

	public void cancel(){
		System.out.println("QueryFrame: CANCEL");

		this.setVisible(false);
	}

	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();

		if (cmd.equals("EXECUTE")){
			execute();
		}

		else if (cmd.equals("CLEAR")){
			clear();
		}

		else if (cmd.equals("CANCEL")){
			cancel();
		}
	}

	public void windowActivated(WindowEvent e){
	}

	public void windowClosed(WindowEvent e){
	}

	public void windowClosing(WindowEvent e){
		System.out.println("QueryFrame: windowClosing");
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