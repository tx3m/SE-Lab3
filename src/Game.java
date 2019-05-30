import java.util.ArrayList;
import java.util.List;

/**
 *  This class is the game class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  This class creates the game environment consisting 
 *  of a simple labyrinth of rooms and returns its Instance to the player.
 *  It also welcomes and farewells the player.
 * 
 * @author  Michael Kolling and David J. Barnes, modified by Wolfgang Renz
 * @version 2011.07.31, 2019.05.16
 */

public class Game implements Observable
{
    private static Game game;
    //private Player player; // single player
    private ArrayList<Player> players;
    public Room startRoom;

    /**
     * Create the game and initialise its internal map.
     */
    private Game()
    {
        startRoom= createRooms();
        players = new ArrayList<Player>();
    }

    public static Game getGame()
    {
        if(game == null){
            game = new Game();
        }
        return game; 
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room outside, theatre, pub, lab, office, library;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        library = new Room("in the BT7 library");

        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("upstairs", library);

        library.setExit("downstairs", outside);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        return outside;
    }

    /**
     * Print out the opening message for the player.
     */
    private void welcome(Player player)
    {
        player.setCurrentRoom(startRoom);
        player.println("");
        player.println("Welcome to The World of Zuul!");
        player.println("The World of Zuul is a new, incredibly boring adventure game.");
        player.println("Type 'help' if you need help.");
        player.println("");
        player.println(player.getCurrentRoom().getLongDescription());
    }

    private void farewell(Player p)
    {
        p.println(p.getName() +" Thank you for playing.  Good bye.");
    }

    public void addPlayer(Player p)
    {
        //player = p; // overwrites an existing player
        //welcome(player);
        players.add(p);
        welcome(players.get(players.indexOf(p)));
        notifyPlayers();
    }

    public void deletePlayer(Player p)
    {
        //if(p.equals(player)){
        //    farewell(player);
        //}
        int playerIndex = players.indexOf(p);
        if(p.equals(players.get(playerIndex))) {
            farewell(p);
        }
        /* player index +1 as we would start from 0, which doesn't make sense to show */
        System.out.println("Player " + (playerIndex + 1) + " deleted!");
        players.remove(playerIndex);
        notifyPlayers();
    }
            
    public void notifyPlayers(){
        for (Player player : players){
            player.update(game, startRoom);
        }
    }
}
