import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Game {

	private int playerCount;
	private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private List<Player> playerList = new ArrayList<Player>();
	private int currentPlayerIndex = 0;
	private Random randomGenerator = new Random();
	private Board board;
	private boolean rolledDice = false;
	private List<Card> solutionEnvelope = new ArrayList<>();
	private boolean suggestionMade;

	/**
	 * The initial game constructor that creates a board, asks how many players are in the game and
	 * fills the playerList with each player. It then sets the players starting positions, creates
	 * the envelope with the game solution and deals the remaining cards to the players. It then
	 * calls turn to start the game loop.
	 */
	public Game(){
		board = new Board();
		board.drawBoard(playerList);
		try {
			System.out.println("How Many Players?");
			System.out.print("> ");
			String text = input.readLine();
			playerCount = Integer.parseInt(text);

		} catch(IOException e) {
			System.err.println("I/O Error - " + e.getMessage());
		}
		System.out.println(playerCount);
		for(int i=0; i<playerCount; i++)playerList.add(new Player());

		dealCards();
		setPlayerStart();
		turn();

	}
	
	/**
	 * This method creates the accusation envelope and deals the cards
	 * out to the players randomly
	 */
	private void dealCards() {
		List<Card> weaponsDeck = new ArrayList<Card>(){{
			add(new Weapon("Candlestick"));
			add(new Weapon("Dagger"));
			add(new Weapon("Lead Pipe"));
			add(new Weapon("Revolver"));
			add(new Weapon("Rope"));
			add(new Weapon("Spanner"));

		}};
		List<Card> roomsDeck = new ArrayList<Card>(){{
			add(new Weapon("Kitchen"));
			add(new Weapon("Ballroom"));
			add(new Weapon("Conservatory"));
			add(new Weapon("Dining Room"));
			add(new Weapon("Billard Room"));
			add(new Weapon("Library"));
			add(new Weapon("Study"));
			add(new Weapon("Hall"));
			add(new Weapon("Lounge"));

		}};
		List<Card> charactersDeck = new ArrayList<Card>(){{
			add(new Weapon("Miss Scarlett"));
			add(new Weapon("Colonel Mustard"));
			add(new Weapon("Mrs. White"));
			add(new Weapon("The Reverend Green"));
			add(new Weapon("Mrs. Peacock"));
			add(new Weapon("Professor Plum"));

		}};

		int cardIndex = randomGenerator.nextInt(6);
		solutionEnvelope.add(weaponsDeck.remove(cardIndex));
		cardIndex = randomGenerator.nextInt(9);
		solutionEnvelope.add(roomsDeck.remove(cardIndex));
		cardIndex = randomGenerator.nextInt(6);
		solutionEnvelope.add(charactersDeck.remove(cardIndex));

		List<Card> deck = new ArrayList<>();
		deck.addAll(weaponsDeck);
		deck.addAll(roomsDeck);
		deck.addAll(charactersDeck);
		
		int player = 0;
		
		Collections.shuffle(deck);
		for(Card c: deck){
			playerList.get(player).addCard(c);
			player++;
			if(player == playerList.size())player = 0;
		}
	}
	
	/**
	 * Sets the starting positions of the players. This could be in the board
	 * class.
	 */
	private void setPlayerStart(){
		List<Integer> startingPositions = new ArrayList<>();
		startingPositions.add(0);
		startingPositions.add(9);
		startingPositions.add(0);
		startingPositions.add(14);
		startingPositions.add(6);
		startingPositions.add(23);
		startingPositions.add(19);
		startingPositions.add(23);
		startingPositions.add(24);
		startingPositions.add(7);
		startingPositions.add(17);
		startingPositions.add(0);
		int i=0;
		for(Player p: playerList){
			p.setYpos(startingPositions.get(i));
			p.setXpos(startingPositions.get(i+1));
			i=i+2;
		}
	}

	private void turn() {
		Player currentPlayer = playerList.get(currentPlayerIndex);
		int turnChoice = 0;

		board.drawBoard(playerList);

		try {
			System.out.println();
			System.out.println("It is Player "+(currentPlayerIndex+1)+"'s turn");
			System.out.println("What would you like to do?");
			System.out.println("1. Roll Dice");
			System.out.println("2. Make a suggestion");
			System.out.println("3. Make an accusation");
			System.out.println("4. See hand");
			System.out.println("5. End Turn");
			System.out.print("> ");
			String text = input.readLine();
			turnChoice = Integer.parseInt(text);

		} catch(IOException e) {
			System.err.println("I/O Error - " + e.getMessage());
		}
		if(turnChoice == 1 && !rolledDice){
			move();
		}else if(turnChoice == 1 && rolledDice){
			System.out.println("You have already rolled, choose again");
			turn();
		}else if(turnChoice == 2 && !suggestionMade){
			suggestion();
		}else if(turnChoice == 2 && suggestionMade){
			System.out.println("You have already made a suggestion, choose again");
			turn();
		}else if(turnChoice ==3){
			accusation();
		}else if(turnChoice == 4){
			System.out.println("This is your hand");
			showHand();
		}else if(turnChoice == 5){
			System.out.println("End of Player "+(currentPlayerIndex+1)+"'s turn");
			currentPlayerIndex++;
			if(currentPlayerIndex == playerList.size())currentPlayerIndex = 0;
			rolledDice = false;
			suggestionMade = false;
			turn();
		}

	}

	private void move() {
		rolledDice = true;
		System.out.println("Rolling the dice...");
		int dice = randomGenerator.nextInt(6)+1;
		System.out.println("You rolled a "+dice);
		int i=0;
		while(i<dice){
			String direction = "X";
			System.out.println("Which way do you want to move?");
			System.out.println("You have "+(dice-i)+" moves left");
			System.out.println("N, E, S, W?");
			System.out.print("> ");
			try {
				direction = input.readLine();
				System.out.println("You choose to move "+direction);
				System.out.println();
			} catch (IOException e) {
				System.err.println("I/O Error - " + e.getMessage());
				System.out.print("> ");
				e.printStackTrace();
			}
			boolean validMove = false;
			boolean checkMove = board.checkMove(playerList.get(currentPlayerIndex).getX(), playerList.get(currentPlayerIndex).getY(), direction);
			System.out.println(checkMove);
			if(checkMove){
				validMove = playerList.get(currentPlayerIndex).move(direction);
			}
			if(validMove){
				board.drawBoard(playerList);
				i++;
			}else{
				System.out.println("Not a valid move");
				System.out.println();
			}
		}
		turn();
	}

	private void suggestion(){
		List<Card> sug = new ArrayList<>();
		String playerRoom = board.getRoom(playerList.get(currentPlayerIndex).getX(), playerList.get(currentPlayerIndex).getY());
		if(playerRoom.equals("ground") || playerRoom.equals("door")){
			System.out.println("You are not in a room, cannot make a suggestion");
			suggestionMade = true;
			return;
		}
		try {
			
			System.out.println("The room is "+playerRoom);
			Card room = new Room("Kitchen");
			sug.add(room);
			
			Card weapon = null;
			while(weapon == null){
				System.out.println("What weapon?");
				System.out.print("> ");
				String text = input.readLine();
				if(text.equalsIgnoreCase("candlestick")||text.equalsIgnoreCase("dagger")){
					weapon = new Weapon(text);
					sug.add(weapon);
				}else{
					System.out.println("Not a valid weapon");
					System.out.println();
				}
			}
			Card character = null;
			while(character == null){
				System.out.println("What character?");
				System.out.print("> ");
				String text = input.readLine();
				if(text.equalsIgnoreCase("Miss Scarlett")||text.equalsIgnoreCase("Colonel Mustard")){
					character = new Character(text);
					sug.add(character);
				}else{
					System.out.println("Not a valid character");
					System.out.println();
				}
			}

		} catch(IOException e) {
			System.err.println("I/O Error - " + e.getMessage());
		}
		boolean suggestion = false;
		for(Player p: playerList){
			for(Card c: sug){
				if(p.holding(c)){
					if(!suggestion){
						System.out.println("player "+(playerList.indexOf(p)+1)+" is holding the "+c);
						suggestion = true;
					}


				}
			}
		}
		if(!suggestion){
			System.out.println("Your suggestion was not countered by any players");
		}
		suggestionMade = true;
		turn();
	}

	private void accusation() {
		Card room = null;
		while(room == null){
			System.out.println("What room?");
			System.out.print("> ");
			//String text = input.readLine();
			//if(text.equalsIgnoreCase("kitchen")||text.equalsIgnoreCase("ballroom")){
			//	room = new Room(text.toLowerCase());
				//sug.add(room);
			//}else{
				System.out.println("Not a valid room");
				System.out.println();
			//}
		}
	}

	private void showHand() {
		playerList.get(currentPlayerIndex).showHand();
		turn();
	}

	public static void main(String[] args) {
		new Game();

	}
}
