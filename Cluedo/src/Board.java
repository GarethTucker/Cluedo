import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Board implements KeyListener{
	private String[][] board;
	private Map<String, Room> loc;
	
	public Board(){
		
		board = new String[25][24];
		
		loc = new HashMap();
		
		//Rooms
		Room kitchen = new Room("kitchen");
		Room ballRoom = new Room("ballRoom");
		Room conservatory = new Room("conservatory");
		Room billiardRoom = new Room("billiardRoom");
		Room library = new Room ("library");
		Room study = new Room("study");
		Room hall = new Room("hall");
		Room lounge = new Room("lounge");
		Room diningRoom = new Room("diningRoom");
		Room center = new Room("center");
		
		//other place
		Room ground = new Room("ground");
		Room start = new Room("start");
		Room door = new Room("door");
		Room wall = new Room("wall");
		Room portal = new Room("portal");
		
		//create the board
		loc.put("K  ", kitchen);
		loc.put("B  ", ballRoom);
		loc.put("C  ", conservatory);
		loc.put("Bi ", billiardRoom);
		loc.put("Li ", library);
		loc.put("St ", study);
		loc.put("H  ", hall);
		loc.put("L  ", lounge);
		loc.put("D  ", diningRoom);
		loc.put("X  ", center);
		
		loc.put(".  ", ground);
		loc.put("S  ", start);
		loc.put("DD ", door);
		loc.put("XX ", wall);
		loc.put("P1 ", portal);
		loc.put("P2 ", portal);
		
		createBoard();
//		drawBoard();
	}
	
	public Point checkMove(int preX, int preY, String dir, List<Player> playerList){
		
		int newX = preX;
		int newY = preY;
//		System.out.println(dir);
//		board[preY][preX] = "PP ";
		if(dir.equalsIgnoreCase("N")){
			newY = preY-1;
		}else if(dir.equalsIgnoreCase("E")){
			newX = preX+1;
		}else if(dir.equalsIgnoreCase("S")){
			newY = preY+1;
		}else if(dir.equalsIgnoreCase("W")){
			newX = preX-1;
		}else{
			return null;
		}
		
		Point newPoint = new Point(newX, newY);
		
		if(newX < 0 || newX > 23 || newY < 0 || newY > 24){
			return null;
		}
		
		System.out.println(preX+ " " + preY + " " + newX + " " + newY);
		Room preLoc = loc.get(board[preY][preX]);
		Room newLoc = loc.get(board[newY][newX]);
		System.out.println(newLoc);
		
		//go into/out a room from door
		if(preLoc.equals(loc.get("DD ")) 
			|| newLoc.equals(loc.get("DD "))){
			return newPoint;
		}
		
		//check wall
		if (newLoc.equals(loc.get("XX "))){
			return null;
		}
		
		//start from a start position
		if(newLoc.equals(preLoc) 
			|| preLoc.equals(loc.get("S  "))){
			return newPoint;
		}
		
		//go in to a portal
		if (!preLoc.equals(loc.get(".  ")) 
				&& (newLoc.equals(loc.get("P1 ")) || newLoc.equals(loc.get("P2 ")))){
			newPoint = processPortal(newPoint);
			return newPoint;
		}
			
		//go out of a portal
		if ((preLoc.equals(loc.get("P1 ")) || preLoc.equals(loc.get("P2 "))) 
				&& !newLoc.equals(loc.get(".  "))){
			return newPoint;
		}
		
		return null;
	}

	private Point processPortal(Point newPoint) {
		// TODO Auto-generated method stub
		if (newPoint.getX() == 5 && newPoint.getY() ==1){
			newPoint.setLocation(23, 21);
			System.out.println(newPoint.toString());
			return newPoint;
		}
		if (newPoint.getX() == 23 && newPoint.getY() ==21){
			newPoint.setLocation(5, 1);
			return newPoint;
		}
		if (newPoint.getX() == 22 && newPoint.getY() ==5){
			newPoint.setLocation(0, 19);
			return newPoint;
		}
		if (newPoint.getX() == 0 && newPoint.getY() ==19){
			newPoint.setLocation(22, 5);
			return newPoint;
		}
		return newPoint;
	}

	public String getRoom(int x, int y){
		return loc.get(board[y][x]).toString();
		
	}
	
	private void createBoard(){
		//draw ground
		for (int y = 0; y < 25; ++y){
			for (int x = 0; x < 24; ++x){
				board[y][x] = ".  ";
			}
		}

		//draw walls
		for (int y = 0; y < 25; ++y){
			board[y][0] = "XX ";
			board[y][23] = "XX ";
		}
		board[1][6] = "XX ";
		board[1][17] = "XX ";
		
		for (int x = 0; x < 24; ++x){
			board[0][x] = "XX ";
			board[24][x] = "XX ";
		}
		board[0][8] = "XX ";
		

		board[7][0] = ".  ";
		board[24][16] = ".  ";

		//draw kitchen
		for (int y = 1; y < 7; ++y){
			for (int x = 0; x < 6; ++x){
				board[y][x] = "K  ";
			}
		}
		board[6][0] = "XX ";

		//draw ballRoom
		for (int y = 2; y < 8; ++y){
			for (int x = 8; x < 16; ++x){
				board[y][x] = "B  ";
			}
		}
		for (int y = 0; y < 2; ++y){
			for (int x = 10; x < 14; ++x){
				board[y][x] = "B  ";
			}
		}

		//draw conservatory
		for (int y = 1; y < 6; ++y){
			for (int x = 18; x < 24; ++x){
				board[y][x] = "C  ";
			}
		}
		board[5][23] = "XX ";

		//draw billiardRoom
		for (int y = 8; y < 13; ++y){
			for (int x = 18; x < 24; ++x){
				board[y][x] = "Bi ";
			}
		}

		//draw library
		for (int y = 15; y < 18 ; ++y){
			for (int x = 17; x < 24; ++x){
				board[y][x] = "Li ";
			}
		}
		for (int x = 18; x < 23; ++x){
			board[14][x] = "Li ";
			board[18][x] = "Li ";
		}

		//draw study
		for (int y = 21; y < 25 ; ++y){
			for (int x = 17; x < 24; ++x){
				board[y][x] = "St ";
			}
		}
		board[24][17] = "XX ";

		//draw hall
		for (int y = 18; y < 25 ; ++y){
			for (int x = 9; x < 15; ++x){
				board[y][x] = "H  ";
			}
		}

		//draw lounge
		for (int y = 19; y < 25 ; ++y){
			for (int x = 0; x < 7; ++x){
				board[y][x] = "L  ";
			}
		}
		board[24][6] = "XX ";

		//draw diningRoom
		for (int y = 10; y < 16 ; ++y){
			for (int x = 0; x < 8; ++x){
				board[y][x] = "D  ";
			}
		}
		for (int x = 0; x < 5; ++x){
			board[9][x] = "D  ";
		}

		//draw doors
		board[7][4] = "DD ";
		board[5][7] = "DD ";
		board[8][9] = "DD ";
		board[8][14] = "DD ";
		board[5][16] = "DD ";
		board[5][18] = "DD ";
		board[9][17] = "DD ";
		board[16][16] = "DD ";
		board[20][17] = "DD ";
		board[20][15] = "DD ";
		board[17][11] = "DD ";
		board[17][12] = "DD ";
		board[18][6] = "DD ";
		board[16][6] = "DD ";
		board[12][8] = "DD ";

		//draw start points
		board[0][9] = "S  ";
		board[0][14] = "S  ";
		board[6][23] = "S  ";
		board[19][23] = "S  ";
		board[24][7] = "S  ";
		board[17][0] = "S  ";

		//draw portals
		board[1][5] = "P1 ";
		board[21][23] = "P1 ";
		board[5][22] = "P2 ";
		board[19][0] = "P2 ";
	}
	
	public void drawBoard(List<Player> players) {
		// TODO Auto-generated method stub
//		for (int i = 0; i < 25; ++i){
//			System.out.print("▄▄▄");
//		}
		System.out.println();
		for (int y = 0; y < 25; ++y){
//			System.out.print("▌▌");
			for (int x = 0; x < 24; ++x){
				boolean player = false;
				for(Player p: players){
					if(p.getX() == x && p.getY() == y){
						System.out.print("PP ");
						player = true;
					}
				}
				if(!player){
					System.out.print(board[y][x]);
				}
				player = false;
			}
//			System.out.print("▌▌");
			System.out.println();
		}
//		for (int i = 0; i < 25; ++i){
//			System.out.print("▀▀▀");
//		}
		System.out.println();
	}

//	public static void main(String[] args){
//		new Board();
//	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
