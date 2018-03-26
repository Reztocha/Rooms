import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Math;
/**
 * All GUI elements go here.
 *
 * @author (Catherine Denis)
 * @version (0.2)
 */
public class GUIEnvironment extends JFrame implements KeyListener, MouseListener
{
    private Container contents;
    private HashMap<Player, Integer> map;
    private ArrayList<Room> rooms;
    private ArrayList<Player> players;
    private Iterator<Player> iter;
    private JButton challenge;
    private JLabel winner;
    private Player currentplayer;
    private Player challenger;
    private int currentPositionX;
    private int currentPositionY;
    private int widthOfFrame;
    private int heightOfFrame;
    private Color[] colors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.WHITE, Color.PINK, Color.ORANGE, Color.RED, Color.GRAY};
    private Room currentRoom;
    private volatile int rpsResult = -2;
    /**
     * Constructor for objects of class GUIEnvironment
     */
    public GUIEnvironment()
    {
        map = new HashMap<Player, Integer>();
        rooms = new ArrayList<Room>();
        players = new ArrayList<Player>();
        createEnvironment();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Puts together all GUI elements for display
     */
    public void createEnvironment()
    {
        contents = getContentPane();
        contents.setLayout(null);
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(3,3));
        container.setSize(300,300);
        for(int i = 0; i< 9; i++) {
            Room panel = new Room(i);
            panel.setLayout(null);
            panel.addMouseListener(this);
            panel.getInnerBounds().addMouseListener(this);
            panel.setBackground(colors[i]);
            rooms.add(panel);
            container.add(panel);
        }
        
        challenge = new JButton("Challenge");
        challenge.setLocation(0,0);
        challenge.setSize(100,20);
        rooms.get(0).add(challenge);
        challenge.addMouseListener(this);
        challenge.setVisible(false);
        itsRainingMen();
        
        winner = new JLabel();
        winner.setSize(100,20);
        winner.setBackground(Color.WHITE);
        winner.setLocation(100,130);
        contents.add(winner);
        
        contents.add(container);
        
        addKeyListener(this);
        setSize(315, 340);
        setLocationRelativeTo(null); //Centers window
        setVisible(true);
    }
    
    /**
     * Adds player to a room
     * @param roomNumber - the room a player to add a player to
     * @param p - Player to add to room
     */
    public void addPlayerToRoom(int roomNumber, Player p) {
        (rooms.get(roomNumber)).addToInnerBounds(p);
    }
    
    /**
     * Eradicates a player from existance
     * @param p - Player to remove
     */
    public void removePlayer(Player p) {
        iter = players.iterator();
        while (iter.hasNext()) {
            Player str = iter.next();
            if (str.equals(p)) {
                iter.remove();
                rooms.get(p.getRoom()).removeFromInnerBounds(p);
                rooms.get(p.getRoom()).remove(p);
            }
        }
        
        if(p.equals(currentplayer)) {
            currentplayer = players.get(0);
        }
        checkWinner();
    }
    
    /**
     * Checks to see who has won the game, if anyone has
     */
    public void checkWinner() {
        if(players.size() == 1) {
            winner.setText("You won, " + players.get(0).getName() +"!");
            winner.setVisible(true);
        }
    }
    
    /**
     * Adds players and adds them to rooms
     */
    public void itsRainingMen()
    {
        int rand = ThreadLocalRandom.current().nextInt(3, 10); 
        for(int i = 0; i < rand; i++) {
            Player player = new Player("Player"+(i+1), i);
            player.addMouseListener(this);
            player.setColor(new Color(25*i, 34 ,100));
            players.add(player);
            addPlayerToRoom(player.getRoom(),player);
            currentPositionX = 0;
            currentPositionY = 0;
        }
        currentplayer = players.get(0);
        currentplayer.setToSelectColor();
        currentRoom = rooms.get(currentplayer.getRoom());
    }
    
    /**
     * Allows the current player to challenge another to a game
     * @param p - Player to be challenged
     */
    public void challengePlayer(Player p) {
        challenger = p;
        challenge.setVisible(true);
    }
    /**
     * Checks to see if any other players are near currentplayer
     */
    public void detectPlayersInRange() {
        for(Player player : players) {
            if(currentplayer.equals(player) == false && currentplayer.getRoom() == player.getRoom()) {
                if((currentplayer.getX() >= player.getX() - 20)
                    && (currentplayer.getX() <= player.getX() + 20)
                    && (currentplayer.getY() >= player.getY() - 20)
                    && (currentplayer.getY() <= player.getY() + 20)) {
                    challengePlayer(player);
                }
                else {
                    challenge.setVisible(false);
                }
            }
        }
    }
    /**
     * Removes a player based on game results
     */
    public void finishHim() {
        switch(rpsResult) {
            case 0: //do nothing
                break;
            case 1:
                //current player won, remove challenger
                removePlayer(challenger);
                break;
            case -1:
                //current player lost, remove currentplayer
                removePlayer(currentplayer);
                break;
            default: //do nothing
                break;
        }
    }
   
    /**
     * Sets result of rock, paper, scissors game
     * @param r - numeric value of result
     */
    public void setResult(int r) {
        rpsResult = r;
    }
    /* distance calc
     */
    public int getDistance(int x1, int y1, int x2, int y2) {
         int deltaX = x2-x1;
         int deltaY = y2-y1;
         return (int) Math.sqrt( Math.pow(deltaX, 2) + Math.pow(deltaY, 2) ); 
     }
    @Override
    public void mouseClicked(MouseEvent e) {
        this.requestFocus();
        //Switch current player to player clicked on
        if((e.getSource()) instanceof Player) {
            currentplayer.setToDefaultColor();
            currentplayer = (Player) (e.getSource());
            currentPositionX = currentplayer.getX();
            currentPositionY = currentplayer.getY();
            currentplayer.setToSelectColor();
            repaint();
        }
        //Switch current player to room clicked
        if((e.getSource()) instanceof Room || (e.getSource()) instanceof Room.InnerBounds) {
            if((e.getSource()) instanceof Room)
                currentRoom = (Room) (e.getSource());
            else if ((e.getSource()) instanceof Room.InnerBounds) {
                Room.InnerBounds inB = (Room.InnerBounds) (e.getSource());
                currentRoom = (Room) inB.getRoom();
            }
            else {/*exception*/}
            (rooms.get(currentplayer.getRoom())).remove(currentplayer);
            currentplayer.setRoom(currentRoom.getNumber());
            addPlayerToRoom(currentRoom.getNumber(), currentplayer);
            detectPlayersInRange();
            repaint();
        }
        //Start game if the button is clicked
        if((e.getSource()).equals(challenge)) {
            RockPaperScissors rps = new RockPaperScissors(this);
            challenge.setVisible(false);
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    /**
     * W, A, S, and D used
     * @param e - key being pressed
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        detectPlayersInRange();
        if(keyCode == KeyEvent.VK_W) {
            if(currentPositionY != 0)
                currentPositionY = currentPositionY - 5;
            //needs to have boundaries
            currentplayer.setY(currentPositionY);
            currentplayer.setLocation(currentplayer.getX(),currentplayer.getY());
            repaint();
        }
        else if(keyCode == KeyEvent.VK_D) {
            if(currentPositionX != 60)
                currentPositionX = currentPositionX + 5;
            currentplayer.setX(currentPositionX);
            currentplayer.setLocation(currentplayer.getX(),currentplayer.getY());
            repaint();
        }
        else
            e.consume();
        if(keyCode == KeyEvent.VK_A) {
            if(currentPositionX != 0)
            currentPositionX = currentPositionX - 5;
            currentplayer.setX(currentPositionX);
            currentplayer.setLocation(currentplayer.getX(),currentplayer.getY());
            repaint();
        }
        else if(keyCode == KeyEvent.VK_S) {
            if(currentPositionY != 60)
                currentPositionY = currentPositionY + 5;
            currentplayer.setY(currentPositionY);
            currentplayer.setLocation(currentplayer.getX(),currentplayer.getY());
            repaint();
        }
        else
            e.consume();
        contents.setFocusable(true);
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        contents.setFocusable(true);
    }
    @Override
    public void keyTyped(KeyEvent e)
    {
        contents.setFocusable(true);
        
        for(int i = 0; i< currentRoom.getAllDoors().size(); i++) {
            /*
            int deltaX = Math.abs(currentRoom.getAllDoors().get(i).getDoorX() - 
                     currentplayer.getX());
            int deltaY = Math.abs(currentRoom.getAllDoors().get(i).getDoorY() + 3 - 
                     currentplayer.getY());
            if(deltaX <= 10 || deltaY <= 10) {
                System.out.println(deltaX);
                
                System.out.println(deltaY);
                System.out.println("door!");
                
            }*/
            /*
                int doorY = currentRoom.getAllDoors().get(i).getDoorY() + 3;
                int doorX = currentRoom.getAllDoors().get(i).getDoorX();
                int d =  getDistance(cpX, cpY,doorX, doorY);
            */
            
            int cpX = currentplayer.getX();
            int cpY = currentplayer.getY();
            
            //If near another player, change to black
            for(Player player : players) {
                if(currentplayer.equals(player) == false && currentplayer.getRoom() == player.getRoom()) {
                int dist = getDistance(cpX, cpY, player.getX(), player.getY());
                    if(dist < 11) {
                        currentplayer.setColor(Color.BLACK);
                    }
                    else {
                        System.out.println("Nothing");
                        System.out.println("Player position: " + currentplayer.getX()
                                           + ", " + currentplayer.getY());
                        System.out.println("Door position: " + 
                                    currentRoom.getAllDoors().get(i).getDoorX() +
                                    ", " + currentRoom.getAllDoors().get(i).getDoorY() );
                    }
                }
            }
        }
    }
}