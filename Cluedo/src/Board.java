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
//		Room start = new Room("start");
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
//		loc.put("S  ", start);
		loc.put("░░ ", door);
		loc.put("▓▓ ", wall);
		loc.put("◊◊ ", portal);
		loc.put("∆∆ ", portal);
		
		createBoard();
//		drawBoard();
	}
	
	public boolean checkMove(int preX, int preY, String dir){
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
			return false;
		}
		
		if(newX < 0 || newX > 23 || newY < 0 || newY > 24){
			return false;
		}
		
		System.out.println(preX+ " " + preY + " " + newX + " " + newY);
		Room preLoc = loc.get(board[preY][preX]);
		Room newLoc = loc.get(board[newY][newX]);
		if(preLoc.equals(loc.get("░░ ")) || newLoc.equals(loc.get("░░ "))){
			return true;
		}
		if(newLoc.toString().equals(preLoc.toString()) || preLoc.toString().equals("S  ")){
			return true;
		}
		
		return false;
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
			board[y][0] = "▓▓ ";
			board[y][23] = "▓▓ ";
		}
		board[1][6] = "▓▓ ";
		board[1][17] = "▓▓ ";
		
		for (int x = 0; x < 24; ++x){
			board[0][x] = "▓▓▓";
			board[24][x] = "▓▓ ";
		}
		board[0][8] = "▓  ";
		

		board[7][0] = ".  ";
		board[24][16] = ".  ";

		//draw kitchen
		for (int y = 1; y < 7; ++y){
			for (int x = 0; x < 6; ++x){
				board[y][x] = "K  ";
			}
		}
		board[6][0] = "▓▓ ";

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
		board[5][23] = "▓▓ ";

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
		board[24][17] = "▓▓ ";

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
		board[24][6] = "▓▓ ";

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
		board[7][4] = "░░ ";
		board[5][7] = "░░ ";
		board[8][9] = "░░ ";
		board[8][14] = "░░ ";
		board[5][16] = "░░ ";
		board[5][18] = "░░ ";
		board[9][17] = "░░ ";
		board[16][16] = "░░ ";
		board[20][17] = "░░ ";
		board[20][15] = "░░ ";
		board[17][11] = "░░ ";
		board[17][12] = "░░ ";
		board[18][6] = "░░ ";
		board[16][6] = "░░ ";
		board[12][8] = "░░ ";

		//draw start points
		board[0][9] = ".  ";
		board[0][14] = ".  ";
		board[6][23] = ".  ";
		board[19][23] = ".  ";
		board[24][7] = ".  ";
		board[17][0] = ".  ";

		//draw portals
		board[1][5] = "◊◊ ";
		board[21][23] = "◊◊ ";
		board[5][22] = "∆∆ ";
		board[19][0] = "∆∆ ";
	}
	
	public void drawBoard(List<Player> players) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 25; ++i){
			System.out.print("▄▄▄");
		}
		System.out.println();
		for (int y = 0; y < 25; ++y){
			System.out.print("▌▌");
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
			System.out.print("▌▌");
			System.out.println();
		}
		for (int i = 0; i < 25; ++i){
			System.out.print("▀▀▀");
		}
		System.out.println();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
