/**
 * A simple game of Rock, Paper, Scissors
 * @author Christian Daher
 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RockPaperScissors extends JFrame implements ActionListener {
    private JButton rock, paper, scissors, end;
    private JLabel ticker;
    private int result;
    private int computerChoice;
    private int playerChoice;
    private GUIEnvironment f;
    
    /**
     * Sets up the game of Rock, Paper, Scissors
     * 
     * @param g The GUIEnvironment that the result of the game will be sent to
     */
    public RockPaperScissors(GUIEnvironment g) {
        f = g;
        
        computerChoice = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        
        System.out.println(computerChoice);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        ticker = new JLabel("You are playing rock paper scissors. Pick your move: ");
        ticker.setSize(500, 30);
        ticker.setLocation(70, 100);
        
        rock = new JButton("Rock");
        rock.setSize(90, 30);
        rock.setLocation(100, 150);
        rock.addActionListener(this);
        
        paper = new JButton("Paper");
        paper.setSize(90, 30);
        paper.setLocation(200, 150);
        paper.addActionListener(this);
        
        scissors = new JButton("Scissors");
        scissors.setSize(90, 30);
        scissors.setLocation(300, 150);
        scissors.addActionListener(this);
        
        end = new JButton("OK");
        end.setSize(90, 30);
        end.setLocation(200, 150);
        end.addActionListener(this);
        end.setVisible(false);
    
        this.setLayout(null);
        this.setSize(new Dimension(500, 300));
        this.setVisible(true);
        this.add(rock);
        this.add(paper);
        this.add(scissors);
        this.add(ticker);
        this.add(end);
    }
    
    /**
     * Runs the comparison based on the player's choice
     * Once that has happened, 
     */
    @Override
    public void actionPerformed(ActionEvent a) {
        if (a.getSource() == rock) {
            playerChoice = 1;
        }
        if (a.getSource() == paper) {
            playerChoice = 2;
        }
        if (a.getSource() == scissors) {
            playerChoice = 3;
        }
        if (a.getSource() == end) {
            this.dispose();
            f.finishHim();
        }
        
        result = checkGame();
        
        rock.setVisible(false);
        paper.setVisible(false);
        scissors.setVisible(false);
        end.setVisible(true);
    }
    
    /**
     * Uses some simple math to figure out who won the game
     * 
     * @return 1 for player win, 0 for draw, -1 for computer win
     */
    private int checkGame() {
        int crunch = playerChoice - computerChoice;
        if (crunch == 0) {
            ticker.setText("It's a draw");
            f.setResult(0);
            return 0;
        }
        if (crunch == -2 || crunch == 1) {
            ticker.setText("You won");
            f.setResult(1);
            return 1;
        }
        if (crunch == -1 || crunch == 2) {
            ticker.setText("You lost");
            f.setResult(-1);
            return -1;
        }
        return 0;
    }
}