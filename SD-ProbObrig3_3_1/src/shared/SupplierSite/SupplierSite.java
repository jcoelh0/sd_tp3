package shared.SupplierSite;

import entities.Manager.Interfaces.IManagerSS;
import interfaces.RepositoryInterface;
import settings.Piece;
import settings.Constants;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class SupplierSite implements IManagerSS {
    
    private Piece partNeeded;
    private int[] piecesBought;

    String rmiRegHostName;
    int rmiRegPortNumb;
    private final RepositoryInterface repositoryInterface;
    
    public SupplierSite(RepositoryInterface repository, String rmiRegHostName, int rmiRegPortNumb) {
        
        int nTypePieces = Constants.N_TYPE_PIECES;
        
        piecesBought = new int[nTypePieces];
        for (int i = 0; i < nTypePieces; i++) {
            piecesBought[i] = 0;
        }
        
        this.repositoryInterface = repository;
        this.rmiRegHostName = rmiRegHostName;
        this.rmiRegHostName = rmiRegHostName;
    }

    /**
     * Manager's method. Manager goes into supplier site and buys the required
     * pieces in a random amount.
     *
     * @param partNeeded a piece that the manager needs to buy
     * @return an Integer representing the amount of pieces he bought
     */
    @Override
    public synchronized int goToSupplier(Piece partNeeded) {
        int randomNum = 2;
        System.out.println("Manager - Bought " + randomNum + " of " + partNeeded);
        this.partNeeded = partNeeded;
        piecesBought[partNeeded.getTypePiece().ordinal()] += randomNum;
        //updatePiecesBought(piecesBought);
        return randomNum;
    }

    /**
     * Method used for log. Retrieves the total number of pieces bought by the
     * manager for each type of piece.
     *
     * @return an Array with the total number of pieces bought for each type of
     * piece
     */
    public int[] getPiecesBought() {
        return piecesBought;
    }
    
    /*
    private synchronized void updatePiecesBought(int[] piecesBought) {
        RepositoryMessage response;
        startCommunication(cc_repository);
        cc_repository.writeObject(new RepositoryMessage(RepositoryMessage.NUMBER_PARTS_PURCHASED, piecesBought));
        response = (RepositoryMessage) cc_repository.readObject();
        cc_repository.close(); 
    }
    
    private void startCommunication(ChannelClient cc) {
        while(!cc.open()) {
            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {
                
            }
        }
    }
*/
}
