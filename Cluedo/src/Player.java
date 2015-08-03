import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Player {
	private int Xpos = 0;
	private int Ypos = 0;
	private List<Card> hand = new ArrayList<>();
	private String name;
	
	public Player(String n){
		this.setName(n);
	}

	public boolean holding(Card card) {
		for(Card c: hand){
			if(c.equals(card)){
				return true;
			}
		}
		return false;
	}
	/**
	 * This method takes the direction as a N, E, S, W and updates the player position accordingly
	 * It checks with the board to make sure that it is a valid move
	 * @param direction - the compass direction of the way to move
	 * @return - if it was a valid move and player position was updated
	 */
	public boolean move(String direction) {
		if(direction.equalsIgnoreCase("N")){
			Ypos = Ypos-1;
			return true;
		}else if(direction.equalsIgnoreCase("E")){
			Xpos = Xpos+1;
			return true;
		}else if(direction.equalsIgnoreCase("S")){
			Ypos = Ypos +1;
			return true;
		}else if(direction.equalsIgnoreCase("W")){
			Xpos = Xpos -1;
			return true;
		}
		return false;
	}

	public void setYpos(Integer integer) {
		Ypos = integer;
	}

	public void setXpos(Integer integer) {
		Xpos = integer;
	}

	public void showHand() {
		for(Card c: hand){
			System.out.println(c);
		}
	}
	public void addCard(Card c) {
		hand.add(c);
	}
	public int getX() {
		return Xpos;
	}
	public int getY() {
		return Ypos;
	}
	public void updatePosition(Point newPoint) {
		Xpos = (int) newPoint.getX();
		Ypos = (int) newPoint.getY();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
