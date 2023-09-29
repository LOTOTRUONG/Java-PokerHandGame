package poker04;

public enum Suits {
	
	DIAMONDS(1),
    CLUBS(2),
    HEARTS(3),
    SPADES(4);
	
	private final int suitValue;
	Suits(final int suitValue) {
		this.suitValue = suitValue;
	}
	
	public int getSuitValue() {
		return this.suitValue;
	}
}


