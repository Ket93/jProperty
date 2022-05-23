
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ScrollPanel extends JPanel implements ActionListener {

    ArrayList<JButton> buttons = new ArrayList<>();
    MyPanel parentPanel;
    public ScrollPanel(MyPanel parentPanel)
    {
        this.parentPanel = parentPanel;
        this.setSize(250,1600);
        this.setPreferredSize(new Dimension(250,1600));

        this.setLayout(null);
        // Final Panel Setup
        setVisible(true);
    }

    public void updateButtons(ArrayList<Document> newProperties){
        // Removing old buttons
        for(JButton button: buttons){
            remove(button);
        }
        buttons.clear();
        // getting limit of buttons
        int limit = newProperties.size();
        if(limit > 50){
            limit = 50;
        }
        // Adding new buttons
        for(int i = 0; i < limit; i++){
            JButton currentButton = new JButton();
            currentButton.setBounds(20,20 + (i*30),250,30);
            if(parentPanel.showingLocalListings){
                currentButton.setText((String)newProperties.get(i).get("Address"));
            }
            else{
                currentButton.setText((String)newProperties.get(i).get("name"));
            }
            currentButton.setFocusable(false);
            currentButton.setActionCommand(Integer.toString(i));
            currentButton.addActionListener(this);
            add(currentButton);
            buttons.add(currentButton);
        }
        revalidate();
        paintComponents(this.getGraphics());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(buttons.contains((JButton)e.getSource())){
            parentPanel.selectProperty(Integer.parseInt(e.getActionCommand()));
        }
    }
}
