import java.util.*;

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

    public static HashMap<Player, Room> getPlayersMap() {
        return playersMap;
    }

    public static void setPlayersMap(HashMap<Player, Room> playersMap) {
        Game.playersMap = playersMap;
    }

    private static HashMap<Player,Room> playersMap;
    public Room startRoom;

    /**
     * Create the game and initialise its internal map.
     */
    private Game()
    {
        startRoom= createRooms();
        playersMap = new HashMap<Player, Room>();
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
        //outside.setExit("upstairs", library);

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
        player.setPreviousRoom(startRoom);
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
        welcome(p);
        playersMap.put(p, p.getCurrentRoom());
    }

    public void deletePlayer(Player p)
    {
        farewell(p);
        if(p.equals(playersMap.get(p))) {
            farewell(p);

        }
        System.out.println("Player " + p.getName() + " deleted!");
        if(playersMap.size() == 1){
            System.out.println("This is here Thank you for playing. Have a good day!");
        };
        playersMap.remove(p);
        notifyPlayers(); //not sure if it is working yet
    }
            
    public void notifyPlayers(){
        for (Observer observer : playersMap.keySet()){
            Player p = (Player) observer;

            System.out.println("Current room: " + p.getName() + " " + p.getCurrentRoom().getShortDescription());
            System.out.println("Prev room: "+ p.getName()+ " " + p.getPreviousRoom().getShortDescription());

            for (Player player: playersMap.keySet()) {
                //if we have two different players AND they are in the same room
                if (!player.equals(p) && player.getCurrentRoom().equals(p.getCurrentRoom())) {
                    //if this player did not move AND someone else entered the room
                    if (p.getCurrentRoom().equals(p.getPreviousRoom())) {
                        p.println("Player " + player.getName() + " entered this room!");
                    } else { //the this player entered a room and there is another player here
                        p.println("Player " + player.getName() + " is in this room!");
                    }
                }
            }

            observer.update(playersMap.get(observer).getRoom());

//            if (!p.getCurrentRoom().equals(p.getPreviousRoom())) {
//                p.println("Player " + p.getName() + " " + p.getCurrentRoom().getShortDescription());
//            }
        }
    }
}
