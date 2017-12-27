import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;
/**
 * Player.
 *
 * @author (Catherine Denis) & Christian Daher
 * @version (0.1)
 */
public class Player extends JPanel
{
    private int hash;
    private int timestamp = (int)new Date().getTime();
    private String name;
    private int room;
    private int xCord;
    private int yCord;
    private Color color;
    private Color defaultColor = new Color(145, 32, 121);
    private Color selectcolor = new Color(34,121,23);
    /**
     * Constructor for objects of class Player
     * Starts in the first room
     * Set to default color
     */
    public Player()
    {
        name = "Player";
        room = 0;
        setPreferredSize(new Dimension(10,10));
        xCord = 0;
        yCord = 0;
        color = defaultColor;
        this.makeHash();
    }

    /**
     * Constructor for objects of class Player
     * @param name - Desired name of the player
     * @param room - Desired room number to start in
     */
    public Player(String name, int room)
    {
        this.name = name;
        this.room = room;
        setPreferredSize(new Dimension(10,10));
        xCord = 0;
        yCord = 0;
        color = defaultColor;
        setBackground(new Color(145, 32, 121,0));
        this.makeHash();
    }
    
    /**
     * Gets the name of this player
     * @return - The name of the player
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the player's name
     * @param n - The new name of the player
     */
    public void setName(String n) {
        name = n;
    }
    
    /**
     * Returns the room that this player is in
     * @return - the room number
     */
    public int getRoom() {
        return room;
    }
    
    /**
     * Sets the room number that the player is in
     * @param r - The desired room number to place the player in
     */
    public void setRoom(int r) {
         room = r;
    }
    
    /**
     * Sets the player's color to a new color
     * @param c - New color desired
     */
    public void setColor(Color c) {
        color = c;
    }
    
    /**
     * Returns the player's current color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Sets the player's color to the default color
     */
    public void setToDefaultColor() {
        color = defaultColor;
    }
    
    /**
     * Sets the player's color to the default select color
     */
    public void setToSelectColor() {
        color = selectcolor;
    }
    
    /**
     * Returns the x-coordinate of the player
     */
    public int getX(){
        return xCord;
    }
    
    /**
     * Sets the x-coordinate of the player
     * @param x - The desired value for the player's x-coordinate
     */
    public void setX(int x){
        xCord = x;
    }
    
    /**
     * Returns the y-coordinate of the player
     */
    public int getY(){
        return yCord;
    }
    
    /**
     * Sets the player's y-coordinate
     * @param y - The desired y-coordinate value for the player
     */
    public void setY(int y){
        yCord = y;
    }
    
    /**
     * Draws the shape of the circle
     * @param g - Graphics component
     */
    private void drawAThing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(color);
        g2d.fillOval(0,0,10,10);
        getInsets().set(0, 0, 0, 0);
    }
    
    /**
     * Paints the graphical components of Resizable Circle
     * @param g - Graphical element
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAThing(g);
    }
    
    /**
     * Creates the hashcode for player
     */
    private void makeHash() {
        hash = 1;
        for (int i = 0; i < this.getName().length(); i++) {
            hash = hash + (timestamp * (int)this.getName().charAt(i));
        }
        hash = Math.abs(hash);
    }
    
    /**
     * Returns the hashcode for this player
     * @return hash - hashcode calculated for player
     */
    @Override
    public int hashCode() {
        return hash;
    }
    
    /**
     * Returns whether or not this player is the same as another player
     * @return - boolean value true or false
     */
    public boolean equals(Player play) {
        if(play instanceof Player){
            if(this.name == play.name
                && this.room == play.room
                && this.color == play.color
                && this.hash == play.hash
                && this.xCord == play.xCord
                && this.yCord == play.yCord)
                { return true; }
        }
        return false;
    }
}