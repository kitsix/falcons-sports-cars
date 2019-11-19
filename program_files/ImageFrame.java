import javax.swing.*;
import java.awt.*;

class ImageFrame extends JFrame{

    QueryResultsFrame host;

public ImageFrame(QueryResultsFrame frame){
    this.host = frame;
    this.setVisible(true);
    setupMainFrame();
}

void setupMainFrame(){
    Toolkit tk;
    Dimension d;
    tk = Toolkit.getDefaultToolkit();
    d = tk.getScreenSize();
    int frameWidth = 200;
    int frameHeight = 200;
    setSize(frameWidth, frameHeight);
    setLocation(d.width/2 - frameWidth/2, d.height/8 - frameHeight/2);
    setTitle("Image");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);        
    setResizable(false);
    setLocationRelativeTo(this.host);      

}

}