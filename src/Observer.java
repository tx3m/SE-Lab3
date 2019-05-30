
/**
 * Write a description of interface Observer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Observer
{
    /**
     * This method will update all Players with the changes in Game
     */
   void update(Game game, Room room);
}
