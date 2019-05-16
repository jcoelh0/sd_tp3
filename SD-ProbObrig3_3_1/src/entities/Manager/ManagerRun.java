package entities.Manager;

import static settings.Constants.*;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class ManagerRun {

    /**
     * Starts the manager.
     * @param args -
     */
    public static void main(String[] args) {
        Manager manager = new Manager(N_CUSTOMERS);
        manager.start();
        try {
            manager.join();
        }
        catch(InterruptedException ex) {
            System.out.println("Manager died!");
        }
    }
}
