package tjc.rug.ExpertSystem;

/**
 * This class is to implement a workaround for creating a "fat jar" of the application
 */
public class ExpertSystemMain {

    /**
     * Immediately call the original main
     * @param args  Command line arguments (Not currently used)
     */
    public static void main(String[] args) {
        Main.main(args);
    }
}

