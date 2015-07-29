
public class Character implements Card {
	

	private String type;

	public Character(String text) {
		type = text;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Character){
			Character c = (Character) o;
			if(c.toString().equalsIgnoreCase(type))return true;
		}
		return false;
	}
	
	public String toString(){
		return type;
	}

}
