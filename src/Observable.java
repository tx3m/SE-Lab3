
/**
 * Write a description of interface Observable here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Observable
{
    /**
     * Those 3 methods will be used to add Players, delete Players and send a notification to all registered players
     */
    void addPlayer(Player p);
    
    void deletePlayer(Player p);
    
    void notifyPlayers();
}
