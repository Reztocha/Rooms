import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
/**
 * Moveable area for a Player
 *
 * @author (Catherine Denis)
 * @version (0.2)
 */
public class Room extends JPanel
{
    private Color transparent = new Color(34,23,42,0);
    /**
     * A door within the Room
     */
    protected class Door extends JPanel
    {
        private String direction;
        private ImageIcon image;
        private JLabel label;
        private URL file;
        
        /**
         * Constructor for objects of Door class
         */
        public Door()
        {
            file = this.getClass().getResource("leftdoor.png");
            image = new ImageIcon(file);
            label = new JLabel(image);
            setBackground(transparent);
            
            setSize(22, 27);
            add(label);
        }
        /**
         * Constructor for objects of Door class
         * @param p - Direction the door is, in relation to the room it's in
         */
        public Door(String p)
        {
            direction = p;
            switch(direction) {
                case "left": setFile("leftdoor.png");  break;
                case "right": setFile("rightdoor.png"); break;
                case "up": setFile("upperdoor.png"); break;
                case "down": setFile("lowerdoor.png"); break;
            }
            image = new ImageIcon(file);
            label = new JLabel(image);
            setBackground(transparent);
            
            add(label);
            setSize(22, 27);
        }
        
        /**
         * Sets the direction the door is in
         * @param d - Desired direction of door
         */
        protected void setDirection(String d)
        {
            direction = d;
        }
        
        /**
         * Gets the direction this door is in
         * @return direction
         */
        public String getDirection()
        {
            return direction;
        }
        
        /**
         * Returns the name of the door's image file
         */
        public URL getFile()
        {
            return file;
        }
        
        /**
         * Sets the image displayed
         * @param x -> The desired image to display
         */
        public void setFile(String pic)
        {
            file = this.getClass().getResource(pic);
        }        
    }
    /**
     * Empty space/area where player can move around in
     */
    protected class InnerBounds extends JPanel
    {
        private Room room;
        /**
         * Constructor for objects of InnerBounds
         */
        public InnerBounds() {
            room = null;
        }
        /**
         * Constructor for objects of InnerBounds
         * @param r - the room this area is in
         */
        public InnerBounds(Room r) {
            room = r;
        }
        /**
         * Returns the room this inner area is in
         * @return room
         */
        public Room getRoom() {
            return room;
        }
    }
    private ImageIcon wall;
    private URL file;
    private InnerBounds inner;
    private int number;
    private JLabel label;
    private List<Door> doors;
    
    /**
     * Constructor for objects of class Room
     */
    public Room()
    {
        doors = new ArrayList<Door>();
        setLayout(null);
        setBounds(0, 0, 100, 100);
        number = 0;
        file = this.getClass().getResource("wall.png");
        wall = new ImageIcon(file);
        label = new JLabel(wall);
        label.setLayout(null);
        label.setBounds(0, 0, 100, 100);
        add(label);
        
        //inner part of room
        inner = new InnerBounds(this);
        inner.setBackground(transparent);
        inner.setSize(72,72);
        inner.setLocation(14,14);
        add(inner);
    }

    /**
     * Constructor for objects of class Room
     * @param num - int room number
     */
    public Room(int num)
    {
        doors = new ArrayList<Door>();
        setLayout(null);
        setBounds(0, 0, 100, 100);
        
        number = num;
        directionalDoors();
        file = this.getClass().getResource("wall.png");
        wall = new ImageIcon(file);
        label = new JLabel(wall);
        label.setLayout(null);
        label.setBounds(0, 0, 100, 100);
        add(label);
        
        //Inner part of room
        inner = new InnerBounds(this);
        inner.setBackground(transparent);
        inner.setSize(72,72);
        inner.setLocation(14,14);
        add(inner);
    }
    
    /**
     * Creates and sets the location of a door relative to Room
     * @param dir - Direction the door is inside of the room
     */
    private void createDoor(String dir) {
        Door d = new Door(dir);
        switch(d.getDirection()) {
            case "left":
                d.setLocation(0-2,50-17);
                break;
            case "up": 
                d.setLocation(50-12,0-2);
                break;
            case "down":
                d.setLocation(50-12,100-18);
                break;
            case "right":
                d.setLocation(100-18,50-17);
                break;
        }
        add(d);
        doors.add(d);
    }
    /**
     * Places all the doors for all of the rooms possible
     */
    private void directionalDoors() {
        switch(number) {
            case 0:
                createDoor("down");
                createDoor("right");
                break;
            case 1:
                createDoor("left");
                createDoor("down");
                createDoor("right");
                break;
            case 2:
                createDoor("left");
                createDoor("down");
                break;
            case 3:
                createDoor("up");
                createDoor("down");
                createDoor("right");
                break;
            case 4:
                createDoor("left");
                createDoor("up");
                createDoor("down");
                createDoor("right");
                break;
            case 5: 
                createDoor("left");
                createDoor("up");
                createDoor("down");
                break;
            case 6:
                createDoor("up");
                createDoor("right");
                break;
            case 7:
                createDoor("left");
                createDoor("up");
                createDoor("right");
                break;
            case 8:
                createDoor("left");
                createDoor("up");
                break;
        }
    }
    
    /**
     * Gets the list of all the doors in this room
     * @return - list containing all this room's doors
     */
    public List<Door> getAllDoors() {
        return doors;
    }
    /**
     * Gets the room number
     * @return - this room's assigned number
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Sets the room number
     * @param n - The desired room number
     */
    protected void setNumber(int n) {
        number = n;
    }
    
    /**
     * Returns the file being used for walls
     * @return - URL of file
     */
    public URL getFile() {
        return file;
    }
    
    /**
     * Adds a JPanel object to the inner part of Room
     * @param p - JPanel object
     */
    public void addToInnerBounds(JPanel p) {
        inner.add(p);
    }
    
    /**
     * Adds a JPanel object to the inner part of Room
     * @param p - JPanel object
     */
    public void removeFromInnerBounds(JPanel p) {
        inner.remove(p);
    }
    
    /**
     * Returns the inner JPanel object
     * @return - inner JPanel
     */
    public JPanel getInnerBounds() {
        return inner;
    }
    
    /**
     * Sets the image displayed
     * @param x -> The desired image to display
     */
    public void setFile(String pic)
    {
        file = this.getClass().getResource(pic);
        try {
            BufferedImage img = ImageIO.read(file);
            wall.setImage(img);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns whether or not this room is the same as another room
     * @return - boolean value true or false
     */
    public boolean equals(Room room) {
        if(room instanceof Room){
            if(this.number == room.number)
                { return true; }
        }
        return false;
    }
}