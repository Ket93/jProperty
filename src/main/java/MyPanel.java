import org.bson.Document;
import org.bson.types.Decimal128;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.File;
import java.net.URI;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;


public class MyPanel extends JPanel implements ActionListener {
    // MyList
    ArrayList<Document> myList = new ArrayList<>();
    // Local properties stuff
    ArrayList<Document> allLocalProperties = new ArrayList<>(); // This contains all 650 local properties
    boolean showingLocalListings = false;
    // Current properties shown
    ArrayList<Document> currentProperties = new ArrayList<>(); // This contains the properties current shown on the buttons
    int selectedProperty = -1;
    Image currentImage = null;
    Image titleBar;
    Image pinPic;
    // Panel related things
    JButton mainButton;
    JButton websiteButton;
    JButton mapButton;
    JButton addListButton;
    JButton reviewButton;
    JButton listButton;
    ArrayList<JButton> allButtons;
    ArrayList<Integer> xPositions = new ArrayList<Integer>();
    ArrayList<Integer> yPositions = new ArrayList<Integer>();
    JScrollPane scrollPane;
    ScrollPanel scrollPanel;
    JTextArea text;
    JTextArea padding;
    private ArrayList<Double> lattitudes = new ArrayList<Double>();
    private ArrayList<Double> longitudes = new ArrayList<Double>();

