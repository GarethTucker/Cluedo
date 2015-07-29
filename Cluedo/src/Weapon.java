
public class Weapon implements Card {

	String type;

	public Weapon(String string) {
		type = string;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Weapon){
			Weapon w = (Weapon) o;
			if(w.toString().equalsIgnoreCase(type))return true;
		}
		return false;
	}
	
	public String toString(){
		return type;
	}

}
