
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;


public class GameTests {
	
	@Test
	public void test1() throws IOException{
		System.out.println("test1");
		Game g = new Game(3);
		System.out.println("test2");
		g.setPlayerPos(1, 1, 0);
		System.out.println("test3");
		boolean b = g.accusation(new Weapon("dagger"), new Room("kitchen"), new Room("Miss Scarlett"));
		System.out.println(b);
		System.out.println("test4");
		assertFalse(b);
	}
}
