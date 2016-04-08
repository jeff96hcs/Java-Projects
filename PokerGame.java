package PJ4;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each playerHand. 
 * The player is dealt one five-card poker playerHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. Jacks or Better: a pair pays out only if the cards in the pair are Jacks, 
 * 	Queens, Kings, or Aces. Lower pairs do not pay out. 
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the main poker game class.
 * It uses Decks and Card objects to implement poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */


public class PokerGame {

    // default constant values
    private static final int startingBalance=100;
    private static final int numberOfCards=5;

    // default constant payout value and playerHand types
    private static final int[] multipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] goodHandTypes={ 
	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // must use only one deck
    private static final Decks oneDeck = new Decks(1);

    // holding current poker 5-card hand, balance, bet    
    private List<Card> playerHand;
    private int playerBalance;
    private int playerBet;

    /** default constructor, set balance = startingBalance */
    public PokerGame()
    {
    	this(startingBalance);
    }

    /** constructor, set given balance */
    public PokerGame(int balance)
    {
    	this.playerBalance= balance;
    }

    /** This display the payout table based on multipliers and goodHandTypes arrays */
    private void showPayoutTable()
    { 
    	System.out.println("\n\n");
    	System.out.println("Payout Table   	      Multiplier   ");
    	System.out.println("=======================================");
    	int size = multipliers.length;
    	for (int i=size-1; i >= 0; i--) {
    		System.out.println(goodHandTypes[i]+"\t|\t"+multipliers[i]);
    	}
    	System.out.println("\n\n");
    }

    /** Check current playerHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {
    	ListIterator<Card> playerHandIterator = playerHand.listIterator();
    	ArrayList<Integer> RankArray = new ArrayList<Integer>() ;
    	int i = 0;
        while(i < playerHand.size()){
        	int currentRank = playerHand.get(i).getRank();
        	RankArray.add(currentRank);
        	i++;
        }
        java.util.Collections.sort(RankArray);
        String yourHandType = "Sorry, you lost";
        i = 0;
        boolean sameSuit = true;
        boolean isConsecutive = true;
        while(i<playerHand.size()){
    		int currentSuit = playerHand.get(i).getSuit();
    		if(i!=4){
    			if(currentSuit != playerHand.get(i+1).getSuit()){
    				sameSuit = false;
    			}
    			if((RankArray.get(i+1)-RankArray.get(i))!=1){
    				if(RankArray.get(0)!=1 || RankArray.get(1)!=10){
    					isConsecutive = false;
    				}
    				else if(i >= 2){
    					isConsecutive = false;
    				}
    			}
    		}
    		i++;
    	}
        i = 0;
        if(isConsecutive == true){        	
        	if(sameSuit == true){        		
        		if(RankArray.get(4)==13 && RankArray.get(0)==1){
        			yourHandType = goodHandTypes[8];
        			playerBalance += playerBet*(multipliers[8]-1);
        		}
        		else{
        			yourHandType = goodHandTypes[7];
        			playerBalance += playerBet*(multipliers[7]-1);
        		}
        	}
        	else{
        		yourHandType = goodHandTypes[3];
        		playerBalance += playerBet*(multipliers[3]-1);
        	}
        }
        else{
        	if(sameSuit == true){
        		yourHandType = goodHandTypes[4];
        	}
        	while(i < RankArray.size()-1){
        		if(i<2 && RankArray.get(i)==RankArray.get(i+3)){
        			yourHandType = goodHandTypes[6];
        			playerBalance += playerBet*(multipliers[6]-1);
        			break;
        		}
        		else if(i<3 && RankArray.get(i)==RankArray.get(i+2)){
        			if(i==0 && RankArray.get(i+3)==RankArray.get(i+4)){
        				yourHandType = goodHandTypes[5];
        				playerBalance += playerBet*(multipliers[5]-1);
        				break;
        			}
        			else{
        				if(sameSuit == true){
        					yourHandType = goodHandTypes[4];
        					playerBalance += playerBet*(multipliers[4]-1);
        					break;
        				}
        				else{
        					yourHandType = goodHandTypes[2];
        					playerBalance += playerBet*(multipliers[2]-1);
            				break;
        				}        				
        			}
        		}
        		else if (i<4 && RankArray.get(i)==RankArray.get(i+1)){
        			if(i == 0  && RankArray.get(i+2)==RankArray.get(i+4)){
        				yourHandType = goodHandTypes[5];
        				playerBalance += playerBet*(multipliers[5]-1);
        				break;
        			}
        			else if(i<2 && RankArray.get(i+2)==RankArray.get(i+3)){
        				if(sameSuit == true){
        					yourHandType = goodHandTypes[4];
        					playerBalance += playerBet*(multipliers[4]-1);
        					break;
        				}
        				else{
        					yourHandType = goodHandTypes[1];
        					playerBalance += playerBet*(multipliers[1]-1);
        					break;
        				}
        			}
        			else if(i == 0 && RankArray.get(i+3)==RankArray.get(i+4)){
        				if(sameSuit == true){
        					yourHandType = goodHandTypes[4];
        					playerBalance += playerBet*(multipliers[4]-1);
        					break;
        				}
        				else{
        					yourHandType = goodHandTypes[1];
        					playerBalance += playerBet*(multipliers[1]-1);
        					break;
        				}
        			}
        			else if(RankArray.get(i)>=10){
        				if(sameSuit == true){
        					yourHandType = goodHandTypes[4];
        					playerBalance += playerBet*(multipliers[4]-1);
        					break;
        				}
        				else{
        					yourHandType = goodHandTypes[0];
        					playerBalance += playerBet*(multipliers[0]-1);
        					break;
        				}
        			}
        		}
        		i++;
        	}
        	
        }

        
        System.out.println(yourHandType);
        if(yourHandType == "Sorry, you lost"){
        	playerBalance-=playerBet;
        }
        // implement this method!
    }


    /*************************************************
     *   add other private methods here ....
     *
     *************************************************/


    public void play() 
    {
    /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for positions of cards to keep  
     *          get positions in one input line
     *		update cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */
    	showPayoutTable();
        Scanner input = new Scanner(System.in);
        showPayoutTable();
        while (playerBalance > 0) {
            System.out.println("Balance:$" + playerBalance);
            System.out.print("Enter Bet:");
            playerBet = input.nextInt();
            while (playerBet > playerBalance) {
                System.out.print("That is not a valid input! Please enter bet again: ");
                playerBet = input.nextInt();
            }
            oneDeck.reset();
            oneDeck.shuffle();
            try {
                playerHand = oneDeck.deal(numberOfCards);
            } catch (PlayingCardException pce) {
                System.out.println("Not enough cards left!");
            }
            System.out.println("Hand: " + playerHand);
            String positions = input.nextLine();
            System.out.println("Enter positions of cards to keep (e.g. 1 4 5 ):");
            positions = input.nextLine();
            Scanner in = new Scanner(positions);
            boolean[] keepPosition = {false, false, false, false, false};            
            while(in.hasNextInt()){
                int pos = in.nextInt();
                keepPosition[pos-1]= true;                
            }
            for(int i = 0; i < playerHand.size(); i++){
                if(keepPosition[i]==false){
                    try{
                        playerHand.set(i, oneDeck.deal(1).get(0));
                    }
                    catch(PlayingCardException pce){
                        System.out.println("Not enough cards to deal!");
                    }
                }
            }
            System.out.println();
            System.out.println("Hand: " + playerHand);
            checkHands();
            System.out.println();
            if (playerBalance == 0) {
                System.out.println("Your balance is $0");
                System.out.println("Bye!");
                break;
            } else {
                System.out.print("Your Balance:$" + playerBalance + ", one more game(y or n)?");
                String answer = input.nextLine();                
                if (answer.equals("n")) {
                	System.out.println("Game Over! Bye!");
                    break;                   
                } else {
                	System.out.println();
                    System.out.print("Want to see checkout table? (y or n)");
                    answer = input.next();
                    if (answer.equals("y")) {
                        showPayoutTable();
                    }
                }
            }
        }
    }



    /*************************************************
     *   Do not modify methods below
    /*************************************************


    /** testCheckHands() is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */ 

    private void testCheckHands()
    {
      	try {
    		playerHand = new ArrayList<Card>();

		// set Royal Flush
		playerHand.add(new Card(1,4));
		playerHand.add(new Card(10,4));
		playerHand.add(new Card(12,4));
		playerHand.add(new Card(11,4));
		playerHand.add(new Card(13,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight Flush
		playerHand.set(0,new Card(9,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight
		playerHand.set(4, new Card(8,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Flush 
		playerHand.set(4, new Card(5,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

		// set Four of a Kind
		playerHand.clear();
		playerHand.add(new Card(8,4));
		playerHand.add(new Card(8,1));
		playerHand.add(new Card(12,4));
		playerHand.add(new Card(8,2));
		playerHand.add(new Card(8,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Three of a Kind
		playerHand.set(4, new Card(11,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Full House
		playerHand.set(2, new Card(11,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Two Pairs
		playerHand.set(1, new Card(9,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Royal Pair
		playerHand.set(0, new Card(3,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// non Royal Pair
		playerHand.set(2, new Card(3,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		System.out.println(e.getMessage());
      	}
    }


    /* Run testCheckHands() */
    public static void main(String args[]) 
    {
	PokerGame pokergame = new PokerGame();
	pokergame.testCheckHands();
	
    }
}