    boolean heatMap;
    Image map;
    public MyPanel(){
        loadImages();
        addCords();
        getPositions();
        // Setting up local listings
        allLocalProperties = NetworkHandler.getLocalListings();
        this.setLayout(null);
        try{
            titleBar = ImageIO.read(new File("Images/jProperty1.png"));
        }
        catch(IOException exception){
            exception.printStackTrace();
            System.out.println("Image not found");
            System.exit(1);
        }

        // Setting background color
        this.setBackground(new Color(0xa3d5ff));

        // Creating buttons
        mainButton = new JButton();
        websiteButton = new JButton();
        mapButton = new JButton();
        addListButton = new JButton();
        reviewButton = new JButton();
        listButton = new JButton();

        //Creating Text Area
        text = new JTextArea();
        padding = new JTextArea();

        // Customizing Buttons

        mainButton.setText("Search Listings");
        mainButton.setFocusable(false);
        mainButton.addActionListener(this);
        mainButton.setBackground(new Color(59, 89, 182));
        mainButton.setForeground(Color.WHITE);
        mainButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        mainButton.setBounds(60,30,300,30);
        mainButton.setBorder(BorderFactory.createRaisedBevelBorder());

        websiteButton.setText("Open Website");
        websiteButton.setFocusable(false);
        websiteButton.setForeground(Color.BLUE);
        websiteButton.setBackground(new Color(59, 89, 182));
        websiteButton.setForeground(Color.WHITE);
        websiteButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        websiteButton.setBounds(850,500,150,30);
        websiteButton.addActionListener(this);
        websiteButton.setBorder(BorderFactory.createRaisedBevelBorder());

        mapButton.setText("Toggle Heat Map");
        mapButton.setFocusable(false);
        mapButton.addActionListener(this);
        mapButton.setBackground(new Color(59, 89, 182));
        mapButton.setForeground(Color.WHITE);
        mapButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        mapButton.setBounds(850,15,150,30);
        mapButton.setBorder(BorderFactory.createRaisedBevelBorder());

        reviewButton.setText("Reviews");
        reviewButton.setFocusable(false);
        reviewButton.addActionListener(this);
        reviewButton.setBackground(new Color(59, 89, 182));
        reviewButton.setForeground(Color.WHITE);
        reviewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        reviewButton.setBounds(680,500,150,30);
        reviewButton.setBorder(BorderFactory.createRaisedBevelBorder());

        addListButton.setText("Add to my List");
        addListButton.setFocusable(false);
        addListButton.addActionListener(this);
        addListButton.setBackground(new Color(59, 89, 182));
        addListButton.setForeground(Color.WHITE);
        addListButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        addListButton.setBounds(510,500,150,30);
        addListButton.setBorder(BorderFactory.createRaisedBevelBorder());

        listButton.setText("My List");
        listButton.setFocusable(false);
        listButton.addActionListener(this);
        listButton.setBackground(new Color(59, 89, 182));
        listButton.setForeground(Color.WHITE);
        listButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        listButton.setBounds(850,55,150,30);
        listButton.setBorder(BorderFactory.createRaisedBevelBorder());

        allButtons = new ArrayList<JButton>();
        allButtons.add(mainButton);
        allButtons.add(websiteButton);
        allButtons.add(mapButton);
        allButtons.add(addListButton);
        allButtons.add(reviewButton);
        allButtons.add(listButton);

        // Customizing Text Area

        padding.setBounds(400,100,580,260);
        padding.setBorder(null);
        padding.setBackground(new Color(0xfcefef));
        padding.setEditable(false);
        padding.setBorder(BorderFactory.createBevelBorder(1));

        text.setLineWrap(true);
        text.setBounds(410,110,560,240);
        text.setWrapStyleWord(true);
        text.setBackground(new Color(0xfcefef));
        text.setFont(new Font ("Tahoma", Font.PLAIN, 14));
        text.setText("Property Name \n\n" + "Summary: \n" + "Street: \n" + "Price: \n" +
                "Property Type: \n" + "Accommodates: \n" + "Bedrooms: \n" + "Bathrooms: \n" + "");
        text.setEditable(false);
        text.revalidate();


        // Adding scroll pane
        scrollPanel = new ScrollPanel(this);
        scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setLocation(60, 90);
        scrollPane.setSize(300,420);
        this.add(scrollPane);

        // Adding Buttons
        this.add(mainButton);
        this.add(websiteButton);
        this.add(mapButton);
        this.add(reviewButton);
        this.add(addListButton);
        this.add(listButton);

        // Adding Text Area
        this.add(text);
        this.add(padding);
        heatMap = false;
        // Final Panel Setup
        setVisible(true);
        repaint();
    }
    public void loadImages(){
        try{
            pinPic = ImageIO.read(new File("pinPic.png"));
           // pinPic = pinPic.getScaledInstance(30,30,0);
            titleBar = ImageIO.read(new File("Images/jProperty.png"));
            map = ImageIO.read(new File("windMap.jpeg"));
            map = map.getScaledInstance(1024,576,0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addCords(){
        try{
            /*
            File cords = new File("latsAndLongs.txt");
            Scanner fileReader = new Scanner(cords);
             */
            String coordsString = NetworkHandler.getLatsAndLongs();
            String[] coordsArray = coordsString.split("\n");
            for(String coord: coordsArray){
                String[] newCoords = coord.split(",");
                Double newLat = Double.parseDouble(newCoords[0]);
                lattitudes.add(newLat);
                Double newLong = Double.parseDouble(newCoords[1]);
                longitudes.add(newLong);
            }
        }
        catch(Exception exception){
            System.out.println("cant find it");
            exception.printStackTrace();
            System.exit(1);
        }
    }
    public void getPositions(){
        double left = 42.29023;
        double right = 42.154739;
        double top = 83.131683;
        double bottom = 82.805071;
        double horizontalPixels = 1024.0000000000000;
        double verticalPixels = 576.0000000000000;
        double horizontal = horizontalPixels/(left-right);
        double vertical = verticalPixels/(top-bottom);
        for (Double xCord: lattitudes){
            double finalX = Math.abs(xCord-left);
            xPositions.add((int)(finalX*horizontal));
        }
        for (Double yCord: longitudes){
            double finalY = Math.abs(yCord-top);
            yPositions.add((int)(finalY*vertical));
        }
    }
    public void selectProperty(int index){
        selectedProperty = index;
        if(showingLocalListings){
            currentImage = null;
            text.setText((String) currentProperties.get(selectedProperty).get("Address") +"\n\n" +
                    "Realtor: " + (String) currentProperties.get(selectedProperty).get("List Office") + "\n" +
                    "City: " + (String)currentProperties.get(selectedProperty).get("City") + "\n" +
                    "Price: " + ((String) currentProperties.get(selectedProperty).get("List Price"))  + "\n" +
                    "Property Type: "+ "House" +"\n" +
                    "District: " + (String) currentProperties.get(selectedProperty).get("Dist")+ "\n" +
                    "Bedrooms: " + (String) currentProperties.get(selectedProperty).get("Beds") + "\n" +
                    "Bathrooms: "+ (String) currentProperties.get(selectedProperty).get("Baths Total") + "\n" + "");
        }
        else{
            text.setText((String) currentProperties.get(selectedProperty).get("name") +"\n\n" +
                    "Summary: " + (String) currentProperties.get(selectedProperty).get("summary") + "\n" +
                    "Street: " + (String)((Document) currentProperties.get(selectedProperty).get("address")).get("street") + "\n" +
                    "Price: $" + Double.toString(((Decimal128) currentProperties.get(selectedProperty).get("price")).doubleValue()) + "/day" + "\n" +
                    "Property Type: "+ (String) currentProperties.get(selectedProperty).get("property_type") +"\n" +
                    "Accommodates: " + Integer.toString( (Integer) currentProperties.get(selectedProperty).get("accommodates"))+ "\n" +
                    "Bedrooms: " + Integer.toString( (Integer) currentProperties.get(selectedProperty).get("bedrooms")) + "\n" +
                    "Bathrooms: "+ Double.toString(((Decimal128) currentProperties.get(selectedProperty).get("bathrooms")).doubleValue()) + "\n" + "");
            getPropertyImage();
        }
        repaint();
    }

    public void getPropertyImage(){
        JDialog jDialog = new JDialog();
        jDialog.setLayout(new GridBagLayout());
        jDialog.add(new JLabel("Fetching image..."));
        jDialog.setMinimumSize(new Dimension(150, 50));
        jDialog.setResizable(false);
        jDialog.setModal(false);
        jDialog.setUndecorated(true);
        jDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
        jDialog.revalidate();
        jDialog.paintComponents(jDialog.getGraphics());
        try {
            URL url = new URL((String) ((Document)currentProperties.get(selectedProperty).get("images")).get("picture_url"));
            Image image = ImageIO.read(url);
            currentImage = image;
        } catch (IOException e) {
            System.out.println("Image not found for property");
            currentImage = null;
        }
        jDialog.dispose();
    }

    public void airbnbPopulator(String country, int maxPrice){
        currentProperties.clear();
        for(Document listing: NetworkHandler.getListings(1000)) {
            Document addressInfo  = (Document) listing.get("address");
            if(addressInfo.get("country").equals(country) || country.equals("Worldwide")){
                if(((Decimal128) listing.get("price")).doubleValue() < maxPrice || maxPrice == 0){
                    currentProperties.add(listing);
                }
            }
        }
        updateScrollPanel();
    }

    public void localPopulator(){
        currentProperties.clear();
        for(int i = 0; i < 50; i++){
            currentProperties.add(allLocalProperties.get(i));
        }
        updateScrollPanel();
    }

    public void updateScrollPanel(){
        scrollPanel.updateButtons(currentProperties);
    }


    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        if (!heatMap) {
            g2.setColor(new Color(0x000000));
            g2.setStroke(new java.awt.BasicStroke(8));
            g2.drawRect(60, 90, 300, 420);
            g2.drawImage(titleBar, 420, -10, this);
            if (currentImage != null) {
                Image blittedImage = currentImage.getScaledInstance(192,120,0);
                g2.drawImage(blittedImage, 595, 370, this);
                g2.setStroke(new java.awt.BasicStroke(5));
                g2.drawRect(595, 370, 192, 120);
            }
            g2.setStroke(new java.awt.BasicStroke(1));
        }
        else{
            g2.drawImage(map, 0, 0, this);
            for (int i = 0; i < longitudes.size(); i++){
                g2.drawImage(pinPic,xPositions.get(i)-15,yPositions.get(i)-30,this);
            }
        }

        //hideScrollPane();

    }

    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == mainButton)
        {
            // Search tool
            int selection = JOptionPane.showOptionDialog(this,
                    "Which database would you like to access?",
                    "Database Choice",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Airbnb", "Local listings"},"none");
            //Airbnb selection
            if(selection == 0){
                String country = (String)JOptionPane.showInputDialog(
                        this,
                        "Select the country you would like to search in",
                        "Country selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        new String[]{"Worldwide", "United States", "Spain", "Australia", "Canada", "Turkey", "Portugal", "Brazil", "Hong Kong"},
                        null);
                if(country == null){
                    return;
                }
                String maxPriceString = (String)JOptionPane.showInputDialog(
                        this,
                        "What is the maximum price per day you are looking for? (Write 0 for no limit)",
                        "Price selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        null);
                int maxPrice;
                try{
                    if(maxPriceString != null){
                        maxPrice = Integer.parseInt(maxPriceString);
                        JLabel loadingLabel = new JLabel("Fetching listings...");
                        loadingLabel.setBounds(500,100,100,100);
                        loadingLabel.setVisible(true);
                        // Loading window
                        JDialog jDialog = new JDialog();
                        jDialog.setLayout(new GridBagLayout());
                        jDialog.add(new JLabel("Fetching listings..."));
                        jDialog.setMinimumSize(new Dimension(150, 50));
                        jDialog.setResizable(false);
                        jDialog.setModal(false);
                        jDialog.setUndecorated(true);
                        jDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        jDialog.setLocationRelativeTo(null);
                        jDialog.setVisible(true);
                        jDialog.revalidate();
                        jDialog.paintComponents(jDialog.getGraphics());
                        // clearing list
                        if(showingLocalListings){
                            myList.clear();
                        }
                        showingLocalListings = false;
                        airbnbPopulator(country, maxPrice);
                        jDialog.dispose();
                        if(currentProperties.size() == 0){
                            JOptionPane.showMessageDialog(this, "No properties found matching your search. (Try a higher max price?)");
                        }
                    }

                }
                catch(NumberFormatException exception){
                    JOptionPane.showMessageDialog(this, "Invalid price. (Prices must be Integer values)");
                }

            }
            // Local houses selection
            else if (selection == 1){
                if(!showingLocalListings){
                    myList.clear();
                }
                showingLocalListings = true;
                Collections.shuffle(allLocalProperties);
                localPopulator();
            }
        }
        else if(e.getSource() == websiteButton){
            if(selectedProperty == -1){
                JOptionPane.showMessageDialog(this,
                        "Select a property first!",
                        "You've landed in ghost town",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (showingLocalListings) {
                JOptionPane.showMessageDialog(this,
                    "Websites not supported for local listings",
                    "Unsupported feature",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try{
                if (selectedProperty != -1 && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI((String)currentProperties.get(selectedProperty).get("listing_url")));
                }

            }
            catch(URISyntaxException | IOException exception){
                JOptionPane.showMessageDialog(this, "Listing has an invalid website attached");
            }

        }
        else if(e.getSource() == reviewButton){
            if(selectedProperty == -1){
                JOptionPane.showMessageDialog(this,
                        "Select a property first!",
                        "You've landed in ghost town",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!showingLocalListings) {
                ArrayList<Document> reviews = (ArrayList<Document>) currentProperties.get(selectedProperty).get("reviews");
                ArrayList<String> reviewNames = new ArrayList<>();
                for (Document review : reviews) {
                    reviewNames.add((String) review.get("reviewer_name"));
                }
                if(reviews.size() == 0){
                    JOptionPane.showMessageDialog(this,
                            "This listing doesn't have any reviews",
                            "No reviews",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    String choice = (String) JOptionPane.showInputDialog(
                            this,
                            "Select the review that you would like to view",
                            "Reviews",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            reviewNames.toArray(),
                            null);
                    if(choice != null){
                        int index = reviewNames.indexOf(choice);
                        JOptionPane.showMessageDialog(this,
                                (String) reviews.get(index).get("comments"),
                                choice + "'s review",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                }
            }
            else{
                JOptionPane.showMessageDialog(this,
                        "Reviews not supported for local listings",
                        "Unsupported feature",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(e.getSource() == addListButton){
            if (selectedProperty == -1 || currentProperties.size() == 0){
                JOptionPane.showMessageDialog(this,
                        "Select a property first!",
                        "You've landed in ghost town",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            myList.add(currentProperties.get(selectedProperty));
            JOptionPane.showMessageDialog(this,
                    "Added to my List",
                    "Success!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == listButton){
            if(myList.size() == 0){
                JOptionPane.showMessageDialog(this,
                        "Your list is empty.",
                        "Add to your List!",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            ArrayList<String> options = new ArrayList<>();
            for (Document item : myList) {
                if(!showingLocalListings){
                    options.add((String) item.get("name"));
                }
                else{
                    options.add((String) item.get("Address"));
                }
            }
            String choice = (String) JOptionPane.showInputDialog(
                    this,
                    "Select the listing that you would list to view",
                    "My List",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options.toArray(),
                    null);
            if(choice != null){
                int index = options.indexOf(choice);
                currentProperties.add(myList.get(index));
                selectProperty(currentProperties.size() - 1);
            }

        }
        repaint();
        if (e.getSource() == mapButton) {
            if (currentProperties.size() == 0){
                JOptionPane.showMessageDialog(this,
                        "Select a property first!",
                        "You've landed in ghost town",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!showingLocalListings){
                JOptionPane.showMessageDialog(this,
                        "Airbnb listings don't support the heat map",
                        "Unsupported feature",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            heatMap = !heatMap;
            if (heatMap == false){
                setButtonsVis();
            }
            else{
                setButtonsInvis();
                setMapButtonVisible();
            }
        }
        else{
            setButtonsVis();
        }
        repaint();
    }
    public void setButtonsInvis(){
        for (JButton curButton : allButtons){
            curButton.setVisible(false);
        }
        scrollPane.setVisible(false);
        text.setVisible(false);
        padding.setVisible(false);
    }
    public void setButtonsVis(){
        for (JButton curButton : allButtons){
            curButton.setVisible(true);
        }
        scrollPane.setVisible(true);
        text.setVisible(true);
        padding.setVisible(true);
    }
    public void setMapButtonVisible(){
        mapButton.setVisible(true);
    }
    public void hideScrollPane(){
        scrollPane.setVisible(false);
    }
}
