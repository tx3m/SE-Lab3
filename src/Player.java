import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents players in the Zuul game and is the class 
 * to instantiate. 
 * Each player has a current location in the game provided by the game class.
 * In the beginning, the player registers with the game environment.
 * The player class also instantiates two user interfaces,
 * a text-based console and a text-based GUI.
 * 
 * On first use, the console user interface is recommended to use.
 * Since the game environment is awfully simple, it is recommended to extend
 * the game environment, put items into the rooms, let the user collect items
 * up to a weight limit into his/her rucksack, and to let the player win when
 * she is able to find the required items and bring them into the goal room.
 * 
 * @author  Michael Kolling and David J. Barnes, modified by Wolfgang Renz
 * @version 2011.07.31, 2019.05.16
 */
public class Player implements Observer 
{
    private String name;
    private Room currentRoom;
    private PlayerUI cui;
    private PlayerUI gui;
    private Game game;
    private ArrayList<String> backpack;

    /**
     * Constructor for objects of class Player
     */
    public Player(String name, boolean flagGUI)
    {
        this.name= name;
//        if(flagGUI) gui= new GUI(this);
//        ConsoleUI ui = new ConsoleUI(this);
//        cui= ui;

        GUI ui = new GUI(this);
        gui = ui;

        System.out.println(name);

        register();
        //this.game.notifyPlayers();
        ui.start(); // this function has no implementation yet!
        backpack = new ArrayList();
    }

    /**
     * Constructor for objects of class Player
     */
    public Player(String name)
    {
        this(name, false);
    }    

    private void register()
    {
        game= Game.getGame();
        game.addPlayer(this);
    }

    public String getName()
    {
        return this.name;
    }

    public void println(String s)
    {
//        cui.println(s);

        if(gui!= null) gui.println(s);
    }

    /**
     * Return the current room for this player.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Set the current room for this player.
     */
    public void setCurrentRoom(Room room)
    {
        currentRoom = room;
    }

    public void addToBackpack(String itemToAdd){
        backpack.add(itemToAdd);
    }

    /**
     * Try to walk in a given direction. If there is a door
     * this will change the player's location.
     */
    public void walk(String direction)
    {
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            println("There is no door!");
        }
        else {
            setCurrentRoom(nextRoom);
            println(nextRoom.getLongDescription());
        }
    }
/*
    public void pickUp(String itemToPick)
    {
        // Try to pick up item
        Room nextRoom = currentRoom.getExit(itemToPick);

        if (itemAvailable == null) {
            println("There is no item!");
        }
        else {
            addToBackpack(itemToPick);
            println(nextRoom.getLongDescription());
        }
    }

 */

    public void quit()
    {
        game.deletePlayer(this);
    }

    private static void usage()
    {
        System.out.println("First Usage:");
        System.out.println("Player \"Zuul\"");  
        System.out.println("Later you can change it in the "
            +"batch command file to:");
        System.out.println("Player [-GUI] YourPlayerName");                      
    }


    
    public void update(Game game, Room room){
        this.game = game;
        this.currentRoom = room;
    }

    public static void main(String [] argv)
    {
        boolean flagGUI=false;
        //String name= "defaultPlayer";
//        String name;
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter player name:");
//        name = scan.nextLine();
        String name= "italo";

        //What does this loop do?
        for(int i=0; i<argv.length; i++){
            if ( argv[i].equals("-GUI") ) flagGUI=true;
            else name= argv[i];
        }

        new Player(name, flagGUI);
    }
}
