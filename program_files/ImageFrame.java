import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javafx.embed.swing.SwingFXUtils;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.awt.Image;


class ImageFrame extends JFrame{

public ImageFrame(){
    this.setVisible(true);
    setupMainFrame();
}

void setupMainFrame(){
    Toolkit tk;
    Dimension d;
    tk = Toolkit.getDefaultToolkit();
    d = tk.getScreenSize();
    int frameWidth = 300;
    int frameHeight = 300;
    setSize(frameWidth, frameHeight);
    setLocation(d.width/2 - frameWidth/2, d.height/8 - frameHeight/2);
    setTitle("Image");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);        
    setResizable(false);
}

}