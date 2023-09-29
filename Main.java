package poker04;


import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		Deck deck = new Deck();
		deck.shuffle();
		
		
		  	Player[] players = new Player[4];
	        players[0] = new Player("Player 1");
	        players[1] = new Player("Player 2");
	        players[2] = new Player("Player 3");
	        players[3] = new Player("Player 4");
	        
	        //deal cards to player 
	        
	        for (int i = 0; i < 5; i++) {
	        	for (Player player : players) {
	        		Cards card = deck.dealCard();
	        		if (card != null) {
	        			player.addCardtoHand(card);
	                   
	        		}
	        	}
	        }
	        
	        //Display carte and their value for each player
	        
	        for(Player player : players) {
	        	System.out.println("\n" + player + " hand:");
	        	List<Cards> hand = player.getHand();
	        	for(Cards card : hand) {
	        		System.out.println("   " +card);
	        	}
		        String handValue = RankingPlayer.evaluateHand(hand);
		        System.out.println();
		        System.out.println( player + " has " + handValue);
		        

	        }
	        
	        
	       
	        
	        Player winner = RankingPlayer.determineWinner(players);
	        System.out.println("\n\nWinner is: " + winner);
	}

	
}



