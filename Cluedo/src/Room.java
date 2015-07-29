
public class Room implements Card {
	private String name;
	
	public Room(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Room))return false;
		Room r = (Room) o;
		return r.toString().equals(name);
	}
	
	public int hashCode(){
		return name.hashCode();
	}
	
}
