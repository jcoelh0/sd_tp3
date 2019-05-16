package entities.Customer;

import static settings.Constants.*;

/**
 * Main customer class. Instantiates the customers.
 * 
 * @author André Oliveira
 * @author João Coelho
 */
public class CustomerRun {

    /**
     * Main customer class. Instantiates the customers.
     * @param args -
     */
    public static void main(String[] args) {
        
        Customer[] customers = new Customer[N_CUSTOMERS];

        for(int i = 0; i < N_CUSTOMERS; i++) {
            customers[i] = new Customer(i);
            customers[i].start();
        }

        for(int i = 0; i < N_CUSTOMERS; i++) {
            try {
                customers[i].join();
            }
            catch(InterruptedException ex) {
                System.out.println("Customer " + i + " is dead!");
            }
        }
    }
}
