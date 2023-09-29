package poker04;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	 String name; 
	 List<Cards> hand;
	
	public Player(String name) {
		this.name = name;
		this.hand = new ArrayList<Cards>();
	}
	
	public void addCardtoHand(Cards card) {
		hand.add(card);
	}
	
	public List<Cards> getHand() {
		return hand;
	}

	@Override
	public String toString() {
		return name;
	}
}
