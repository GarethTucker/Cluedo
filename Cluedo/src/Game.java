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
	private List<Card> solutionEnvelope = new ArrayList<>();

	/**
	 * The initial game constructor that creates a board, asks how many players are in the game and
	 * fills the playerList with each player. It then sets the players starting positions, creates
	 * the envelope with the game solution and deals the remaining cards to the players. It then
	 * calls turn to start the game loop.
	 */
	public Game(){
		board = new Board();
		
		boolean playerCountChoosen = true;
		while(playerCountChoosen ){
			try {
				System.out.println("How Many Players?");
				System.out.print("> ");
				String text = input.readLine();
				playerCount = Integer.parseInt(text);
				playerCountChoosen = false;

			} catch(IOException e) {
				System.err.println("I/O Error - " + e.getMessage());
			} catch(NumberFormatException e){
				System.out.println("Not a valid Entry");
				playerCountChoosen = true;
			}
		}
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
			add(new Room("Kitchen"));
			add(new Room("Ballroom"));
			add(new Room("Conservatory"));
			add(new Room("Dining Room"));
			add(new Room("Billard Room"));
			add(new Room("Library"));
			add(new Room("Study"));
			add(new Room("Hall"));
			add(new Room("Lounge"));

		}};
		List<Card> charactersDeck = new ArrayList<Card>(){{
			add(new Character("Miss Scarlett"));
			add(new Character("Colonel Mustard"));
			add(new Character("Mrs. White"));
			add(new Character("The Reverend Green"));
			add(new Character("Mrs. Peacock"));
			add(new Character("Professor Plum"));

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
	
	
	//The list of choices that are used in the turn method.
	private List<String> choicesList = new ArrayList<String>(){{
		add("Roll Dice");
		add("Make a suggestion");
		add("Make an accusation");
		add("See hand");
		add("End Turn");
	}};
	/**
	 * The Main turn method, from here the decisions are made and the game is run.
	 * Recursively calls itself until the game is over.
	 */
	private void turn() {
		boolean gameWon = false;
		String turnChoice = "";
		
		//board.drawBoard(playerList);
		String textInput = "";
		try {
			System.out.println();
			System.out.println("It is Player "+(currentPlayerIndex+1)+"'s turn");
			System.out.println("What would you like to do?");
			for(int i=0; i<choicesList.size(); i++){
				System.out.println((i+1)+". "+choicesList.get(i));
			}
			System.out.print("> ");
			textInput = input.readLine();
			turnChoice = choicesList.remove(Integer.parseInt(textInput)-1);

		} catch(NumberFormatException e) {
			turnChoice = "";
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(turnChoice.equals("Roll Dice")){			
			move();
		}else if(turnChoice.equals("Make a suggestion")){
			suggestion();
		}else if(turnChoice.equals("Make an accusation")){
			gameWon = accusation();
		}else if(turnChoice.equals("See hand")){
			System.out.println("This is your hand");
			showHand();
		}else if(turnChoice.equals("End Turn")){
			System.out.println("End of Player "+(currentPlayerIndex+1)+"'s turn");
			currentPlayerIndex++;
			if(currentPlayerIndex == playerList.size())currentPlayerIndex = 0;
			resetChoicesList();
		}else{
			System.out.println("Not a valid entry");
		}
		if(!gameWon){
			turn();
		}
		System.out.println("Game over");
	}
	/**
	 * Simple method to reset the choices, this is called when the player ends their turn
	 */
	private void resetChoicesList() {
		choicesList = new ArrayList<String>(){{
			add("Roll Dice");
			add("Make a suggestion");
			add("Make an accusation");
			add("See hand");
			add("End Turn");
		}};	
	}
	
	/**
	 * Rolls the dice and gives the player as many choices as was rolled on the dice
	 * to move around the board. This is checked by the board and the player position.
	 */
	private void move() {
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
				//board.drawBoard(playerList);
				i++;
			}else{
				System.out.println("Not a valid move");
				System.out.println();
			}
		}
	}
	
	/**
	 * Builds a suggestion from the players position on the board and the choices they make and 
	 * then tests this against the hands of the other players.
	 */
	private void suggestion(){
		List<Card> sug = new ArrayList<>();
		String playerRoom = board.getRoom(playerList.get(currentPlayerIndex).getX(), playerList.get(currentPlayerIndex).getY());
		if(playerRoom.equals("ground") || playerRoom.equals("door")){
			System.out.println("You are not in a room, cannot make a suggestion");
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
				if(text.equalsIgnoreCase("candlestick")||text.equalsIgnoreCase("dagger")||text.equalsIgnoreCase("Lead Pipe")
						||text.equalsIgnoreCase("Revolver")||text.equalsIgnoreCase("Rope")||text.equalsIgnoreCase("Spanner")){
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
				if(text.equalsIgnoreCase("Miss Scarlett")||text.equalsIgnoreCase("Colonel Mustard")||text.equalsIgnoreCase("Mrs. White")
						||text.equalsIgnoreCase("The Reverend Green")||text.equalsIgnoreCase("Mrs. Peacock")||text.equalsIgnoreCase("Professor Plum")){
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
		for(Player p: playerList){
			for(Card c: sug){
				if(p.holding(c)){
					System.out.println("player "+(playerList.indexOf(p)+1)+" is holding the "+c);
					return;
				}
			}
		}
		System.out.println("Your suggestion was not countered by any players");
	}
	
	/**
	 * Builds an accusation from the players position  choices they make and 
	 * then tests this against the envelope and returns whether they were correct
	 * or not.
	 */
	private boolean accusation() {
		List<Card> playerAccusation = new ArrayList<>();
		try {
			Card room = null;
			while(room == null){
				System.out.println("What room?");
				System.out.print("> ");
				String text;
				text = input.readLine();
				if(text.equalsIgnoreCase("kitchen")||text.equalsIgnoreCase("ballroom")||text.equalsIgnoreCase("conservatory")
						||text.equalsIgnoreCase("billiard room")||text.equalsIgnoreCase("library")||text.equalsIgnoreCase("study")
						||text.equalsIgnoreCase("hall")||text.equalsIgnoreCase("lounge")||text.equalsIgnoreCase("dining room")){
					room = new Room(text.toLowerCase());
					playerAccusation.add(room);
				}else{
					System.out.println("Not a valid room");
					System.out.println();
				}
			}
			Card weapon = null;
			while(weapon == null){
				System.out.println("What weapon?");
				System.out.print("> ");
				String text = input.readLine();
				if(text.equalsIgnoreCase("candlestick")||text.equalsIgnoreCase("dagger")||text.equalsIgnoreCase("Lead Pipe")
						||text.equalsIgnoreCase("Revolver")||text.equalsIgnoreCase("Rope")||text.equalsIgnoreCase("Spanner")){
					weapon = new Weapon(text);
					playerAccusation.add(weapon);
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
				if(text.equalsIgnoreCase("Miss Scarlett")||text.equalsIgnoreCase("Colonel Mustard")||text.equalsIgnoreCase("Mrs. White")
						||text.equalsIgnoreCase("The Reverend Green")||text.equalsIgnoreCase("Mrs. Peacock")||text.equalsIgnoreCase("Professor Plum")){
					character = new Character(text);
					playerAccusation.add(character);
				}else{
					System.out.println("Not a valid character");
					System.out.println();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Card c: playerAccusation){
			if(!solutionEnvelope.contains(c)){
				return false;
			}
		}
		return true;
	}


	private void showHand() {
		playerList.get(currentPlayerIndex).showHand();
		turn();
	}

	public static void main(String[] args) {
		new Game();

	}
}
