package poker03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//code by Loan TRUONG

public class PokerGame {
	    public static void main(String[] args) {
	        List<String> deck = generateDeck();
	        Collections.shuffle(deck);
	        
	        List<List<String>> playersHands = new ArrayList<>();
	        for (int i = 0; i < 4; i++) {
	            List<String> hand = dealHand(deck);
	            playersHands.add(hand);
	            System.out.println("Player " + (i + 1) + " Hand: " + hand);
	        }
            System.out.println("\n");

	        
	        List<String> playerRankings = new ArrayList<>();
	        for (List<String> hand : playersHands) {
	            String ranking = evaluateHand(hand);
	            playerRankings.add(ranking);
	            System.out.println("Player " + (playersHands.indexOf(hand) + 1) + " has: " + ranking);
	        }
            System.out.println("\n");

	        int winnerIndex = determineWinner(playersHands);
	        System.out.println("Winner: Player " + (winnerIndex));
	    }
	    
	    public static List<String> generateDeck() {
	        List<String> deck = new ArrayList<>();
	        String[] suits = {"C", "S", "H", "D"}; //C for clubs, S for spades, H for hearts, D for diamonds
	        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
	        
	        for (String suit : suits) {
	            for (String rank : ranks) {
	                deck.add(rank + suit);
	            }
	        }
	        
	        return deck;
	    }
	    
	    public static List<String> dealHand(List<String> deck) {
	        List<String> hand = new ArrayList<>();
	        for (int i = 0; i < 5; i++) {
	            hand.add(deck.remove(0));
	        }
	        return hand;
	    }
	    
	    public static String evaluateHand(List<String> hand) {
	    	 if (isRoyalFlush(hand)) {
	             return "Royal Flush";
	         } else if (isStraightFlush(hand)) {
	             return "Straight Flush";
	         } else if (isFourOfAKind(hand)) {
	             return "Four of a Kind";
	         } else if (isFullHouse(hand)) {
	             return "Full House";
	         } else if (isFlush(hand)) {
	             return "Flush";
	         } else if (isStraight(hand)) {
	             return "Straight";
	         } else if (isThreeOfAKind(hand)) {
	             return "Three of a Kind";
	         } else if (isTwoPair(hand)) {
	             return "Two Pair";
	         } else if (isOnePair(hand)) {
	             return "One Pair";
	         } else {
	             return "High Card";
	         }
	     }

	     public static boolean isRoyalFlush(List<String> hand) {
	         return isStraightFlush(hand) && containsAllRanks(hand, "T", "J", "Q", "K", "A");
	     }

	     public static boolean isStraightFlush(List<String> hand) {
	         return isFlush(hand) && isStraight(hand);
	     }

	     public static boolean isFourOfAKind(List<String> hand) {
	         return containsSameRankNTimes(hand, 4);
	     }

	     public static boolean isFullHouse(List<String> hand) {
	         return containsSameRankNTimes(hand, 3) && containsSameRankNTimes(hand, 2);
	     }

	     public static boolean isFlush(List<String> hand) {
	         return hand.stream().map(card -> card.charAt(1)).distinct().count() == 1;
	     }

	     public static boolean isStraight(List<String> hand) {
	      
	    	 List<Integer> ranks = hand.stream()
	                 .map(card -> "23456789TJQKA".indexOf(card.charAt(0)))
	                 .sorted()
	                 .collect(Collectors.toList());

	         for (int i = 1; i < ranks.size(); i++) {
	             if (ranks.get(i) != ranks.get(i - 1) + 1) {
	                 return false;
	             }
	         }

	         return true;
	     }

	     public static boolean isThreeOfAKind(List<String> hand) {
	         return containsSameRankNTimes(hand, 3);
	     }

	     public static boolean isTwoPair(List<String> hand) {
	         int pairs = 0;
	         Map<Character, Integer> rankCounts = new HashMap<>();

	         for (String card : hand) {
	             char rank = card.charAt(0);
	             rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
	         }

	         for (int count : rankCounts.values()) {
	             if (count == 2) {
	                 pairs++;
	             }
	         }

	         return pairs == 2;
	     }

