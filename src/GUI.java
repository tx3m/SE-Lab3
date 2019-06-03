import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;

/**
 * Most simple Zuul graphical user interface class.
 * It can be used as an alternative to the console text interface.
 * Four buttons allow the player to walk to a neighboring room.
 * The text area gives feedback to the user.
 *
 * @author Wolfgang Renz
 * @version 2019.05.16
 */
public class GUI implements PlayerUI
{
    private Player player;
    private JTextArea textArea;

    /**
     * Constructor for objects of class GUI
     */
    public GUI(Player p)
    {
        player = p;
        System.out.println("Gui for player: "+ "\"" + player.getName()+ "\" created!");
        createFrame(player.getName(), 30, 40);
    }

    private void createFrame(String name, int len, int wid)
    {
        JFrame frame= new JFrame(name);
        frame.setVisible(false);

        textArea= new JTextArea(len, wid);
        Container contentPane= frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(textArea), BorderLayout.CENTER);        
        
        //direction buttons panel
        JPanel directionButtons = new JPanel();        
        BoxLayout boxLayout = new BoxLayout(directionButtons, BoxLayout.Y_AXIS);
        directionButtons.setLayout(boxLayout);        
        directionButtons.add(activeButton("north"));
        directionButtons.add(activeButton("south"));
        directionButtons.add(activeButton("east"));
        directionButtons.add(activeButton("west"));
        contentPane.add(directionButtons, BorderLayout.EAST);
        
        
        //start and quit buttons panel
        JPanel mainButtons = new JPanel(new GridLayout(2,0));
        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");
        //quit button panel
        quitButton.addActionListener(e -> {
            QuitCommand quitCmd = new QuitCommand();
            quitCmd.setSecondWord(null);
            quitCmd.execute(player);
            closeWindow(frame);
        });

        //start button panel
        startButton.addActionListener(e -> {
            String newName;
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter new player name:");
            newName = scan.nextLine();
            Game.getGame().addPlayer(new Player(newName));
        });
        
        mainButtons.add(startButton);
        mainButtons.add(quitButton);
        contentPane.add(mainButtons, BorderLayout.WEST);

        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        frame.pack();
        frame.setVisible(true);
    }

    private JButton activeButton(String direction)
    {
        JButton button = new JButton(direction);
        button.addActionListener(new ActionListener()
        // anonymous class definition implementing the ActionListener Interface
            {   
                public void actionPerformed(ActionEvent e)
                {
                    GoCommand cmd= new GoCommand();
                    cmd.setSecondWord(button.getLabel());
                    cmd.execute(player);
                }
            }  
        );
        return button;
    }

    public void println(String s)
    {
        textArea.append(s+ "\n");
    }

    @Override
    public void start() {

    }

    public void closeWindow(JFrame frameToClose){
        frameToClose.dispose();
    }
}
