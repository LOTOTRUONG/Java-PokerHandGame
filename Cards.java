package poker04;

public class Cards implements Comparable<Cards> {
	
	private final Ranks ranks;
	private final Suits suits;

	
	public Cards (Suits suits, Ranks ranks) {
		this.suits = suits; 
		this.ranks = ranks;
	}
	
	
	public Suits getSuits() {
		return suits;
	}
	
	public Ranks getRanks(){
		return ranks;
	}
	
	@Override
	    public String toString() {
	        return ranks + " of " + suits;
	    }
	@Override
	public int compareTo(Cards o) {
		return 0;
	}
	
	

}