	     public static boolean isOnePair(List<String> hand) {
	         return containsSameRankNTimes(hand, 2);
	     }

	     public static boolean containsSameRankNTimes(List<String> hand, int n) {
	         Map<Character, Integer> rankCounts = new HashMap<>();

	         for (String card : hand) {
	             char rank = card.charAt(0);
	             rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
	         }

	         return rankCounts.values().contains(n);
	     }

	     public static boolean containsAllRanks(List<String> hand, String... ranks) {
	         Set<String> rankSet = new HashSet<>(Arrays.asList(ranks));
	         return hand.stream().map(card -> card.charAt(0)).allMatch(rankSet::contains);
	     }
	    
	    
	     public static int determineWinner(List<List<String>> playersHands) {
	    	
	    	   int winnerIndex = 0;
	           String winnerRanking = "";

	           for (int i = 0; i < playersHands.size(); i++) {
	               String ranking = evaluateHand(playersHands.get(i));
	               if (compareRankings(ranking, winnerRanking) > 0) {
	                   winnerIndex = i+1;
	                   winnerRanking = ranking;
	               } else if (compareRankings(ranking, winnerRanking) == 0) {
	                   int tiebreakerResult = resolveTiebreaker(playersHands.get(i), playersHands.get(winnerIndex));
	                   if (tiebreakerResult > 0) {
	                       winnerIndex = i+1;
	                       winnerRanking = ranking;
	                   }
	               }
	           }


	           return winnerIndex;
	    }
	    public static int resolveTiebreaker(List<String> hand1, List<String> hand2) {
	    	int result = compareHighestCard(hand1, hand2);
	        if (result != 0) {
	            return result;
	        }

	        return 0;
	    }
			

	    public static int compareHighestCard(List<String> hand1, List<String> hand2) {
			 int rankValue1 = getHighestCardRankValue(hand1);
			    int rankValue2 = getHighestCardRankValue(hand2);

			    return Integer.compare(rankValue1, rankValue2);
		}

		private static int getHighestCardRankValue(List<String> hand) {
		
		    int highestRankValue = 0;

		    for (String card : hand) {
		        char rank = card.charAt(0);
		        int rankValue = rankValueFromString(rank);

		        if (rankValue > highestRankValue) {
		            highestRankValue = rankValue;
		        }
		    }

		    return highestRankValue;
		}

		private static int rankValueFromString(char rank) {
			 switch (rank) {
			 	case '2':
		            return 2;
		        case '3':
		            return 3;
		        case '4':
		            return 4;
		        case '5':
		            return 5;
		        case '6':
		            return 6;
		        case '7':
		            return 7;
		        case '8':
		            return 8;
		        case '9':
		            return 9;
		        case 'T':
		            return 10;
		        case 'J':
		            return 11;
		        case 'Q':
		            return 12;
		        case 'K':
		            return 13;
		        case 'A':
		            return 14;
		        default:
		            throw new IllegalArgumentException("Invalid rank: " + rank); } 
		}

		public static int compareRankings(String ranking1, String ranking2) {
	    	   Map<String, Integer> rankingValues = new HashMap<>();
	    	    rankingValues.put("Royal Flush", 10);
	    	    rankingValues.put("Straight Flush", 9);
	    	    rankingValues.put("Four of a Kind", 8);
	    	    rankingValues.put("Full House", 7);
	    	    rankingValues.put("Flush", 6);
	    	    rankingValues.put("Straight", 5);
	    	    rankingValues.put("Three of a Kind", 4);
	    	    rankingValues.put("Two Pair", 3);
	    	    rankingValues.put("One Pair", 2);
	    	    rankingValues.put("High Card", 1);

	    	    int value1 = rankingValues.getOrDefault(ranking1, 0);
	    	    int value2 = rankingValues.getOrDefault(ranking2, 0);
	   
	    	    return Integer.compare(value1, value2);
	    }
	}



