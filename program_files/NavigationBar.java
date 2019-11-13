import javax.swing.*;    
import java.awt.event.*;    
public class NavigationBar extends JMenuBar implements ActionListener{   

    JMenuBar navBar;    
    JMenu file, edit, help;    
    JMenuItem cut, copy, paste, selectAll;    
    JTextArea ta;    
    
    NavigationBar(){  

        cut = new JMenuItem("cut");    
        copy = new JMenuItem("copy");    
        paste = new JMenuItem("paste");    
        selectAll = new JMenuItem("selectAll"); 

        cut.addActionListener(this);    
        copy.addActionListener(this);    
        paste.addActionListener(this);    
        selectAll.addActionListener(this); 

        navBar = new JMenuBar();    
        file = new JMenu("File");    
        edit = new JMenu("Edit");    
        help = new JMenu("Help"); 

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll); 

        navBar.add(file);
        navBar.add(edit);
        navBar.add(help);

        ta = new JTextArea();    
        ta.setBounds(5,5,360,320);   

        //f.add(mb);f.add(ta);    
        //f.setJMenuBar(mb);  
        //f.setLayout(null);    
        //f.setSize(400,400);    
        //f.setVisible(true);    
    }     
    public void actionPerformed(ActionEvent e) {    
        if(e.getSource()==cut)    
        ta.cut();    
        if(e.getSource()==paste)    
        ta.paste();    
        if(e.getSource()==copy)    
        ta.copy();    
        if(e.getSource()==selectAll)    
        ta.selectAll();    
    }        
}  