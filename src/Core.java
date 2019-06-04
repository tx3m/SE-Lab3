public class Core {
    public static void main (String[] args){
       Object game = Game.getGame();
       String name = "Hazoh";
       new Player(name,game);
    }
}
