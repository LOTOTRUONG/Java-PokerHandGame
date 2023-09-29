package poker04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
 
	private List<Cards> cards;
	
	public Deck() {
		cards = new ArrayList<Cards>();
		initializeDeck();
		
	}

		private void initializeDeck() {
			for(Suits suit:Suits.values()) {
				for(Ranks rank : Ranks.values()) {
					cards.add(new Cards(suit, rank));
				}
			}
		}
		
		public void shuffle() {
			Collections.shuffle(cards);
		}

		public Cards dealCard() {
			if (cards.isEmpty()) {
				return null;
			}
			return cards.remove(cards.size()-1);
		}
			
		public int size() {
			return cards.size();
		}
	
}
