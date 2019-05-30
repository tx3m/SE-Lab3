import java.awt.*;
import java.awt.event.*;
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
        player= p;
        createFrame(p.getName(), 60, 80);
    }

    private void createFrame(String name, int len, int wid)
    {
        JFrame frame= new JFrame(name);
        textArea= new JTextArea(len, wid);
        Container contentPane= frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(textArea), BorderLayout.CENTER);
        contentPane.add(activeButton("north"), BorderLayout.NORTH);
        contentPane.add(activeButton("south"), BorderLayout.SOUTH);
        contentPane.add(activeButton("west"), BorderLayout.WEST);
        contentPane.add(activeButton("east"), BorderLayout.EAST);        
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


    public Command getCommand() {

        return null;
    }

    @Override
    public void start() {

    }


}




