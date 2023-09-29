package poker04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RankingPlayer {

	

    
    public static String evaluateHand(List<Cards> hand) {
    	
    	Map<Ranks, Long> rankCount = hand.stream().collect(Collectors.groupingBy(Cards :: getRanks, Collectors.counting()));
    	boolean isFlush = isFlush(hand);
        boolean isStraight = isStraight(rankCount);
    	
    	if ( isFlush && isStraight) {
    		if (handContainsRank(hand, Ranks.ACE) && handContainsRank(hand, Ranks.KING)) {
    			return "ROYAL FLUSH";  //Royal Flush
    		}
    		else {
    			return "STRAIGHT FLUSH"; //Straight Flush
    			}
    	}
    		else if(isFourOfAKind(rankCount)) {
    			return "FOUR OF A KIND"; //Fours of a kind 
    		}
    		
    		else if(isFullHouse(rankCount)) {
    			return "FULL HOUSE"; //Fulle House
    		}
    	
    		else if(isFlush) {
    			return "FLUSH"; //Flush
    		}
    		
    		else if(isStraight) {
    			return "STRAIGHT"; //Straight
    		}
    		
    		else if(isThreeOfAKind(rankCount)) {
    			return "THREE OF A KIND"; //Three of a Kind
    		}
    		else if(isTwoPair(rankCount)) {
    			return "TWO PAIR";  //Two Pair
    		}
    		else if (isOnePair(rankCount)) {

    			return "ONE PAIR"; //One Pair
    		}
    		else {
    			return "HIGH CARD"; //High Card
    		}
    }
    


	public static Player determineWinner(Player[] players) {
	     
    	Player winner = players [0];
    	String bestHandValue = evaluateHand(players[0].getHand());
    	
    	for (int i = 1; i < players.length; i++) {
    		String currentHandValue = evaluateHand(players[i].getHand());
    	
    	//compare hand values to determine the winner
    		if(currentHandValue.compareTo(bestHandValue) > 0) {
    			bestHandValue = currentHandValue;
    			winner = players[i];
    		}
    	}
    	
    	return winner;   	
    }
	
	
	
		//method to rank the player 
	private static boolean handContainsRank(List<Cards> hand, Ranks ranks) {
		return hand.stream().anyMatch(card -> card.getRanks() == ranks);
	}
	private static boolean isOnePair(Map<Ranks, Long> rankCount) {
		return rankCount.values().stream().anyMatch(count -> count == 2L);
	}
	
	private static boolean isTwoPair(Map<Ranks, Long> rankCount) {
		long pairCount = rankCount.values().stream().filter(count -> count == 2L).count();
		return pairCount == 2L;
	}
	
	private static boolean isThreeOfAKind(Map<Ranks, Long> rankCount) {
		return rankCount.values().stream().anyMatch(count -> count == 3L);
	}
	
	private static boolean isStraight(Map<Ranks, Long> rankCount) {
		List<Ranks> uniqueRanks = new ArrayList<>(rankCount.keySet()); 
		Collections.sort(uniqueRanks); 
		
		int consecutiveCount = 0; 
		for (int i = 0; i < uniqueRanks.size() - 1; i++) {
			if (uniqueRanks.get(i+1).ordinal() - uniqueRanks.get(i).ordinal() == 1) {
				consecutiveCount++;
			}
			else {
				consecutiveCount = 0;
			}
			
			if (consecutiveCount == 4) {
				return true;
			}
		}
		
		//check for the special case where Ace ca be low (A,2,3,4,5)
		
		if (uniqueRanks.contains(Ranks.ACE) && uniqueRanks.containsAll(Arrays.asList(Ranks.TWO, Ranks.THREE, Ranks.FOUR, Ranks.FIVE))) 
				{
			return true;
		}
		
		return false;
	}

	private static boolean isFlush(List<Cards> hand) {
		Suits firstCardSuit = hand.get(0).getSuits();
		return hand.stream().allMatch(card -> card.getSuits() == firstCardSuit);
		
	}
	
	
	private static boolean isFullHouse(Map<Ranks, Long> rankCount) {
		boolean isThreeOfAKind = rankCount.values().stream().anyMatch(count -> count == 3L);
		boolean isPair = rankCount.values().stream().anyMatch(count -> count == 2L);
		return isThreeOfAKind && isPair;
	}
	
	private static boolean isFourOfAKind(Map<Ranks, Long> rankCount) {
		return rankCount.values().stream().anyMatch(count -> count == 4L);
	}
	
	
}

