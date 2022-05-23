import org.bson.Document;

import javax.swing.*;
import java.awt.*;

public class JProperty extends JFrame {
    // Frame Object
    MyPanel frame;
    // Constructor
    public JProperty(){
        super("JProperty"); // Setting the title
        // Initializing Networking Features
        NetworkHandler.init();
        // Constructing frame
        frame = new MyPanel();
        // Setting window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024,576);
        setResizable(false);
        setLocationRelativeTo(null);
        add(frame);
        setVisible(true);
    }
    public static void main(String[] args){
        System.out.println("JProperty!");
        JProperty mainInstance = new JProperty();
    }
}
